package net.crossager.tactical.api.wrappers.nbt;

public class NBTParseException extends RuntimeException {

    public NBTParseException(String message) {
        super(message);
    }

    public NBTParseException(String message, TacticalNBTStringReader reader) {
        super(message + " '" + reader.previouslyReadString(35) + "' <--- HERE");
    }
}
