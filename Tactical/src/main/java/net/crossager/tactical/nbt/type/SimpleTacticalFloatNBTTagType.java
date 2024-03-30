package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalFloatNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalFloatNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalFloatNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalFloatNBTTagType extends SimpleTacticalNumberNBTTagType<Float, TacticalFloatNBTTag> implements TacticalFloatNBTTagType {
    public static final byte ID = 5;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?(\\d+|\\d+\\.\\d+)f$", Pattern.CASE_INSENSITIVE);
    public SimpleTacticalFloatNBTTagType() {
        super(Float.class, SimpleTacticalFloatNBTTag::new, Float::parseFloat);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalFloatNBTTag parse(@NotNull String input) {
        return new SimpleTacticalFloatNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalFloatNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(12);
        return new SimpleTacticalFloatNBTTag(dataInput.readFloat());
    }
}
