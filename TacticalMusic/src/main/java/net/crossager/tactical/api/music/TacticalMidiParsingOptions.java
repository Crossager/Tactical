package net.crossager.tactical.api.music;

import net.crossager.tactical.api.TacticalMusic;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Represents options for parsing MIDI files in TacticalMusic.
 */
public interface TacticalMidiParsingOptions {

    /**
     * Retrieves the maximum pitch for parsed notes.
     *
     * @return The maximum pitch for parsed notes.
     */
    float maxPitch();

    /**
     * Sets the maximum pitch for parsed notes.
     *
     * @param maxPitch The maximum pitch for parsed notes.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions maxPitch(float maxPitch);

    /**
     * Checks if octaves should be moved when parsing notes.
     *
     * @return True if octaves should be moved, false otherwise.
     */
    boolean moveOctaves();

    /**
     * Sets whether octaves should be moved when parsing notes.
     *
     * @param moveOctaves True to move octaves, false otherwise.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions moveOctaves(boolean moveOctaves);

    /**
     * Checks if keys should be moved when parsing notes.
     *
     * @return True if keys should be moved, false otherwise.
     */
    boolean moveKeys();

    /**
     * Sets whether keys should be moved when parsing notes.
     *
     * @param moveKeys True to move keys, false otherwise.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions moveKeys(boolean moveKeys);

    /**
     * Checks if unknown instruments should be skipped when parsing notes.
     *
     * @return True if unknown instruments should be skipped, false otherwise.
     */
    boolean skipUnknownInstruments();

    /**
     * Sets whether unknown instruments should be skipped when parsing notes.
     *
     * @param skipUnknownInstruments True to skip unknown instruments, false otherwise.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions skipUnknownInstruments(boolean skipUnknownInstruments);

    /**
     * Checks if warnings should be generated during parsing.
     *
     * @return True if warnings should be generated, false otherwise.
     */
    boolean warnings();

    /**
     * Sets whether warnings should be generated during parsing.
     *
     * @param warnings True to generate warnings, false otherwise.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions warnings(boolean warnings);

    /**
     * Retrieves the Sound associated with the specified MIDI instrument.
     *
     * @param midiInstrument The MIDI instrument for which to retrieve the Sound.
     * @return The Sound associated with the MIDI instrument.
     */
    @NotNull
    String soundForInstrument(@NotNull String midiInstrument);

    /**
     * Associates the specified Sound with the given MIDI instrument.
     *
     * @param midiInstrument The MIDI instrument to associate with the Sound.
     * @param sound          The Sound to associate with the MIDI instrument.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions soundForInstrument(@NotNull String midiInstrument, @NotNull Sound sound);

    /**
     * Sets the default Sound for unspecified MIDI instruments.
     *
     * @param sound The default Sound to set.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions defaultSound(@NotNull Sound sound);

    /**
     * Associates the specified Sound with the given MIDI instrument.
     *
     * @param midiInstrument The MIDI instrument to associate with the Sound.
     * @param sound          The Sound to associate with the MIDI instrument.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions soundForInstrument(@NotNull String midiInstrument, @NotNull String sound);

    /**
     * Sets the default Sound for unspecified MIDI instruments.
     *
     * @param sound The default Sound to set.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions defaultSound(@NotNull String sound);

    /**
     * Removes the default Sound for unspecified MIDI instruments.
     *
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions removeDefaultSound();

    /**
     * Retrieves the drum kit associated with the specified MIDI instrument.
     *
     * @param midiInstrument The MIDI instrument for which to retrieve the drum kit.
     * @return The drum kit associated with the MIDI instrument.
     */
    @NotNull
    TacticalMidiDrumKit drumKitForInstrument(@NotNull String midiInstrument);

    /**
     * Associates the specified drum kit with the given MIDI instrument.
     *
     * @param midiInstrument The MIDI instrument to associate with the drum kit.
     * @param drumKit        The drum kit to associate with the MIDI instrument.
     * @return The TacticalMidiParsingOptions instance for method chaining.
     */
    @NotNull
    TacticalMidiParsingOptions drumKitForInstrument(@NotNull String midiInstrument, @NotNull TacticalMidiDrumKit drumKit);

    /**
     * Creates a TacticalNoteEvent with the specified MIDI instrument, key, and volume.
     *
     * @param midiInstrument The MIDI instrument for the note event.
     * @param key            The key of the note.
     * @param volume         The volume of the note.
     * @return The created TacticalNoteEvent.
     */
    @NotNull
    TacticalNoteEvent createEvent(@NotNull String midiInstrument, int key, float volume);

    /**
     * Creates a new instance of TacticalMidiParsingOptions.
     *
     * @return A new instance of TacticalMidiParsingOptions.
     */
    @NotNull
    static TacticalMidiParsingOptions create() {
        return TacticalMusic.getInstance().createMidiParsingOptions();
    }

    /**
     * Creates a new instance of TacticalMidiParsingOptions based on default settings.
     *
     * @return A new instance of TacticalMidiParsingOptions with default settings.
     */
    @NotNull
    static TacticalMidiParsingOptions createFromDefault() {
        return TacticalMusic.getInstance().createDefaultMidiParsingOptions();
    }
}
