package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.TacticalShortNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalShortNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalShortNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalShortNBTTagType extends SimpleTacticalNumberNBTTagType<Short, TacticalShortNBTTag> implements TacticalShortNBTTagType {
    public static final byte ID = 2;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?(\\d+)s$", Pattern.CASE_INSENSITIVE);

    public SimpleTacticalShortNBTTagType() {
        super(Short.class, SimpleTacticalShortNBTTag::new, Short::parseShort);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalShortNBTTag parse(@NotNull String input) {
        return new SimpleTacticalShortNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalShortNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(10);
        return new SimpleTacticalShortNBTTag(dataInput.readShort());
    }
}
