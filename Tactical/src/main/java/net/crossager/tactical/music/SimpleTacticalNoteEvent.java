package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalMusicKey;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public final class SimpleTacticalNoteEvent implements TacticalNoteEvent {
    private Sound sound;
    private float volume;
    private TacticalMusicKey tacticalMusicKey;
    private int octave;
    private int key;
    private final boolean isDrum;

    public SimpleTacticalNoteEvent(@NotNull Sound sound, float volume, TacticalMusicKey tacticalMusicKey, int octave, boolean isDrum) {
        this.sound = sound;
        this.volume = volume;
        this.tacticalMusicKey = tacticalMusicKey;
        octave(octave);
        this.isDrum = isDrum;
    }

    public SimpleTacticalNoteEvent(@NotNull Sound sound, float volume, int key) {
        this.sound = sound;
        this.volume = volume;
        this.isDrum = false;
        rawKey(key);
    }

    public SimpleTacticalNoteEvent(@NotNull Sound sound, float volume, int key, boolean isDrum) {
        this.sound = sound;
        this.volume = volume;
        this.isDrum = isDrum;
        rawKey(key);
    }

    @Override
    public @NotNull TacticalNoteEvent sound(@NotNull Sound sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public @NotNull TacticalNoteEvent volume(float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public @NotNull TacticalMusicKey key() {
        return tacticalMusicKey;
    }

    @Override
    public @NotNull TacticalNoteEvent key(@NotNull TacticalMusicKey tacticalMusicKey) {
        this.tacticalMusicKey = tacticalMusicKey;
        this.key = octave * 12 + tacticalMusicKey.ordinal();
        return this;
    }

    @Override
    public int rawKey() {
        return key;
    }

    @Override
    public @NotNull TacticalNoteEvent rawKey(int key) {
        this.tacticalMusicKey = TacticalMusicKey.fromKey(key % 12);
        this.octave = key / 12;
        this.key = key;
        return this;
    }

    @Override
    public int octave() {
        return octave;
    }

    @Override
    public @NotNull TacticalNoteEvent octave(int octave) {
        this.octave = octave;
        this.key = octave * 12 + tacticalMusicKey.ordinal();
        return this;
    }

    @Override
    public @NotNull Sound sound() {
        return sound;
    }

    @Override
    public boolean isDrum() {
        return this.isDrum;
    }

    @Override
    public float volume() {
        return volume;
    }

    @Override
    public String toString() {
        return "SimpleNoteEvent{" +
                "sound=" + sound +
                ", volume=" + volume +
                ", keyNote=" + tacticalMusicKey +
                ", octave=" + octave +
                ", key=" + key +
                '}';
    }


}
