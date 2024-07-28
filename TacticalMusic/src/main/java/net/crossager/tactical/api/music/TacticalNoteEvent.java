package net.crossager.tactical.api.music;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event that contains information about a musical note.
 */
public interface TacticalNoteEvent {

    /**
     * Retrieves the sound associated with the note event.
     *
     * @return The sound of the note event.
     */
    @NotNull
    String sound();

    /**
     * Checks if the note event represents a drum sound.
     *
     * @return True if the note event represents a drum sound, otherwise false.
     */
    boolean isDrum();

    /**
     * Sets the sound for the note event.
     *
     * @param sound The new sound for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent sound(@NotNull Sound sound);

    /**
     * Sets the sound for the note event.
     *
     * @param sound The new sound for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent sound(@NotNull String sound);

    /**
     * Retrieves the volume of the note event.
     *
     * @return The volume of the note event.
     */
    float volume();

    /**
     * Sets the volume for the note event.
     *
     * @param volume The new volume for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent volume(float volume);

    /**
     * Retrieves the musical key associated with the note event.
     *
     * @return The musical key of the note event.
     */
    @NotNull
    TacticalMusicKey key();

    /**
     * Sets the musical key for the note event.
     *
     * @param tacticalMusicKey The new musical key for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent key(@NotNull TacticalMusicKey tacticalMusicKey);

    /**
     * Retrieves the raw key value of the note event.
     *
     * @return The raw key value of the note event.
     */
    int rawKey();

    /**
     * Sets the raw key value for the note event.
     *
     * @param rawKey The new raw key value for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent rawKey(int rawKey);

    /**
     * Retrieves the octave of the note event.
     *
     * @return The octave of the note event.
     */
    int octave();

    /**
     * Sets the octave for the note event.
     *
     * @param octave The new octave for the note event.
     * @return This TacticalNoteEvent instance for method chaining.
     */
    @NotNull
    TacticalNoteEvent octave(int octave);
}
