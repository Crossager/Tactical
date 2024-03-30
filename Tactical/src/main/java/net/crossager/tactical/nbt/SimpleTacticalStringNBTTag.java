package net.crossager.tactical.nbt;

import io.netty.handler.codec.EncoderException;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTStringReader;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalStringNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalStringNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UTFDataFormatException;

public class SimpleTacticalStringNBTTag extends SimpleTacticalNBTTag<String> implements TacticalStringNBTTag {
    public SimpleTacticalStringNBTTag(String string) {
        super(string);
    }

    @Override
    public @NotNull String serialize() {
        StringBuilder sb = null;
        for (int i = 0; i < value.toCharArray().length; i++) {
            char c = value.charAt(i);
            if (TacticalNBTStringReader.isLegalEscapeCharacter(c)) {
                if (sb == null) sb = new StringBuilder(value.substring(0, i));
                sb.append('\\');
            }
            if (sb != null) sb.append(value.charAt(i));
        }
        return '"' + (sb == null ? value : sb.toString()) + '"';
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        try {
            dataOutput.writeUTF(value);
        } catch (UTFDataFormatException var3) {
            throw new EncoderException("Malformed utf string '" + value + "'");
        }
    }

    @Override
    public @NotNull TacticalNBTTagType<String, ? extends TacticalNBTTag<String>> type() {
        return TacticalStringNBTTagType.type();
    }

    @Override
    public @NotNull TacticalStringNBTTag copy() {
        return new SimpleTacticalStringNBTTag(value);
    }

    @Override
    public @NotNull TacticalStringNBTTag asStringTag() {
        return this;
    }
}
