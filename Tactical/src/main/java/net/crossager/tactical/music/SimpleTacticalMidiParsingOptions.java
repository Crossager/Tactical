package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalMusicKey;
import net.crossager.tactical.api.music.TacticalMidiDrumKit;
import net.crossager.tactical.api.music.TacticalMidiParsingOptions;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SimpleTacticalMidiParsingOptions implements TacticalMidiParsingOptions {
    private final Map<String, String> sounds = new HashMap<>();
    private String defaultSound;
    private final Map<String, TacticalMidiDrumKit> drumKits = new HashMap<>();
    private boolean moveOctaves = false;
    private boolean moveKeys = false;
    private boolean warnings = true;
    private boolean skipUnknownInstruments = true;
    private float maxPitch = 2;

    @Override
    public float maxPitch() {
        return maxPitch;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions maxPitch(float maxPitch) {
        this.maxPitch = maxPitch;
        return this;
    }

    @Override
    public boolean moveOctaves() {
        return moveOctaves;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions moveOctaves(boolean moveOctaves) {
        this.moveOctaves = moveOctaves;
        return this;
    }

    @Override
    public boolean moveKeys() {
        return moveKeys;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions moveKeys(boolean moveKeys) {
        this.moveKeys = moveKeys;
        return this;
    }

    @Override
    public boolean skipUnknownInstruments() {
        return skipUnknownInstruments;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions skipUnknownInstruments(boolean skipUnknownInstruments) {
        this.skipUnknownInstruments = skipUnknownInstruments;
        return this;
    }

    @Override
    public boolean warnings() {
        return warnings;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions warnings(boolean warnings) {
        this.warnings = warnings;
        return this;
    }

    @Override
    public @NotNull String soundForInstrument(@NotNull String midiInstrument) {
        String sound = sounds.getOrDefault(midiInstrument, defaultSound);
        if (sound == null) throw new NoSuchElementException("No sound bound for instrument '" + midiInstrument + "'");
        return sound;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions soundForInstrument(@NotNull String midiInstrument, @NotNull Sound sound) {
        sounds.put(midiInstrument, sound.getKey().toString());
        return this;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions defaultSound(@NotNull Sound sound) {
        this.defaultSound = sound.getKey().toString();
        return this;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions soundForInstrument(@NotNull String midiInstrument, @NotNull String sound) {
        sounds.put(midiInstrument, sound);
        return this;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions defaultSound(@NotNull String sound) {
        this.defaultSound = sound;
        return this;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions removeDefaultSound() {
        this.defaultSound = null;
        return this;
    }

    @Override
    public @NotNull TacticalMidiDrumKit drumKitForInstrument(@NotNull String midiInstrument) {
        TacticalMidiDrumKit drumKit = drumKits.get(midiInstrument);
        if (drumKit == null) throw new NoSuchElementException("No drum kit bound for instrument '" + midiInstrument + "'");
        return drumKit;
    }

    @Override
    public @NotNull TacticalMidiParsingOptions drumKitForInstrument(@NotNull String midiInstrument, @NotNull TacticalMidiDrumKit drumKit) {
        drumKits.put(midiInstrument, drumKit);
        return this;
    }

    @Override
    public @NotNull TacticalNoteEvent createEvent(@NotNull String midiInstrument, int key, float volume) {
        String availableSound = sounds.get(midiInstrument);
        if (availableSound != null)
            return new SimpleTacticalNoteEvent(availableSound, volume, key);

        TacticalMidiDrumKit drumKit = drumKits.get(midiInstrument);
        if (drumKit == null) {
            if (defaultSound == null)
                throw new NoSuchElementException("No instrument bound for instrument '" + midiInstrument + "'");
            return new SimpleTacticalNoteEvent(defaultSound, volume, key);
        }

        TacticalMidiDrumKit.TacticalDrumSound drumSound = drumKit.soundForKey(key);

        return new SimpleTacticalNoteEvent(drumSound.sound(), volume, TacticalMusicKey.C, 1, true);
    }

    public static SimpleTacticalMidiParsingOptions createDefault() {
        SimpleTacticalMidiParsingOptions options = new SimpleTacticalMidiParsingOptions();
        options.moveOctaves(true);
        options.defaultSound(Sound.BLOCK_NOTE_BLOCK_HARP);

        options.soundForInstrument("MIDI", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Grand Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Grand Piano 1", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Acoustic Grand Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Electric Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Electric Piano 1", Sound.BLOCK_NOTE_BLOCK_HARP);
        options.soundForInstrument("Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Acoustic Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Acoustic Bass 1", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Jazz Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Electric Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Bass Guitar", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Bass Guitar (Classic)", Sound.BLOCK_NOTE_BLOCK_BASS);
        options.soundForInstrument("Vibraphone", Sound.BLOCK_NOTE_BLOCK_BELL);
        options.soundForInstrument("Xylophone", Sound.BLOCK_NOTE_BLOCK_XYLOPHONE);
        options.soundForInstrument("Violin", Sound.BLOCK_NOTE_BLOCK_CHIME);
        options.soundForInstrument("8-Bit Sine", Sound.BLOCK_NOTE_BLOCK_BIT);
        options.soundForInstrument("8-Bit Square", Sound.BLOCK_NOTE_BLOCK_BIT);
        options.soundForInstrument("8-Bit Sawtooth", Sound.BLOCK_NOTE_BLOCK_BIT);
        options.soundForInstrument("8-Bit Triangle", Sound.BLOCK_NOTE_BLOCK_BIT);
        options.soundForInstrument("Flute", Sound.BLOCK_NOTE_BLOCK_FLUTE);
        options.soundForInstrument("Concert Harp", Sound.BLOCK_NOTE_BLOCK_FLUTE);
        options.soundForInstrument("French Horn", Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO);
        options.soundForInstrument("Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
        options.soundForInstrument("Electric Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
        options.soundForInstrument("Acoustic Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
        options.soundForInstrument("Jazz Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
        options.soundForInstrument("Sitar", Sound.BLOCK_NOTE_BLOCK_BANJO);
        options.soundForInstrument("Music Box", Sound.BLOCK_NOTE_BLOCK_BANJO);
        options.soundForInstrument("Steel Drums", Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE);
        options.soundForInstrument("Synth Pluck", Sound.BLOCK_NOTE_BLOCK_PLING);
        options.soundForInstrument("Smooth Synth", Sound.BLOCK_NOTE_BLOCK_PLING);

        options.drumKitForInstrument("Drum Kit", TacticalMidiDrumKit.createFromDefault());
        options.drumKitForInstrument("Electric Drum Kit", TacticalMidiDrumKit.createFromDefault());

        return options;
    }
}
