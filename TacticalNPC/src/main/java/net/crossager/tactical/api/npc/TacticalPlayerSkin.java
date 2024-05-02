package net.crossager.tactical.api.npc;

import net.crossager.tactical.api.TacticalNPC;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Represents the skin of a player, including texture and signature.
 */
public interface TacticalPlayerSkin {

    /**
     * Returns the texture of the player's skin.
     *
     * @return the texture of the player's skin
     */
    @NotNull
    String texture();

    /**
     * Returns the signature of the player's skin.
     *
     * @return the signature of the player's skin
     */
    @NotNull
    String signature();

    /**
     * Represents a null skin, useful for cases where no skin is available.
     */
    TacticalPlayerSkin NO_SKIN = of("", "");

    /**
     * Creates a TacticalPlayerSkin instance with the given texture and signature.
     *
     * @param texture   the texture of the player's skin
     * @param signature the signature of the player's skin
     * @return a TacticalPlayerSkin instance
     */
    static TacticalPlayerSkin of(@NotNull String texture, @NotNull String signature) {
        return TacticalNPC.getInstance().createPlayerSkin(texture, signature);
    }

    /**
     * Fetches the skin of a player by their username and invokes the callback with the retrieved skin.
     *
     * @param username the username of the player whose skin to fetch
     * @param callback the callback to invoke with the retrieved skin
     */
    static void fetchByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback) {
        TacticalNPC.getInstance().fetchSkinByUsername(username, callback);
    }

    /**
     * Fetches the skin of a player by their UUID and invokes the callback with the retrieved skin.
     *
     * @param uuid     the UUID of the player whose skin to fetch
     * @param callback the callback to invoke with the retrieved skin
     */
    static void fetchByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback) {
        TacticalNPC.getInstance().fetchSkinByUUID(uuid, callback);
    }

    /**
     * Fetches the skin of a player by their username and invokes the callback with the retrieved skin.
     * If an error occurs during the retrieval process, the onError callback is invoked.
     *
     * @param username the username of the player whose skin to fetch
     * @param callback the callback to invoke with the retrieved skin
     * @param onError  the callback to invoke if an error occurs during retrieval
     */
    static void fetchByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        TacticalNPC.getInstance().fetchSkinByUsername(username, callback, onError);
    }

    /**
     * Fetches the skin of a player by their UUID and invokes the callback with the retrieved skin.
     * If an error occurs during the retrieval process, the onError callback is invoked.
     *
     * @param uuid     the UUID of the player whose skin to fetch
     * @param callback the callback to invoke with the retrieved skin
     * @param onError  the callback to invoke if an error
     */
    static void fetchByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        TacticalNPC.getInstance().fetchSkinByUUID(uuid, callback, onError);
    }
}
