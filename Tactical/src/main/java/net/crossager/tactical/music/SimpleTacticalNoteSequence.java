package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalMusicPlayer;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteFrame;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleTacticalNoteSequence implements TacticalNoteSequence {
    private final TacticalMusicManager musicManager;
    private final List<TacticalNoteFrame> noteFrames;
    private final int tickSpacing;

    public SimpleTacticalNoteSequence(TacticalMusicManager musicManager, List<TacticalNoteFrame> noteFrames, int tickSpacing) {
        this.musicManager = musicManager;
        this.noteFrames = noteFrames;
        this.tickSpacing = tickSpacing;
    }

    @Override
    public @NotNull TacticalMusicPlayer playFor(@NotNull Player player, @NotNull SoundCategory soundCategory) {
        return new SimpleTacticalPlayerMusicPlayer(player, musicManager, this, soundCategory);
    }

    @Override
    public @NotNull TacticalMusicPlayer playForAll(@NotNull Location locationToPlay, @NotNull SoundCategory soundCategory) {
        return new SimpleTacticalForAllMusicPlayer(locationToPlay, musicManager, this, soundCategory);
    }

    @Override
    public int tickLength() {
        return noteFrames.size() * tickSpacing();
    }

    @Override
    public int tickSpacing() {
        return tickSpacing;
    }

    @Override
    public int bpm() {
        return 300 / tickSpacing;
    }

    @Override
    public @NotNull List<TacticalNoteEvent> getNoteEventsForTick(int tick) {
        if (tick % tickSpacing != 0) return List.of();
        return noteFrames.get(tick / tickSpacing).tacticalNoteEvents();
    }

    @Override
    public @NotNull List<TacticalNoteFrame> getAllNoteFrames() {
        return noteFrames;
    }
}
