package net.crossager.tactical.config.serializers;

import com.google.gson.*;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonWriter;
import net.crossager.tactical.api.config.TacticalConfigOptions;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.api.util.FunctionUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

public class JsonSerializer implements TacticalConfigSerializer {
    @Override
    public @NotNull String format() {
        return "json";
    }

    @Override
    public @NotNull String serialize(@NotNull String name, @NotNull TacticalConfigOptions options, @NotNull Map<String, TacticalConfigValue> children) throws IOException {
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (options.prettyPrint())
            jsonWriter.setIndent("  ".repeat(options.tabSize()));
        else
            jsonWriter.setIndent("");
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, serializeChildren(children));
        return writer.toString();
    }

    private JsonElement serializeChildren(Map<String, TacticalConfigValue> children) {
        JsonObject jsonObject = new JsonObject();
        children.forEach(FunctionUtils.apply2Before(jsonObject::add, Function.identity(), this::serializeElement));
        return jsonObject;
    }

    private JsonElement serializeElement(TacticalConfigValue object) {
        if (object.isBoolean()) return new JsonPrimitive(object.asBoolean());
        if (object.isNumber()) return new JsonPrimitive(object.asNumber());
        if (object.isString()) return new JsonPrimitive(object.asString());
        if (object.isCharacter()) return new JsonPrimitive(object.asCharacter());
        if (object.isList()) {
            JsonArray jsonElements = new JsonArray(object.asList().size());
            object.asList().forEach(FunctionUtils.applyAndThen(this::serializeElement, jsonElements::add));
            return jsonElements;
        }
        JsonObject jsonObject = new JsonObject();
        object.asSection().children().forEach(FunctionUtils.apply2Before(jsonObject::add, Function.identity(), this::serializeElement));
        return jsonObject;
    }

    @Override
    public void deserialize(@NotNull String data, @NotNull TacticalConfigSection section) {
        JsonElement element = JsonParser.parseString(data);
        if (data.isEmpty() || element.isJsonNull()) return;
        if (!element.isJsonObject()) throw new JsonParseException("File Json has to be object");
        Map<String, JsonElement> children = childrenOf(element.getAsJsonObject());
        children.forEach(FunctionUtils.withThirdArg(this::deserializeChild, section));
    }

    private void deserializeChild(String path, JsonElement element, TacticalConfigSection section) {
        if (element.isJsonObject()) {
            Map<String, JsonElement> children = childrenOf(element.getAsJsonObject());
            TacticalConfigSection newSection = section.addSection(path);
            children.forEach(FunctionUtils.withThirdArg(this::deserializeChild, newSection));
            return;
        }
        if (element.isJsonArray()) {
            TacticalConfigList newList = section.addList(path);
            element.getAsJsonArray().forEach(FunctionUtils.withSecondArg(this::deserializeElement, newList));
            return;
        }
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            if (primitive.getAsNumber() instanceof LazilyParsedNumber)
                section.set(path, primitive.getAsLong());
            else
                section.set(path, primitive.getAsNumber());
            return;
        }
        if (primitive.isString()) {
            section.set(path, primitive.getAsString());
            return;
        }
        if (primitive.isBoolean()) {
            section.set(path, primitive.getAsBoolean());
        }
    }

    private void deserializeElement(JsonElement element, TacticalConfigList list) {
        if (element.isJsonObject()) {
            Map<String, JsonElement> children = childrenOf(element.getAsJsonObject());
            TacticalConfigSection newSection = list.addSection();
            children.forEach(FunctionUtils.withThirdArg(this::deserializeChild, newSection));
            return;
        }
        if (element.isJsonArray()) {
            TacticalConfigList newList = list.addList();
            element.getAsJsonArray().forEach(FunctionUtils.withSecondArg(this::deserializeElement, newList));
            return;
        }
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            list.addValue(primitive.getAsNumber());
            return;
        }
        if (primitive.isString()) {
            list.addValue(primitive.getAsString());
            return;
        }
        if (primitive.isBoolean()) {
            list.addValue(primitive.getAsBoolean());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, JsonElement> childrenOf(JsonObject object) {
        try {
            Field field = JsonObject.class.getDeclaredField("members");
            field.setAccessible(true);
            Map<String, JsonElement> map = (Map<String, JsonElement>) field.get(object);
            field.setAccessible(false);
            return map;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
