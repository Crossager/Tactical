package net.crossager.tactical.api.wrappers.nbt;

import org.jetbrains.annotations.NotNull;

public interface TacticalNBTStringReader {
    @NotNull
    String getString();

    int remainingChars();

    int readerIndex();

    void readerIndex(int readerIndex);

    boolean hasNext();

    void skipWhitespaces();

    char peek();

    char peek(int depth);

    boolean isCurrently(char c);

    char next();

    char currentChar();

    boolean isStartingQuotation();

    boolean isStartingQuotation(char c);

    @NotNull
    String readAnyString();

    @NotNull
    String readStringValue();

    @NotNull
    String readQuotedString();

    boolean beginNextElement();

    void expect(char c);

    void expectAndSkip(char c);

    void skip();

    void skip(int length);

    @NotNull
    String previouslyReadString();

    @NotNull
    String previouslyReadString(int length);

    static boolean isLegalEscapeCharacter(char c) {
        return c == '"' || c == '\'' || c == '\\';
    }
}
