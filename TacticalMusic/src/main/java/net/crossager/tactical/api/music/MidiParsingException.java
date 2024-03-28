package net.crossager.tactical.api.music;

public class MidiParsingException extends RuntimeException {
    public MidiParsingException(String msg, Exception e) {
        super(msg, e);
    }
}
