package net.crossager.tactical.config.type;

import net.crossager.tactical.api.config.ConfigOptions;
import net.crossager.tactical.api.config.items.ConfigListElement;
import net.crossager.tactical.api.config.items.ConfigObject;

import java.util.List;
@Deprecated
public class FileSaver {
    protected final StringBuilder builder = new StringBuilder();
    private final String name;
    private final boolean compact;
    private final String tab;
    private final ConfigType type;

    public FileSaver(String name, ConfigOptions options, ConfigType type, List<ConfigObject> children) {
        this.name = name;
        compact = options.prettyPrint();
        this.type = type;
        tab = "  ";
        appendChildren(builder, children);
    }

    protected void appendChildren(StringBuilder builder, List<ConfigObject> children) {
        for (int i = 0; i < children.size(); i++) {
            ConfigObject child = children.get(i);
            String tabs = getTabs(child.ancestors().size());
            if (child.isValue()) {
                builder.append(type.createField(child.name(), compact, tabs).formatted(type.createValue(child.asString())));
            } else if (child.isSection()) {
                StringBuilder values = new StringBuilder();
                appendChildren(values, (List<ConfigObject>) child.asSection().get().children());
                builder.append(type.createSection(child.name(), compact, tabs).formatted(values));
            } else if (child.isList()) {
                StringBuilder values = new StringBuilder();
                appendElements(values, child.asList().get().asList());
                builder.append(type.createList(child.name(), compact, tabs).formatted(values));
            } else {
                builder.append(type.createField(child.name(), compact, tabs).formatted(""));
            }
            if (i + 1 != children.size()) builder.append(type.listObjectSeparator(compact));
            if ((!compact || type.isNewLineRequired()) && i + 1 != children.size()) builder.append("\n");
        }
    }

    private void appendElements(StringBuilder builder, List<ConfigListElement> children) {
        for (int i = 0; i < children.size(); i++) {
            ConfigListElement element = children.get(i);
            String tabs = getTabs(element.getList().ancestors().size() + 1);
            if (element.isValue()) {
                builder.append(type.createListObject(compact, tabs).formatted(type.createValue(element.asString())));
            } else if (element.isSection()) {
                StringBuilder values = new StringBuilder();
                appendChildren(values, (List<ConfigObject>) element.asSection().get().children());
                builder.append(type.createSectionInList(element.asSection().get().name(), compact, tabs).formatted(values));
            } else if (element.isList()) {
                StringBuilder values = new StringBuilder();
                appendElements(values, element.asList().get().asList());
                builder.append(type.createListInList(element.asList().get().name(), compact, tabs).formatted(values));
            } else {
                builder.append(type.createListObject(compact, tabs).formatted(type.createValue("")));
            }
            if (i + 1 != children.size()) builder.append(type.listObjectSeparator(compact));
            if ((!compact || type.isNewLineRequired()) && i + 1 != children.size()) builder.append("\n");
        }
    }

    private String getTabs(int amount) {
        return tab.repeat(amount);
    }

    @Override
    public String toString() {
        return type.createFile(name, compact).formatted(builder.toString());
    }
    static class ConfigType {

        public String createField(String name, boolean compact, String tabs) {
            return null;
        }

        public boolean isNewLineRequired() {
            return false;
        }

        public String createListObject(boolean compact, String tabs) {
            return null;
        }

        public String createListInList(String name, boolean compact, String tabs) {
            return null;
        }

        public char[] listObjectSeparator(boolean compact) {
            return new char[0];
        }

        public String createFile(String name, boolean compact) {
            return null;
        }

        public Object createValue(String s) {
            return null;
        }

        public String createSectionInList(String name, boolean compact, String tabs) {
            return null;
        }

        public String createList(String name, boolean compact, String tabs) {
            return null;
        }

        public String createSection(String name, boolean compact, String tabs) {
            return null;
        }
    }
}
