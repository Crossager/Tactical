package net.crossager.tactical.nbt;

import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.api.wrappers.nbt.*;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalAnyListNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalListNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNumberNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;

public class SimpleTacticalNBTParser implements TacticalNBTParser {
    public static final int MAX_DEPTH = 256;
    public static final char OBJECT_BEGIN = '{';
    public static final char OBJECT_END = '}';
    public static final char LIST_BEGIN = '[';
    public static final char LIST_END = ']';

    private final TacticalNBTStringReader reader;
    private final List<? extends TacticalNumberNBTTagType<?, ?>> numberTypes;
    private final List<? extends TacticalListNBTTagType<?, ?>> numberListTypes;

    public SimpleTacticalNBTParser(TacticalNBTManager nbtManager, String nbt) {
        this.reader = new SimpleTacticalNBTStringReader(nbt);
        this.numberListTypes = nbtManager.types().stream()
                .filter(TacticalUtils.castClassGenerics(TacticalListNBTTagType.class)::isInstance)
                .map(TacticalUtils.<TacticalListNBTTagType<?, ?>>castClassGenerics(TacticalListNBTTagType.class)::cast).toList();
        this.numberTypes = nbtManager.types().stream()
                .filter(TacticalUtils.castClassGenerics(TacticalNumberNBTTagType.class)::isInstance)
                .map(TacticalUtils.<TacticalNumberNBTTagType<?, ?>>castClassGenerics(TacticalNumberNBTTagType.class)::cast).toList();
    }

    @Override
    public @NotNull String parseString() {
        return reader.getString();
    }

    @Override
    public @NotNull TacticalNBTTagCompound parseCompound() throws NBTParseException {
        TacticalNBTTag<?> parse = parse();
        if (parse instanceof TacticalNBTTagCompound compound) return compound;
        throw new NBTParseException("Not a compound");
    }

    @Override
    public @NotNull TacticalNBTTag<?> parse() throws NBTParseException {
        return parse(MAX_DEPTH);
    }

    @Override
    public @NotNull TacticalNBTTag<?> parse(int maxDepth) throws NBTParseException {
        return parse(maxDepth, false);
    }

    @Override
    public @NotNull TacticalNBTTag<?> parse(int maxDepth, boolean skipExtraEndChars) throws NBTParseException {
        TacticalNBTTag<?> tag = read(maxDepth);
        if (!skipExtraEndChars) {
            reader.skipWhitespaces();
            if (reader.hasNext()) throw new NBTParseException("Illegal end characters");
        }
        return tag;
    }

    private TacticalNBTTagCompound readCompound(int remainingDepth) {
        reader.expectAndSkip(OBJECT_BEGIN);

        SimpleTacticalNBTTagCompound compound = new SimpleTacticalNBTTagCompound(new LinkedHashMap<>());

        reader.skipWhitespaces();
        while (reader.hasNext() && reader.currentChar() != OBJECT_END) {
            reader.skipWhitespaces();

            String field = reader.readAnyString();

            if (field.isEmpty()) throw new NBTParseException("Field name cannot be empty");

            reader.skipWhitespaces();
            reader.expectAndSkip(':');

            compound.set(field, read(decrementRemainingDepth(remainingDepth)));

            if (!reader.beginNextElement()) break;
        }
        reader.expectAndSkip(OBJECT_END);
        return compound;
    }

    private TacticalNBTTag<?> read(int remainingDepth) {
        reader.skipWhitespaces();
        return switch (reader.currentChar()) {
            case OBJECT_BEGIN -> readCompound(remainingDepth);
            case LIST_BEGIN -> readAnyList(remainingDepth);
            default -> readPrimitive();
        };
    }

    private TacticalListNBTTag<?> readAnyList(int remainingDepth) {
        reader.expectAndSkip(LIST_BEGIN);
        reader.skipWhitespaces();
        if (reader.remainingChars() > 2 && !reader.isStartingQuotation(reader.peek(1)) && reader.peek(2) == ';') return readNumberList(remainingDepth);
        return readList(remainingDepth, TacticalAnyListNBTTagType.type());
    }

    private TacticalListNBTTag<?> readNumberList(int remainingDepth) {
         char identifier = reader.next();
         reader.expectAndSkip(';');
         return readList(remainingDepth, numberListTypes.stream()
                 .filter(list -> list.identifier() == identifier)
                 .findAny()
                 .orElseThrow(() -> new NBTParseException("Invalid number list id '" + identifier + '\'')));
    }

    @SuppressWarnings("unchecked")
    private TacticalListNBTTag<?> readList(int remainingDepth, TacticalListNBTTagType<?, ?> type) {
        TacticalListNBTTag<TacticalNBTTag<?>> list = (TacticalListNBTTag<TacticalNBTTag<?>>) type.create(new ArrayList<>());
        while (reader.currentChar() != ']') {
            TacticalNBTTag<?> tag = read(decrementRemainingDepth(remainingDepth));
            if (!type.elementType().isInstance(tag))
                throw new NBTParseException("Element of type " + tag.getClass().getName() + " cannot be added to list of type " + type.elementType());
            list.add(tag);
            if (!reader.beginNextElement()) break;
        }
        reader.expectAndSkip(LIST_END);
        return list;
    }

    private TacticalNBTTag<?> readPrimitive() {
        reader.skipWhitespaces();
        if (reader.isStartingQuotation())
            return new SimpleTacticalStringNBTTag(reader.readQuotedString());
        String value = reader.readStringValue();
        if (value.isEmpty()) throw new NBTParseException("Empty value");
        for (TacticalNumberNBTTagType<?, ?> numberType : numberTypes) {
            Matcher matcher = numberType.numberPattern().matcher(value);
            if (!matcher.matches()) continue;
            String group = matcher.group(1);
            try {
                return numberType.parse(group);
            } catch (NumberFormatException e) {
                throw new NBTParseException("Input string '" + group + "' could not be parsed as " + numberType.valueClass().getName(), reader);
            }
        }
        if (value.equalsIgnoreCase("false")) return TacticalByteNBTTag.FALSE;
        if (value.equalsIgnoreCase("true")) return TacticalByteNBTTag.TRUE;
        return new SimpleTacticalStringNBTTag(value);
    }

    private int decrementRemainingDepth(int remainingDepth) {
        if (remainingDepth == 0) throw new NBTParseException("Reached max depth");
        return remainingDepth - 1;
    }
}
