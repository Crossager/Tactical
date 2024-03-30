package net.crossager.tactical.protocol.inject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.function.Predicate;

public class DecodingReader extends MessageToMessageDecoder<Object> {
    private final Predicate<Object> predicate;

    public DecodingReader(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        if (predicate.test(msg)) out.add(msg);
    }
}
