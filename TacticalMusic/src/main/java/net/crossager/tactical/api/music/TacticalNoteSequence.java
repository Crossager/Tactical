package net.crossager.tactical.api.music;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a sequence of notes in TacticalMusic.
 */
public interface TacticalNoteSequence {

    /**
     * Plays the note sequence for a specific player.
     *
     * @param player        The player to play the sequence for.
     * @param soundCategory The sound category to use for playback.
     * @return The TacticalMusicPlayer instance responsible for playing the note sequence for the player.
     */
    @NotNull
    TacticalMusicPlayer playFor(@NotNull Player player, @NotNull SoundCategory soundCategory);

    /**
     * Plays the note sequence for all players at a specified location.
     *
     * @param locationToPlay The location at which to play the sequence.
     * @param soundCategory  The sound category to use for playback.
     * @return The TacticalMusicPlayer instance responsible for playing the note sequence for all players.
     */
    @NotNull
    TacticalMusicPlayer playForAll(@NotNull Location locationToPlay, @NotNull SoundCategory soundCategory);

    /**
     * Retrieves the length of each tick in milliseconds.
     *
     * @return The length of each tick in milliseconds.
     */
    int tickLength();

    /**
     * Retrieves the spacing between ticks in milliseconds.
     *
     * @return The spacing between ticks in milliseconds.
     */
    int tickSpacing();

    /**
     * Retrieves the beats per minute (BPM) of the note sequence.
     *
     * @return The beats per minute (BPM) of the note sequence.
     */
    int bpm();

    /**
     * Retrieves the list of note events scheduled to occur at the specified tick.
     *
     * @param tick The tick for which to retrieve the note events.
     * @return The list of note events scheduled for the specified tick.
     */
    @NotNull
    List<TacticalNoteEvent> getNoteEventsForTick(int tick);

    /**
     * Retrieves all note frames comprising the note sequence.
     *
     * @return The list of all note frames in the note sequence.
     */
    @NotNull
    List<TacticalNoteFrame> getAllNoteFrames();
}
