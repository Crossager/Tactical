package net.crossager.tactical.api.music;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface TacticalHighPitchHandler {
    TacticalHighPitchHandler SKIP = (event, options) -> Optional.empty();
    TacticalHighPitchHandler ALLOW = (event, options) -> Optional.of(event);

    @NotNull
    Optional<TacticalNoteEvent> handleEvent(@NotNull TacticalNoteEvent event, @NotNull TacticalMidiParsingOptions options);
}
