package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class SimpleTacticalPlayerMusicPlayer extends SimpleTacticalMusicPlayer {
    private final Player player;
    private final SoundCategory soundCategory;

    public SimpleTacticalPlayerMusicPlayer(Player player, TacticalMusicManager musicManager, TacticalNoteSequence tacticalNoteSequence, SoundCategory soundCategory) {
        super(musicManager, tacticalNoteSequence);
        this.player = player;
        this.soundCategory = soundCategory;
    }

    @Override
    protected void playSound(TacticalNoteEvent noteEvent) {
        player.playSound(player.getLocation().add(0, -3, 0), noteEvent.sound(), soundCategory, noteEvent.volume() * 3 * volume(), noteEvent.key().minecraftPitch(noteEvent.octave()));
    }
}
