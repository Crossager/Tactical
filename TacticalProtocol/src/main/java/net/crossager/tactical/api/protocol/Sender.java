package net.crossager.tactical.api.protocol;

/**
 * Enum representing the sender of a network packet.
 * <p>
 * This enum specifies whether the packet is sent from the server to the client or from the client to the server.
 */
public enum Sender {
    SERVER("Clientbound", "Out", "server"),
    CLIENT("Serverbound", "In", "client");

    private final String bounding;
    private final String direction;
    private final String mcpName;

    Sender(String bounding, String direction, String mcpName) {
        this.bounding = bounding;
        this.direction = direction;
        this.mcpName = mcpName;
    }

    /**
     * Returns the bounding type of the packet.
     *
     * @return The bounding type of the packet.
     */
    public String bounding() {
        return bounding;
    }

    /**
     * Returns the direction of the packet.
     *
     * @return The direction of the packet.
     */
    public String direction() {
        return direction;
    }

    /**
     * Returns the name of the packet in Minecraft Protocol (MCP) format.
     *
     * @return The name of the packet in MCP format.
     */
    public String mcpName() {
        return mcpName;
    }
}