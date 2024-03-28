package net.crossager.tactical.api;

import net.crossager.tactical.api.music.MidiParsingException;
import net.crossager.tactical.api.music.TacticalMidiDrumKit;
import net.crossager.tactical.api.music.TacticalMidiParsingOptions;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Represents the Tactical Music API for handling music-related operations.
 */
public interface TacticalMusic {

    /**
     * Loads a note sequence from a JSON file.
     *
     * @param path The path to the JSON file.
     * @return The loaded TacticalNoteSequence.
     * @throws IOException If an I/O error occurs.
     */
    @NotNull
    TacticalNoteSequence loadFromJsonFile(@NotNull Path path) throws IOException;

    /**
     * Loads a note sequence from JSON data.
     *
     * @param json The JSON data representing the note sequence.
     * @return The loaded TacticalNoteSequence.
     */
    @NotNull
    TacticalNoteSequence loadFromJson(@NotNull String json);

    /**
     * Saves a note sequence to a JSON file.
     *
     * @param sequence The note sequence to save.
     * @param path     The path to save the JSON file.
     * @return The JSON data representing the saved note sequence.
     * @throws IOException If an I/O error occurs.
     */
    @NotNull
    String saveToJsonFile(@NotNull TacticalNoteSequence sequence, @NotNull Path path) throws IOException;

    /**
     * Converts a note sequence to JSON data.
     *
     * @param sequence The note sequence to convert.
     * @return The JSON data representing the note sequence.
     */
    @NotNull
    String saveToJson(@NotNull TacticalNoteSequence sequence);

    /**
     * Loads a note sequence from a MIDI file with custom parsing options.
     *
     * @param path                    The path to the MIDI file.
     * @param tacticalMidiParsingOptions The parsing options for loading the MIDI file.
     * @return The loaded TacticalNoteSequence.
     * @throws MidiParsingException If an error occurs during MIDI parsing.
     */
    @NotNull
    TacticalNoteSequence loadFromMidiFile(@NotNull Path path, @NotNull TacticalMidiParsingOptions tacticalMidiParsingOptions) throws MidiParsingException;

    /**
     * Loads a note sequence from a MIDI file located at the specified URL with custom parsing options.
     *
     * @param url                     The URL of the MIDI file.
     * @param tacticalMidiParsingOptions The parsing options for loading the MIDI file.
     * @return The loaded TacticalNoteSequence.
     * @throws MidiParsingException If an error occurs during MIDI parsing.
     */
    @NotNull
    TacticalNoteSequence loadFromMidiFile(@NotNull URL url, @NotNull TacticalMidiParsingOptions tacticalMidiParsingOptions) throws MidiParsingException;

    /**
     * Creates default MIDI parsing options.
     *
     * @return The default MIDI parsing options.
     */
    @NotNull
    TacticalMidiParsingOptions createDefaultMidiParsingOptions();

    /**
     * Creates custom MIDI parsing options.
     *
     * @return The custom MIDI parsing options.
     */
    @NotNull
    TacticalMidiParsingOptions createMidiParsingOptions();

    /**
     * Creates a MIDI drum kit.
     *
     * @return The created MIDI drum kit.
     */
    @NotNull
    TacticalMidiDrumKit createDrumKit();

    /**
     * Creates a default MIDI drum kit.
     *
     * @return The created default MIDI drum kit.
     */
    @NotNull
    TacticalMidiDrumKit createDefaultDrumKit();

    /**
     * Retrieves the singleton instance of the TacticalMusic API.
     *
     * @return The singleton instance of the TacticalMusic API.
     */
    @NotNull
    static TacticalMusic getInstance() {
        return MusicAPIHolder.getInstance();
    }
}
