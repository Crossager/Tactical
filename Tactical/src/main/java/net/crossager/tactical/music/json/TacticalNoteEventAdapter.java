package net.crossager.tactical.music.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.crossager.tactical.api.music.TacticalNoteEvent;
import net.crossager.tactical.music.SimpleTacticalNoteEvent;
import net.crossager.tactical.util.ObjectReadingTypeAdapter;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class TacticalNoteEventAdapter extends ObjectReadingTypeAdapter<TacticalNoteEvent> {
    public static final TacticalNoteEventAdapter INSTANCE = new TacticalNoteEventAdapter();

    public TacticalNoteEventAdapter() {
        super(SimpleTacticalNoteEvent.class, Map.of(
                "sound", in -> Registry.SOUNDS.get(Objects.requireNonNull(NamespacedKey.fromString(in.nextString()))),
                "isDrum", JsonReader::nextBoolean,
                "volume", in -> (float) in.nextDouble(),
                "key", JsonReader::nextInt
        ));
    }

    @Override
    public void write(JsonWriter out, TacticalNoteEvent value) throws IOException {
        out.beginObject();

        out.name("sound");
        out.value(value.sound().getKey().toString());
        out.name("isDrum");
        out.value(value.isDrum());
        out.name("volume");
        out.value(value.volume());
        out.name("key");
        out.value(value.rawKey());

        out.endObject();
    }

    @Override
    public TacticalNoteEvent read(JsonReader in) throws IOException {
        TacticalNoteEvent event = super.read(in);
        event.rawKey(event.rawKey());
        return event;
    }
}
