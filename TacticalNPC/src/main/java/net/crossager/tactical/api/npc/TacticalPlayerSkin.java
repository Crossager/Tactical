package net.crossager.tactical.api.npc;

import net.crossager.tactical.api.TacticalNPC;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public interface TacticalPlayerSkin {
    TacticalPlayerSkin NO_SKIN = of("", "");

    @NotNull
    String texture();
    @NotNull
    String signature();

    static TacticalPlayerSkin of(@NotNull String texture, @NotNull String signature) {
        return TacticalNPC.getInstance().createPlayerSkin(texture, signature);
    }

    static void fetchByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback) {
        TacticalNPC.getInstance().fetchSkinByUsername(username, callback);
    }

    static void fetchByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback) {
        TacticalNPC.getInstance().fetchSkinByUUID(uuid, callback);
    }

    static void fetchByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        TacticalNPC.getInstance().fetchSkinByUsername(username, callback, onError);
    }

    static void fetchByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        TacticalNPC.getInstance().fetchSkinByUUID(uuid, callback, onError);
    }
}
