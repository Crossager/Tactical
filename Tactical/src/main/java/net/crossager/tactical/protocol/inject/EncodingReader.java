package net.crossager.tactical.protocol.inject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.function.Predicate;

public class EncodingReader extends MessageToMessageEncoder<Object> {
    private final Predicate<Object> predicate;

    public EncodingReader(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        if (predicate.test(msg)) out.add(msg);
    }
}
