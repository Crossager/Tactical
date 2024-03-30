package net.crossager.tactical.config.serializers;

import net.crossager.tactical.api.config.TacticalConfigOptions;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.items.*;
import net.crossager.tactical.api.util.TacticalUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.List;
import java.util.Map;

public class XmlSerializer implements TacticalConfigSerializer {
    public static final List<Character> ALLOWED_NAME_CHARACTERS = List.of('-', '_', '.', '\n');

    @Override
    public @NotNull String serialize(@NotNull String name, @NotNull TacticalConfigOptions options, @NotNull Map<String, TacticalConfigValue> children) {
        return Jsoup.parse("").outputSettings(
                new Document.OutputSettings().prettyPrint(options.prettyPrint()).indentAmount(options.tabSize()).syntax(Document.OutputSettings.Syntax.xml))
                .body().appendElement(name).appendChildren(TacticalUtils.toList(children, this::serializeValue)).toString();
    }

    private Node serializeValue(String name, TacticalConfigValue value) {
        name = checkName(name);
        if (value.isList()) {
            Element listElement = new Element(name).attr("type", "list");
            value.asList().forEach(e -> listElement.appendChild(serializeValue("element", e)));
            return listElement;
        }
        if (value.isSection()) {
            Element sectionElement = new Element(name).attr("type", "section");
            value.asSection().children().forEach((path, element) -> sectionElement.appendChild(serializeValue(path, element)));
            return sectionElement;
        }
        return new Element(name).text(checkValue(value.get().toString()));
    }

    private String checkName(String name) {
        if (!Character.isLetter(name.charAt(0))) throw new IllegalArgumentException("First letter of xml field must be a letter");
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c) && !ALLOWED_NAME_CHARACTERS.contains(c))
                throw new IllegalArgumentException("Character '" + c + "' is not allowed in xml field names");
        }
        return name;
    }

    private String checkValue(String value) {
        if (value.contains("<") || value.contains(">"))
            throw new IllegalArgumentException("Characters '<' or '>' are not allowed in xml field values");
        return value;
    }


    @Override
    public void deserialize(@NotNull String data, @NotNull TacticalConfigSection section) {
        Jsoup.parse(data).body().child(0).children().forEach(element -> deserializeChild(element, section));
    }

    private void deserializeChild(Element element, TacticalConfigSection section) {
        if (!element.hasAttr("type") && element.childrenSize() == 0) {
            section.set(element.tagName(), checkValue(element.text()));
            return;
        }
        if (element.attr("type").equals("list")) {
            TacticalConfigList newList = section.addList(element.tagName());
            element.children().forEach(e -> deserializeElement(e, newList));
            return;
        }
        TacticalConfigSection newSection = section.addSection(element.tagName());
        element.children().forEach(e -> deserializeChild(e, newSection));
    }

    @Override
    public @NotNull String format() {
        return "xml";
    }

    private void deserializeElement(Element element, TacticalConfigList list) {
        if (!element.hasAttr("type") && element.childrenSize() == 0) {
            list.addValue(checkValue(element.text()));
            return;
        }
        if (element.attr("type").equals("list")) {
            TacticalConfigList newList = list.addList();
            element.children().forEach(e -> deserializeElement(e, newList));
            return;
        }
        TacticalConfigSection newSection = list.addSection();
        element.children().forEach(e -> deserializeChild(e, newSection));
    }
}
