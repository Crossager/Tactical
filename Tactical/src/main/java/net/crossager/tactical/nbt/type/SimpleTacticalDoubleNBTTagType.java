package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalDoubleNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalDoubleNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalDoubleNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalDoubleNBTTagType extends SimpleTacticalNumberNBTTagType<Double, TacticalDoubleNBTTag> implements TacticalDoubleNBTTagType {
    public static final byte ID = 6;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?((\\d+|\\d+\\.\\d+)d|\\d+\\.\\d+)$", Pattern.CASE_INSENSITIVE);

    public SimpleTacticalDoubleNBTTagType() {
        super(Double.class, SimpleTacticalDoubleNBTTag::new, Double::parseDouble);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalDoubleNBTTag parse(@NotNull String input) {
        return new SimpleTacticalDoubleNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalDoubleNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(16);
        return new SimpleTacticalDoubleNBTTag(dataInput.readDouble());
    }
}
