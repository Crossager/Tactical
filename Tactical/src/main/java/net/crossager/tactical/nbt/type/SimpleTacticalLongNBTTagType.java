package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalLongNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalLongNBTTagType extends SimpleTacticalNumberNBTTagType<Long, TacticalLongNBTTag> implements TacticalLongNBTTagType {
    public static final byte ID = 4;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?(\\d+)l$", Pattern.CASE_INSENSITIVE);

    public SimpleTacticalLongNBTTagType() {
        super(Long.class, SimpleTacticalLongNBTTag::new, Long::parseLong);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalLongNBTTag parse(@NotNull String input) {
        return new SimpleTacticalLongNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalLongNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(16);
        return new SimpleTacticalLongNBTTag(dataInput.readLong());
    }
}
