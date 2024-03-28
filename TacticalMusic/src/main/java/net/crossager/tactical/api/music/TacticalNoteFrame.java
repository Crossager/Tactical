package net.crossager.tactical.api.music;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Represents a frame containing a sequence of {@link TacticalNoteEvent}s.
 */
public interface TacticalNoteFrame {

    /**
     * Retrieves the list of tactical note events contained in this frame.
     *
     * @return The list of tactical note events.
     */
    @NotNull
    List<TacticalNoteEvent> tacticalNoteEvents();
}
