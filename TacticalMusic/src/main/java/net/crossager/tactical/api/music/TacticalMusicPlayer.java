package net.crossager.tactical.api.music;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a player for TacticalMusic, capable of playing note sequences.
 */
public interface TacticalMusicPlayer {

    /**
     * Retrieves the note sequence being played by the player.
     *
     * @return The note sequence being played.
     */
    @NotNull
    TacticalNoteSequence noteSequence();

    /**
     * Retrieves the length of each tick in milliseconds.
     *
     * @return The length of each tick in milliseconds.
     */
    int tickLength();

    /**
     * Retrieves the remaining length of the current tick in milliseconds.
     *
     * @return The remaining length of the current tick in milliseconds.
     */
    int remainingTickLength();

    /**
     * Retrieves the current tick number.
     *
     * @return The current tick number.
     */
    int currentTick();

    /**
     * Sets the current tick number.
     *
     * @param currentTick The current tick number.
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer currentTick(int currentTick);

    /**
     * Pauses playback of the note sequence.
     *
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer pause();

    /**
     * Resumes playback of the note sequence.
     *
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer resume();

    /**
     * Checks if the player is currently playing.
     *
     * @return True if the player is currently playing, false otherwise.
     */
    boolean isPlaying();

    /**
     * Checks if the player is stopped.
     *
     * @return True if the player is stopped, false otherwise.
     */
    boolean isStopped();

    /**
     * Stops playback of the note sequence.
     *
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer stop();

    /**
     * Retrieves the volume of the player.
     *
     * @return The volume of the player.
     */
    float volume();

    /**
     * Sets the volume of the player.
     *
     * @param volume The volume of the player.
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer volume(float volume);

    /**
     * Registers a callback to be executed when the note sequence playback ends.
     *
     * @param onEnd The callback function to execute.
     * @return The TacticalMusicPlayer instance for method chaining.
     */
    @NotNull
    TacticalMusicPlayer onEnd(@NotNull Consumer<TacticalMusicPlayer> onEnd);
}
