package net.crossager.tactical.music;

import net.crossager.tactical.api.music.NotPlayingException;
import net.crossager.tactical.api.music.TacticalMusicPlayer;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public abstract class SimpleTacticalMusicPlayer implements TacticalMusicPlayer {
    private final TacticalMusicManager musicManager;
    private final TacticalNoteSequence tacticalNoteSequence;
    private int currentTick = 0;
    private float volume = 1F;
    private boolean isPlaying = false;
    private boolean isStopped = false;
    private BukkitTask associatedTask;
    private Consumer<TacticalMusicPlayer> onEnd = a -> {};

    public SimpleTacticalMusicPlayer(TacticalMusicManager musicManager, TacticalNoteSequence tacticalNoteSequence) {
        this.musicManager = musicManager;
        this.tacticalNoteSequence = tacticalNoteSequence;
        resume();
    }

    @Override
    public @NotNull TacticalNoteSequence noteSequence() {
        return tacticalNoteSequence;
    }

    @Override
    public int tickLength() {
        return tacticalNoteSequence.tickLength();
    }

    @Override
    public int remainingTickLength() {
        return tickLength() - currentTick();
    }

    @Override
    public int currentTick() {
        return this.currentTick;
    }

    @Override
    public @NotNull TacticalMusicPlayer currentTick(int currentTick) {
        validateNotStopped();
        this.currentTick = currentTick;
        return this;
    }

    @Override
    public @NotNull TacticalMusicPlayer pause() {
        validateNotStopped();
        if (!this.isPlaying) return this;
        this.isPlaying = false;
        if (associatedTask != null) {
            associatedTask.cancel();
            associatedTask = null;
        }
        return this;
    }

    @Override
    public @NotNull TacticalMusicPlayer resume() {
        validateNotStopped();
        if (this.isPlaying) return this;
        this.isPlaying = true;
        if (associatedTask != null) throw new IllegalStateException("Tried to resume player, but task is not null");
        associatedTask = musicManager.createTask(this::step, 0, 1);
        return this;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying && !isStopped;
    }

    @Override
    public boolean isStopped() {
        return isStopped;
    }

    @Override
    public @NotNull TacticalMusicPlayer stop() {
        validateNotStopped();
        this.isStopped = true;
        if (associatedTask != null) {
            associatedTask.cancel();
            associatedTask = null;
        }
        return this;
    }

    @Override
    public float volume() {
        return volume;
    }

    @Override
    public @NotNull TacticalMusicPlayer volume(float volume) {
        validateNotStopped();
        this.volume = volume;
        return this;
    }

    @Override
    public @NotNull TacticalMusicPlayer onEnd(@NotNull Consumer<TacticalMusicPlayer> onEnd) {
        this.onEnd = onEnd;
        return this;
    }

    protected void validateNotStopped() {
        if (isStopped) throw new NotPlayingException();
    }


    protected void step() {
        if (currentTick % tacticalNoteSequence.tickSpacing() == 0) {
            List<TacticalNoteEvent> tacticalNoteEvents = tacticalNoteSequence.getNoteEventsForTick(currentTick);
            tacticalNoteEvents.forEach(this::playSound);
        }
        currentTick += 1;
        if (currentTick >= tacticalNoteSequence.tickLength()) {
            stop();
            onEnd.accept(this);
        }
    }

    protected abstract void playSound(TacticalNoteEvent noteEvent);
}
