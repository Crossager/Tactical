package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalIntNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.regex.Pattern;

public class SimpleTacticalIntNBTTagType extends SimpleTacticalNumberNBTTagType<Integer, TacticalIntNBTTag> implements TacticalIntNBTTagType {
    public static final byte ID = 3;
    private static final Pattern PATTERN = Pattern.compile("^[-+]?(\\d+)$", Pattern.CASE_INSENSITIVE);

    public SimpleTacticalIntNBTTagType() {
        super(Integer.class, SimpleTacticalIntNBTTag::new, Integer::parseInt);
    }

    @Override
    public @NotNull Pattern numberPattern() {
        return PATTERN;
    }

    @Override
    public @NotNull TacticalIntNBTTag parse(@NotNull String input) {
        return new SimpleTacticalIntNBTTag(doParse(input));
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalIntNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(12);
        return new SimpleTacticalIntNBTTag(dataInput.readInt());
    }
}
