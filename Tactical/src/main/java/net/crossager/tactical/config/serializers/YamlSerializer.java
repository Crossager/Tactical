package net.crossager.tactical.config.serializers;

import net.crossager.tactical.api.config.TacticalConfigOptions;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.api.util.FunctionUtils;
import net.crossager.tactical.api.util.TacticalUtils;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// A lot of this was taken from org.bukkit.configuration.file.YamlConfiguration
public class YamlSerializer implements TacticalConfigSerializer {
    private final Yaml yaml = new Yaml();
    private final DumperOptions dumperOptions = new DumperOptions();
    private final YamlRepresenter representer = new YamlRepresenter();
    private final YamlConstructor constructor = new YamlConstructor();

    @Override
    public @NotNull String format() {
        return "yml";
    }

    @Override
    public @NotNull String serialize(@NotNull String name, @NotNull TacticalConfigOptions options, @NotNull Map<String, TacticalConfigValue> children) {
        dumperOptions.setIndent(options.tabSize());
        dumperOptions.setProcessComments(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        representer.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);

        MappingNode node = toNodeTree(children);

        node.setBlockComments(List.of(new CommentLine(null, null , "test", CommentType.BLOCK)));

//        node.setBlockComments(getCommentLines(List.of(), CommentType.BLOCK));
//        node.setEndComments(getCommentLines(List.of(), CommentType.BLOCK));

        StringWriter writer = new StringWriter();
        if (node.getBlockComments().isEmpty() && node.getEndComments().isEmpty() && node.getValue().isEmpty()) {
            writer.write("");
        } else {
            if (node.getValue().isEmpty()) {
                node.setFlowStyle(DumperOptions.FlowStyle.FLOW);
            }
            yaml.serialize(node, writer);
        }
        return writer.toString();
    }

    private MappingNode toNodeTree(Map<String, TacticalConfigValue> children) {
        List<NodeTuple> nodeTuples = new ArrayList<>();
        children.forEach((name, child) -> {
            Node key = representer.represent(name);
            Node value;
            if (child.isSection()) {
                value = toNodeTree(child.asSection().children());
            } else if (child.isList()) {
                value = toNodeSequence(child.asList());
            } else {
                value = representer.represent(child.get());
            }
//            key.setBlockComments(getCommentLines(child.comments().comments(), CommentType.BLOCK));
//            if (value instanceof MappingNode || value instanceof SequenceNode) {
//                key.setInLineComments(getCommentLines(child.comments().inlineComments(), CommentType.IN_LINE));
//            } else {
//                value.setInLineComments(getCommentLines(child.comments().inlineComments(), CommentType.IN_LINE));
//            }

            nodeTuples.add(new NodeTuple(key, value));
        });
        return new MappingNode(Tag.MAP, nodeTuples, DumperOptions.FlowStyle.BLOCK);
    }

    private SequenceNode toNodeSequence(Iterable<TacticalConfigValue> elements) {
        List<Node> elementNodes = new ArrayList<>();
        elements.forEach(element -> {
            Node value;
            if (element.isSection()) {
                value = toNodeTree(element.asSection().children());
            } else if (element.isList()) {
                value = toNodeSequence(element.asList());
            } else {
                value = new ScalarNode(Tag.STR, element.asString(), null, null, DumperOptions.ScalarStyle.PLAIN);
            }
            elementNodes.add(value);
        });
        return new SequenceNode(Tag.SEQ, elementNodes, DumperOptions.FlowStyle.BLOCK);
    }

    @Override
    public void deserialize(@NotNull String data, @NotNull TacticalConfigSection section) {
        MappingNode node;
        try (Reader reader = new UnicodeReader(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)))) {
            node = (MappingNode) yaml.compose(reader);
        } catch (YAMLException | IOException e) {
            throw new IllegalStateException(e);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Top level is not a Map.");
        }

        if (node != null)
            fromNodeTree(node, section);
    }

    private void fromNodeTree(MappingNode input, TacticalConfigSection section) {
        constructor.flattenMapping(input);
        for (NodeTuple nodeTuple : input.getValue()) {
            Node key = nodeTuple.getKeyNode();
            String keyString = String.valueOf(constructor.construct(key));
            Node value = nodeTuple.getValueNode();

            while (value instanceof AnchorNode) {
                value = ((AnchorNode) value).getRealNode();
            }

            if (value instanceof MappingNode && !hasSerializedTypeKey((MappingNode) value)) {
                fromNodeTree((MappingNode) value, section.addSection(keyString));
            } else if (value instanceof SequenceNode) {
                fromNodeSequence((SequenceNode) value, section.addList(keyString));
            } else {
                section.set(keyString, constructor.construct(value));
            }
        }
    }

    private void fromNodeSequence(SequenceNode input, TacticalConfigList list) {
        for (Node value : input.getValue()) {
            while (value instanceof AnchorNode) {
                value = ((AnchorNode) value).getRealNode();
            }

            if (value instanceof MappingNode && !hasSerializedTypeKey((MappingNode) value)) {
                fromNodeTree((MappingNode) value, list.addSection());
            } else if (value instanceof SequenceNode) {
                fromNodeSequence((SequenceNode) value, list.addList());
            } else {
                list.addValue(constructor.construct(value));
            }
        }
    }

    private boolean hasSerializedTypeKey(MappingNode node) {
        for (NodeTuple nodeTuple : node.getValue()) {
            Node keyNode = nodeTuple.getKeyNode();
            if (!(keyNode instanceof ScalarNode)) continue;
            String key = ((ScalarNode) keyNode).getValue();
            if (key.equals(ConfigurationSerialization.SERIALIZED_TYPE_KEY)) {
                return true;
            }
        }
        return false;
    }

    private List<CommentLine> getCommentLines(List<String> comments, CommentType commentType) {
        List<CommentLine> lines = new ArrayList<CommentLine>();
        for (String comment : comments) {
            if (comment == null) {
                lines.add(new CommentLine(null, null, "", CommentType.BLANK_LINE));
            } else {
                String line = comment;
                line = line.isEmpty() ? line : " " + line;
                lines.add(new CommentLine(null, null, line, commentType));
            }
        }
        return lines;
    }
}
