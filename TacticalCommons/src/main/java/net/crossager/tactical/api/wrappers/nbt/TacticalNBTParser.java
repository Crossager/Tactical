package net.crossager.tactical.api.wrappers.nbt;

import org.jetbrains.annotations.NotNull;

public interface TacticalNBTParser {
    @NotNull
    String parseString();
    @NotNull
    TacticalNBTTagCompound parseCompound() throws NBTParseException;
    @NotNull
    TacticalNBTTag<?> parse() throws NBTParseException;
    @NotNull
    TacticalNBTTag<?> parse(int maxDepth) throws NBTParseException;
    @NotNull
    TacticalNBTTag<?> parse(int maxDepth, boolean skipExtraEndChars) throws NBTParseException;

    static TacticalNBTParser of(String parseString) {
        return TacticalNBTManager.getInstance().parse(parseString);
    }

    static TacticalNBTTagCompound parseCompound(String parseString) {
        return of(parseString).parseCompound();
    }

    static TacticalNBTTag<?> parse(String parseString) {
        return of(parseString).parse();
    }
}
