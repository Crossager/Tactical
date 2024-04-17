package net.crossager.tactical.util.reflect;

import org.bukkit.Bukkit;

import java.util.regex.Pattern;

public final class MinecraftVersion {
    public static final MinecraftVersion v1_20_4 = parseVersion("1.20.4");
    public static final MinecraftVersion v1_20_3 = parseVersion("1.20.3");
    public static final MinecraftVersion v1_20_2 = parseVersion("1.20.2");
    public static final MinecraftVersion v1_20_1 = parseVersion("1.20.1");
    public static final MinecraftVersion v1_20   = parseVersion("1.20");
    public static final MinecraftVersion v1_19_4 = parseVersion("1.19.4");
    public static final MinecraftVersion v1_19_3 = parseVersion("1.19.3");

    public static final MinecraftVersion CURRENT = fromBukkitVersion(Bukkit.getBukkitVersion());
    public static final MinecraftVersion LATEST = v1_20_4;

    private static final Pattern BUKKIT_VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.?(\\d*)-(.)(\\d+\\.\\d+)");

    public static boolean hasVersion(MinecraftVersion neededVersion) {
        return CURRENT.equalsOrNewerThan(neededVersion);
    }

    public static void ensureAboveVersion(MinecraftVersion neededVersion) {
        if (CURRENT.isOlderThan(neededVersion)) throw new IllegalStateException("Unsupported version, need at least %s, found %s".formatted(neededVersion, CURRENT));
    }

    public static void ensureBelowVersion(MinecraftVersion neededVersion) {
        if (!CURRENT.isOlderThan(neededVersion)) throw new IllegalStateException("Unsupported version, highest version supported: %s, found %s".formatted(neededVersion, CURRENT));
    }

    public static MinecraftVersion fromBukkitVersion(String bukkitVersion) {
        return parseVersion(bukkitVersion.split("-")[0]);
    }

    public static MinecraftVersion parseVersion(String stringVersion) {
        String[] qualifiers = stringVersion.split("\\.");
        int majorVersion = Integer.parseInt(qualifiers[0]);
        int version = qualifiers.length > 1 ? Integer.parseInt(qualifiers[1]) : 0;
        int minorVersion = qualifiers.length > 2 ? Integer.parseInt(qualifiers[2]) : 0;
        return new MinecraftVersion(majorVersion, version, minorVersion, ReleaseType.RELEASE);
    }

    private final int majorVersion;
    private final int version;
    private final int minorVersion;
    private final ReleaseType releaseType;
    private final int intRepresentation;
    private final String readableVersion;

    public MinecraftVersion(int majorVersion, int version, int minorVersion, ReleaseType releaseType) {
        this.majorVersion = majorVersion;
        this.version = version;
        this.minorVersion = minorVersion;
        this.releaseType = releaseType;
        this.intRepresentation = (majorVersion << 24) | (version << 16) | (minorVersion << 8) | (releaseType.ordinal());
        this.readableVersion = "%s.%s.%s".formatted(majorVersion, version, minorVersion);
    }

    public int majorVersion() {
        return majorVersion;
    }

    public int version() {
        return version;
    }

    public int minorVersion() {
        return minorVersion;
    }

    public ReleaseType releaseType() {
        return releaseType;
    }

    public boolean hasTacticalSupport() {
        return equalsOrNewerThan(v1_19_3);
    }

    public boolean equalsOrNewerThan(MinecraftVersion version) {
        return intRepresentation >= version.intRepresentation;
    }

    public boolean equalsOrOlderThan(MinecraftVersion version) {
        return intRepresentation <= version.intRepresentation;
    }

    public boolean isNewerThan(MinecraftVersion version) {
        return intRepresentation > version.intRepresentation;
    }

    public boolean isOlderThan(MinecraftVersion version) {
        return intRepresentation < version.intRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MinecraftVersion) obj;
        return that.intRepresentation == intRepresentation;
    }

    @Override
    public int hashCode() {
        return intRepresentation;
    }

    @Override
    public String toString() {
        return readableVersion;
    }


    public enum ReleaseType {
        APRIL_FOOLS,
        SNAPSHOT,
        PRE_RELEASE,
        RELEASE_CANDIDATE,
        RELEASE,
    }
}
