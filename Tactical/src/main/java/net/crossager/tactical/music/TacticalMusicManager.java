package net.crossager.tactical.music;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.crossager.tactical.Tactical;
import net.crossager.tactical.api.music.*;
import net.crossager.tactical.music.json.TacticalNoteSequenceAdapter;
import net.crossager.tactical.util.BaseAdapterFactory;
import net.crossager.tactical.util.InternalUtils;
import org.bukkit.Note;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.sound.midi.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.sound.midi.ShortMessage.NOTE_ON;

public class TacticalMusicManager {
    public static final int TPS = 20;
    public static float noteToPitch(Note note) {
        return keyToPitch(note.getId());
    }
    public static float keyToPitch(int key) {
        return (float) Math.pow(2.0, ((double)key - 12.0) / 12.0);
    }

    private final JavaPlugin plugin;
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new BaseAdapterFactory(TacticalNoteSequence.class, new TacticalNoteSequenceAdapter(this))).create();

    public TacticalMusicManager() {
        this(null);
    }

    public TacticalMusicManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask createTask(Runnable callback, long ticksFromNow, long tickSpacing) {
        if (plugin == null) throw new IllegalStateException("This feature of TacticalMusic is not available without a registered plugin");
        return plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, callback, ticksFromNow, tickSpacing);
    }

    public TacticalNoteSequence loadFromJson(String json) {
        return gson.fromJson(json, TacticalNoteSequence.class);
    }

    public String saveToJson(TacticalNoteSequence sequence) {
        return gson.toJson(sequence);
    }

    public TacticalNoteSequence loadFromMidi(Sequence sequence, TacticalMidiParsingOptions options) throws Exception {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setSequence(sequence);
        int currentTempo = (int) ((60 * 20F / Math.round(20 * 60 / sequencer.getTempoInBPM() / 4)) / 4);

        if (sequence.getDivisionType() != Sequence.PPQ)
            throw new IllegalStateException("This sequence type is not supported: Only PPQ (0.0) is supported, found " + sequence.getDivisionType());

        double ticksPerBeat = sequence.getResolution();
        double secondsPerTick = 60 / (ticksPerBeat * currentTempo);

        Track[] tracks = sequence.getTracks();
        Map<TacticalNoteEvent, Integer> unsortedNotes = new HashMap<>();
        int lowestKey = Integer.MAX_VALUE;
        Set<String> unknownInstruments = new HashSet<>();
        for (Track track : tracks) {
            List<TacticalNoteEvent> trackNotes = new ArrayList<>();
            int lowestOctave = Integer.MAX_VALUE;
            String currentSound = null;
            for (int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
                MidiEvent event = track.get(eventIndex);
                if (event.getMessage() instanceof ShortMessage message) {
                    if (currentSound == null)
                        throw new MidiParsingException("Note declaration before sound declaration", null);
                    if (message.getCommand() != NOTE_ON || message.getData2() == 0) continue;
                    int key = message.getData1() - 24;
                    try {
                        TacticalNoteEvent tacticalNoteEvent = options.createEvent(currentSound, key, message.getData2() / 127F);
                        if (!tacticalNoteEvent.isDrum() && tacticalNoteEvent.octave() < lowestOctave) lowestOctave = tacticalNoteEvent.octave();
                        if (!tacticalNoteEvent.isDrum() && key < lowestKey) lowestKey = key;
                        unsortedNotes.put(tacticalNoteEvent, (int) Math.round(event.getTick() * secondsPerTick * TPS));
                        if (options.moveOctaves()) trackNotes.add(tacticalNoteEvent);
                    } catch (NoSuchElementException e) {
                        if (!options.skipUnknownInstruments()) throw e;
                        unknownInstruments.add(currentSound);
                    }
                } else if (event.getMessage() instanceof MetaMessage message) {
                    if (message.getType() == 3)
                        currentSound = new String(message.getData());
                } else {
                    throw new RuntimeException(event.getMessage().toString());
                }
            }
            if (options.moveOctaves() && !options.moveKeys() && lowestOctave != 0 && trackNotes.size() > 0 && !trackNotes.get(0).isDrum()) {
                int moveOctaves = lowestOctave;
                trackNotes.forEach(event -> {
                    event.octave(event.octave() - moveOctaves);
                });
            }
        }
        if (options.moveKeys() && lowestKey != 0) {
            int moveKeys = lowestKey;
            unsortedNotes.forEach((event, i) -> {
                if (!event.isDrum())
                    event.rawKey(event.rawKey() - moveKeys);
            });
        }

        int tickLength = (int) (sequence.getTickLength() * secondsPerTick * TPS);
        int tickSpacing = (int) (ticksPerBeat * secondsPerTick * TPS / 4);
        int frameAmount = (int) Math.ceil((float) tickLength / tickSpacing);
        List<TacticalNoteFrame> noteFrames = InternalUtils.createFillList(frameAmount, () -> new SimpleTacticalNoteFrame(new ArrayList<>()));

        AtomicInteger highPitchNotes = new AtomicInteger();
        unsortedNotes.forEach(((note, tick) -> {
            if (note.key().minecraftPitch(note.octave()) > options.maxPitch()) {
                highPitchNotes.incrementAndGet();
                return;
            }
            noteFrames.get(tick / tickSpacing).tacticalNoteEvents().add(note);
        }));

        if (options.warnings()) {
            if (!unknownInstruments.isEmpty()) {
                Tactical.getInstance().getLogger().warning("Skipped unknown instruments:");
                unknownInstruments.forEach(i -> Tactical.getInstance().getLogger().warning(" - " + i));
            }
            if (highPitchNotes.get() > 0) {
                Tactical.getInstance().getLogger().warning("Skipped %s too high pitched notes".formatted(highPitchNotes));
            }
        }

        return new SimpleTacticalNoteSequence(this, noteFrames, tickSpacing);
    }
}
