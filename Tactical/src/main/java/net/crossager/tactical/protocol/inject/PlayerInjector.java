package net.crossager.tactical.protocol.inject;

import io.netty.channel.Channel;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.protocol.ProtocolUtils;
import org.bukkit.entity.Player;

public class PlayerInjector {
    private static final String DECODING_NAME = "ClientPacketListener";
    private static final String ENCODING_NAME = "ServerPacketListener";
    private boolean isInjected = false;
    private final Channel channel;
    private final Player player;
    private final DecodingReader decodingReader;
    private final EncodingReader encodingReader;

    public PlayerInjector(Player player, PacketInterceptor packetInterceptor) {
        this.channel = ProtocolUtils.getChannel(player);
        this.player = player;
        this.decodingReader = new DecodingReader(packetInterceptor.asPredicate(player, Sender.CLIENT));
        this.encodingReader = new EncodingReader(packetInterceptor.asPredicate(player, Sender.SERVER));
    }

    public PlayerInjector inject() {
        if (isInjected) return this;
        isInjected = true;
        channel.pipeline().addAfter("decoder", DECODING_NAME, decodingReader);
        channel.pipeline().addAfter("encoder", ENCODING_NAME, encodingReader);
        return this;
    }

    public PlayerInjector uninject() {
        if (!isInjected) return this;
        isInjected = false;
        channel.pipeline().remove(DECODING_NAME);
        channel.pipeline().remove(ENCODING_NAME);
        return this;
    }

    public Player player() {
        return player;
    }
}
