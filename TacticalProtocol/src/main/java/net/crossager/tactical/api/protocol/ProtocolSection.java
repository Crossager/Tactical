package net.crossager.tactical.api.protocol;

import org.jetbrains.annotations.NotNull;

/**
 * Interface representing a section of a network protocol.
 * <p>
 * A protocol section contains input and output containers along with the associated protocol.
 *
 * @param <I> The type of the input container.
 * @param <O> The type of the output container.
 */
public interface ProtocolSection<I extends ProtocolContainer, O extends ProtocolContainer> {
    /**
     * Returns the protocol associated with this section.
     *
     * @return The protocol associated with this section.
     */
    @NotNull
    Protocol protocol();

    /**
     * Returns the input container of this section.
     *
     * @return The input container of this section.
     */
    @NotNull
    I in();

    /**
     * Returns the output container of this section.
     *
     * @return The output container of this section.
     */
    @NotNull
    O out();
}
