package net.crossager.tactical.api.music;

import net.crossager.tactical.api.TacticalMusic;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a MIDI drum kit in TacticalMusic.
 */
public interface TacticalMidiDrumKit {

    /**
     * Associates the specified Sound with the given mapped key.
     *
     * @param mappedKey The mapped key to associate with the Sound.
     * @param sound     The Sound to associate with the mapped key.
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull Sound sound);

    /**
     * Associates the specified Sound with the given mapped key, musical key, and octave.
     *
     * @param mappedKey The mapped key to associate with the Sound.
     * @param sound     The Sound to associate with the mapped key.
     * @param key       The musical key to associate with the Sound.
     * @param octave    The octave to associate with the Sound.
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull Sound sound, @NotNull TacticalMusicKey key, int octave);

    /**
     * Retrieves the TacticalDrumSound associated with the specified key.
     *
     * @param key The key for which to retrieve the TacticalDrumSound.
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    TacticalDrumSound soundForKey(int key);

    /**
     * Sets the default Sound, musical key, and octave for unspecified keys.
     *
     * @param sound  The default Sound to set.
     * @param key    The default musical key to set.
     * @param octave The default octave to set.
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    TacticalMidiDrumKit defaultSound(@NotNull Sound sound, @NotNull TacticalMusicKey key, int octave);

    /**
     * Creates a new instance of TacticalMidiDrumKit.
     *
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    static TacticalMidiDrumKit create() {
        return TacticalMusic.getInstance().createDrumKit();
    }

    /**
     * Creates a new instance of TacticalMidiDrumKit based on default settings.
     *
     * @return This TacticalMidiDrumKit instance for method chaining.
     */
    @NotNull
    static TacticalMidiDrumKit createFromDefault() {
        return TacticalMusic.getInstance().createDefaultDrumKit();
    }

    /**
     * Represents a drum sound
     */
    interface TacticalDrumSound {
        /**
         * Retrieves the Sound associated with this drum sound.
         *
         * @return The associated Sound.
         */
        @NotNull
        Sound sound();

        /**
         * Retrieves the musical key associated with this drum sound.
         *
         * @return The associated musical key.
         */
        @NotNull
        TacticalMusicKey key();

        /**
         * Retrieves the octave associated with this drum sound.
         *
         * @return The associated octave.
         */
        int octave();
    }
}
