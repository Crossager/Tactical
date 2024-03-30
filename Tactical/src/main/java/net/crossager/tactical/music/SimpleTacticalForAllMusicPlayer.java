package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class SimpleTacticalForAllMusicPlayer extends SimpleTacticalMusicPlayer {
    private final Location locationToPlay;
    private final SoundCategory soundCategory;

    public SimpleTacticalForAllMusicPlayer(Location locationToPlay, TacticalMusicManager musicManager, TacticalNoteSequence tacticalNoteSequence, SoundCategory soundCategory) {
        super(musicManager, tacticalNoteSequence);
        this.locationToPlay = locationToPlay;
        this.soundCategory = soundCategory;
    }

    @Override
    protected void playSound(TacticalNoteEvent noteEvent) {
        locationToPlay.getWorld().playSound(locationToPlay, noteEvent.sound(), soundCategory, noteEvent.volume() * 3 * volume(), noteEvent.key().minecraftPitch(noteEvent.octave()));
    }
}
