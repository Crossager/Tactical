package net.crossager.tactical.music.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.api.music.TacticalNoteFrame;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import net.crossager.tactical.music.SimpleTacticalNoteFrame;
import net.crossager.tactical.music.SimpleTacticalNoteSequence;
import net.crossager.tactical.music.TacticalMusicManager;
import net.crossager.tactical.util.ListAdapter;
import net.crossager.tactical.util.ObjectReadingTypeAdapter;

import java.io.IOException;
import java.util.Map;

public class TacticalNoteSequenceAdapter extends ObjectReadingTypeAdapter<TacticalNoteSequence> {
    public TacticalNoteSequenceAdapter(TacticalMusicManager musicManager) {
        super(SimpleTacticalNoteSequence.class, Map.of(
                "tickSpacing", JsonReader::nextInt,
                "noteFrames", ListAdapter.read(i -> new SimpleTacticalNoteFrame(ListAdapter.read(TacticalNoteEventAdapter.INSTANCE::read).read(i)))
        ), Map.of(
                "musicManager", i -> musicManager
        ));
    }

    @Override
    public void write(JsonWriter out, TacticalNoteSequence value) throws IOException {
        out.beginObject();
        out.name("tickSpacing");
        out.value(value.tickSpacing());
        out.name("noteFrames");
        out.beginArray();

        for (TacticalNoteFrame noteFrame : value.getAllNoteFrames()) {
            out.beginArray();

            for (TacticalNoteEvent noteEvent : noteFrame.tacticalNoteEvents()) {
                TacticalNoteEventAdapter.INSTANCE.write(out, noteEvent);
            }

            out.endArray();
        }

        out.endArray();
        out.endObject();
    }
}
