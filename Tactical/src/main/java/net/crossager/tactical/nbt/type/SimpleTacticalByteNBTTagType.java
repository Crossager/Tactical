package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalByteNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalByteNBTTagType extends SimpleTacticalNumberNBTTagType<Byte, TacticalByteNBTTag> implements TacticalByteNBTTagType {
    public static final byte ID = 1;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?(\\d+)b$", Pattern.CASE_INSENSITIVE);

    public SimpleTacticalByteNBTTagType() {
        super(Byte.class, SimpleTacticalByteNBTTag::new, Byte::parseByte);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalByteNBTTag parse(@NotNull String input) {
        return new SimpleTacticalByteNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalByteNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(9);
        return new SimpleTacticalByteNBTTag(dataInput.readByte());
    }
}
