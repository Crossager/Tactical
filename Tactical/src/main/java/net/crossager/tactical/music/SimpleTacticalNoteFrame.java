package net.crossager.tactical.music;

import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteFrame;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SimpleTacticalNoteFrame(List<TacticalNoteEvent> tacticalNoteEvents) implements TacticalNoteFrame {
    @Override
    public @NotNull List<TacticalNoteEvent> tacticalNoteEvents() {
        return tacticalNoteEvents;
    }
}
