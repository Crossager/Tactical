package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalMidiDrumKit;
import net.crossager.tactical.api.music.TacticalMusicKey;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SimpleTacticalMidiDrumKit implements TacticalMidiDrumKit {
    private final Map<Integer, TacticalDrumSound> sounds = new HashMap<>();
    private TacticalDrumSound defaultSound;

    @Override
    public @NotNull TacticalDrumSound soundForKey(int mappedKey) {
        TacticalDrumSound sound = sounds.getOrDefault(mappedKey, defaultSound);
        if (sound == null) throw new NoSuchElementException("No drum sound mapped for key '" + mappedKey + "'");
        return sound;
    }

    @Override
    public @NotNull TacticalMidiDrumKit defaultSound(@NotNull Sound sound, @NotNull TacticalMusicKey key, int octave) {
        this.defaultSound = new SimpleTacticalDrumSound(sound.getKey().toString(), key, octave);
        return this;
    }

    @Override
    public @NotNull TacticalMidiDrumKit defaultSound(@NotNull String sound, @NotNull TacticalMusicKey key, int octave) {
        this.defaultSound = new SimpleTacticalDrumSound(sound, key, octave);
        return this;
    }

    @Override
    public @NotNull TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull Sound sound) {
        return soundForKey(mappedKey, sound, TacticalMusicKey.BASE, 0);
    }

    @Override
    public @NotNull TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull Sound sound, @NotNull TacticalMusicKey key, int octave) {
        sounds.put(mappedKey, new SimpleTacticalDrumSound(sound.getKey().toString(), key, octave));
        return this;
    }

    @Override
    public @NotNull TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull String sound) {
        return soundForKey(mappedKey, sound, TacticalMusicKey.BASE, 0);
    }

    @Override
    public @NotNull TacticalMidiDrumKit soundForKey(int mappedKey, @NotNull String sound, @NotNull TacticalMusicKey key, int octave) {
        sounds.put(mappedKey, new SimpleTacticalDrumSound(sound, key, octave));
        return this;
    }

    record SimpleTacticalDrumSound(@NotNull String sound, @NotNull TacticalMusicKey key, int octave) implements TacticalDrumSound {

    }

    public static SimpleTacticalMidiDrumKit createDefault() {
        SimpleTacticalMidiDrumKit drumKit = new SimpleTacticalMidiDrumKit();

        drumKit.defaultSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, TacticalMusicKey.BASE, 0);

        drumKit.soundForKey(5, Sound.ENTITY_ITEM_FRAME_ROTATE_ITEM);
        drumKit.soundForKey(9, Sound.BLOCK_CALCITE_STEP);
        drumKit.soundForKey(11, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
        drumKit.soundForKey(12, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
        drumKit.soundForKey(13, Sound.BLOCK_CALCITE_STEP);
        drumKit.soundForKey(14, Sound.BLOCK_NOTE_BLOCK_SNARE);
        drumKit.soundForKey(15, Sound.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
        drumKit.soundForKey(16, Sound.BLOCK_NOTE_BLOCK_SNARE);
        drumKit.soundForKey(17, Sound.ENTITY_PLAYER_HURT);
        drumKit.soundForKey(18, Sound.BLOCK_NOTE_BLOCK_HAT);
        drumKit.soundForKey(20, Sound.ENTITY_CHICKEN_STEP);
        drumKit.soundForKey(22, Sound.BLOCK_NOTE_BLOCK_HAT);
        drumKit.soundForKey(29, Sound.ENTITY_ENDER_EYE_DEATH);
        drumKit.soundForKey(32, Sound.BLOCK_NOTE_BLOCK_COW_BELL);
        drumKit.soundForKey(45, Sound.BLOCK_AZALEA_LEAVES_FALL);
        drumKit.soundForKey(46, Sound.BLOCK_AZALEA_LEAVES_FALL);

        drumKit.soundForKey(7, Sound.BLOCK_CALCITE_STEP);

        drumKit.soundForKey(4, Sound.ENTITY_ITEM_FRAME_ROTATE_ITEM);
        drumKit.soundForKey(8, Sound.BLOCK_CALCITE_STEP);
        drumKit.soundForKey(10, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
        drumKit.soundForKey(11, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
        drumKit.soundForKey(12, Sound.BLOCK_CALCITE_STEP);
        drumKit.soundForKey(13, Sound.BLOCK_NOTE_BLOCK_SNARE);
        drumKit.soundForKey(14, Sound.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
        drumKit.soundForKey(15, Sound.BLOCK_NOTE_BLOCK_SNARE);
        drumKit.soundForKey(16, Sound.ENTITY_PLAYER_HURT);
        drumKit.soundForKey(17, Sound.BLOCK_NOTE_BLOCK_HAT);
        drumKit.soundForKey(19, Sound.ENTITY_CHICKEN_STEP);
        drumKit.soundForKey(21, Sound.BLOCK_NOTE_BLOCK_HAT);
        drumKit.soundForKey(28, Sound.ENTITY_ENDER_EYE_DEATH);
        drumKit.soundForKey(31, Sound.BLOCK_NOTE_BLOCK_COW_BELL);
        drumKit.soundForKey(44, Sound.BLOCK_AZALEA_LEAVES_FALL);
        drumKit.soundForKey(45, Sound.BLOCK_AZALEA_LEAVES_FALL);

        return drumKit;
    }
}
