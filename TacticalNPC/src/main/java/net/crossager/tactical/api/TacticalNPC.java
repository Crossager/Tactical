package net.crossager.tactical.api;

import net.crossager.tactical.api.npc.TacticalNPCFactory;
import net.crossager.tactical.api.npc.TacticalPlayerSkin;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public interface TacticalNPC {
    @NotNull
    static TacticalNPC getInstance() {
        return NPCAPIHolder.getInstance();
    }

    @NotNull
    TacticalNPCFactory getNPCFactory();

    @NotNull
    TacticalPlayerSkin createPlayerSkin(@NotNull String texture, @NotNull String signature);

    void fetchSkinByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback);

    void fetchSkinByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback);

    void fetchSkinByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError);

    void fetchSkinByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError);

    @NotNull
    TacticalPlayerSkin extractPlayerSkin(@NotNull Player player);
    @NotNull
    TacticalPlayerSkin skinFromData(@NotNull UUID profileId, @NotNull String profileName, @Nullable String textureUrl, @NotNull PlayerTextures.SkinModel skinModel, @Nullable String capeUrl, @Nullable String signature);
}
