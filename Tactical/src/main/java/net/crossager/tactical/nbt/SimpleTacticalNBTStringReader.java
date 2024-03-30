package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.NBTParseException;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTStringReader;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalNBTStringReader implements TacticalNBTStringReader {
    private final String string;
    private int readerIndex = 0;

    public SimpleTacticalNBTStringReader(String string) {
        this.string = string;
    }

    @Override
    public @NotNull String getString() {
        return string;
    }

    @Override
    public int remainingChars() {
        return string.length() - readerIndex;
    }

    @Override
    public int readerIndex() {
        return readerIndex;
    }

    @Override
    public void readerIndex(int readerIndex) {
        this.readerIndex = readerIndex;
    }

    @Override
    public boolean hasNext() {
        return readerIndex < string.length();
    }

    @Override
    public void skipWhitespaces() {
        while (hasNext() && Character.isWhitespace(currentChar())) {
            skip();
        }
    }

    @Override
    public char peek() {
        return string.charAt(readerIndex + 1);
    }

    @Override
    public char peek(int depth) {
        return string.charAt(readerIndex + 1);
    }

    @Override
    public boolean isCurrently(char c) {
        if (!hasNext()) return false;
        return currentChar() == c;
    }

    @Override
    public char next() {
        return string.charAt(readerIndex++);
    }

    @Override
    public char currentChar() {
        return string.charAt(readerIndex);
    }

    @Override
    public boolean isStartingQuotation() {
        return isStartingQuotation(currentChar());
    }

    @Override
    public boolean isStartingQuotation(char c) {
        return c == '"' || c == '\'';
    }

    @Override
    public @NotNull String readAnyString() {
        if (isStartingQuotation())
            return readQuotedString();
        return readStringValue();
    }

    @Override
    public @NotNull String readStringValue() {
        skipWhitespaces();
        int startIndex = readerIndex;
        while (hasNext() && isLegalUnquotedStringChar(currentChar())) {
            skip();
        }
        return string.substring(startIndex, readerIndex);
    }

    @Override
    public @NotNull String readQuotedString() {
        skipWhitespaces();
        if (!isStartingQuotation()) throw new NBTParseException("Expected a quotation begin, got '" + currentChar() + "'");
        char quote = next();
        int startIndex = readerIndex;
        StringBuilder stringBuilder = null;
        boolean escapeNextCharacter = false;
        while (hasNext()) {
            char c = next();
            if (escapeNextCharacter) {
                if (!TacticalNBTStringReader.isLegalEscapeCharacter(c))
                    throw new NBTParseException("Illegal escape character '" + c + "'");
                escapeNextCharacter = false;
            } else {
                if (c == '\\') {
                    escapeNextCharacter = true;
                    if (stringBuilder != null) continue;
                    stringBuilder = new StringBuilder(string.substring(startIndex, readerIndex - 1));
                    continue;
                }
                if (c == quote)
                    return stringBuilder == null ? string.substring(startIndex, readerIndex - 1) : stringBuilder.toString();
            }
            if (stringBuilder != null) stringBuilder.append(c);
        }
        throw new NBTParseException("Missing end quote");
    }

    @Override
    public boolean beginNextElement() {
        skipWhitespaces();
        if (isCurrently(',')) {
            skip();
            return true;
        }
        return false;
    }

    @Override
    public void expect(char c) {
        if (!hasNext()) throw new NBTParseException("Expected char '" + c + "' at index " + readerIndex + ", found none");
        char peek = currentChar();
        if (peek != c) throw new NBTParseException("Expected char '" + c + "' at index " + readerIndex + ", found '" + peek + "'");
    }

    @Override
    public void expectAndSkip(char c) {
        expect(c);
        skip();
    }

    @Override
    public void skip() {
        readerIndex++;
    }

    @Override
    public void skip(int length) {
        readerIndex += length;
    }

    private boolean isLegalUnquotedStringChar(char c) {
        return c >= 'a' && c <= 'z'
                || c >= 'A' && c <= 'Z'
                || c >= '0' && c <= '9'
                || c == '-'
                || c == '+'
                || c == '.'
                || c == '_';
    }

    @Override
    public @NotNull String previouslyReadString() {
        return string.substring(0, readerIndex);
    }

    @Override
    public @NotNull String previouslyReadString(int length) {
        if (readerIndex - length < 0) length = 0;
        return string.substring(readerIndex - length, readerIndex);
    }
}
