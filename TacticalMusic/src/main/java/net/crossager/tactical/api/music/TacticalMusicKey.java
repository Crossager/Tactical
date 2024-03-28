package net.crossager.tactical.api.music;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a musical key
 */
public enum TacticalMusicKey {

    C("C"),
    C_SHARP("C#"),
    D("D"),
    D_SHARP("D#"),
    E("E"),
    F("F"),
    F_SHARP("F#"),
    G("G"),
    G_SHARP("G#"),
    A("A"),
    A_SHARP("A#"),
    B("B");

    private final String display;

    /**
     * Constructs a TacticalMusicKey with the given display name.
     *
     * @param display The display name of the musical key.
     */
    TacticalMusicKey(String display) {
        this.display = display;
    }

    /**
     * Retrieves the Minecraft pitch value associated with this musical key.
     *
     * @return The Minecraft pitch value.
     */
    public float minecraftPitch() {
        return minecraftPitch(0);
    }

    /**
     * Retrieves the Minecraft pitch value associated with this musical key and the specified octave.
     *
     * @param octave The octave to use for the calculation.
     * @return The Minecraft pitch value for the specified octave.
     */
    public float minecraftPitch(int octave) {
        return (float) Math.pow(2.0, ((double) ordinal() - 12.0 + octave * 12) / 12.0);
    }

    /**
     * Returns the display name of the musical key.
     *
     * @return The display name.
     */
    @Override
    public String toString() {
        return display;
    }

    private static final TacticalMusicKey[] VALUES = values();

    /**
     * Retrieves a TacticalMusicKey based on its ordinal value.
     *
     * @param key The ordinal value of the key.
     * @return The TacticalMusicKey corresponding to the ordinal value.
     */
    @NotNull
    public static TacticalMusicKey fromKey(int key) {
        return VALUES[key];
    }
}
