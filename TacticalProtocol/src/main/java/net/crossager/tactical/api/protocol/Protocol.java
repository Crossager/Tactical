package net.crossager.tactical.api.protocol;

/**
 * Enumeration representing different sections of a network protocol.
 */
public enum Protocol {
    HANDSHAKING(-1, "Handshaking", "handshake"),
    PLAY(0, "Play", "game"),
    STATUS(1, "Status", "status"),
    LOGIN(2, "Login", "login");

    private final int id;
    private final String className;
    private final String packageName;

    Protocol(int id, String className, String packageName) {
        this.id = id;
        this.className = className;
        this.packageName = packageName;
    }

    /**
     * Returns the internal ID of the protocol.
     *
     * @return The internal ID of the protocol.
     */
    public int id() {
        return id;
    }

    /**
     * Returns the class prefix of the protocol.
     *
     * @return The class prefix of the protocol.
     */
    public String className() {
        return className;
    }

    /**
     * Returns the package name of the protocol.
     *
     * @return The package name of the protocol.
     */
    public String packageName() {
        return packageName;
    }
}
