package net.crossager.tactical.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.crossager.tactical.api.npc.TacticalPlayerSkin;
import net.crossager.tactical.api.util.TacticalUtils;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public record SimpleTacticalPlayerSkin(String texture, String signature) implements TacticalPlayerSkin {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final String ID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String SKIN_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    public static void fetchSkinByUUID(Plugin plugin, UUID uuid, Consumer<TacticalPlayerSkin> callback, Consumer<Throwable> onError) {
        httpClient.newCall(new Request.Builder().url(SKIN_URL.formatted(uuid.toString().replaceAll("-", ""))).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    response.close();
                    onError.accept(new IOException("Http error: " + response.code()));
                    return;
                }
                JsonObject responseJson = JsonParser.parseString(response.body().string()).getAsJsonObject();
                if (responseJson.has("errorMessage")) {
                    Bukkit.getScheduler().runTask(plugin, () -> onError.accept(new IllegalArgumentException(responseJson.get("errorMessage").getAsString())));
                    return;
                }
                responseJson.get("properties").getAsJsonArray().forEach(element -> {
                    JsonObject property = element.getAsJsonObject();
                    if (!property.get("name").getAsString().equals("textures")) return;
                    String texture = property.get("value").getAsString();
                    String signature = property.get("signature").getAsString();
                    Bukkit.getScheduler().runTask(plugin, () -> callback.accept(new SimpleTacticalPlayerSkin(texture, signature)));
                });
            }
        });
    }

    public static void fetchSkinByUsername(Plugin plugin, String username, Consumer<TacticalPlayerSkin> callback, Consumer<Throwable> onError) {
        fetchUUID(plugin, username, uuid -> {
            fetchSkinByUUID(plugin, uuid, callback, onError);
        }, onError);
    }

    public static void fetchUUID(Plugin plugin, String username, Consumer<UUID> callback, Consumer<Throwable> onError) {
        httpClient.newCall(new Request.Builder().url(ID_URL.formatted(username)).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    response.close();
                    onError.accept(new IOException("Http error: " + response.code()));
                    return;
                }
                JsonObject responseJson = JsonParser.parseString(response.body().string()).getAsJsonObject();
                if (responseJson.has("errorMessage")) {
                    Bukkit.getScheduler().runTask(plugin, () -> onError.accept(new IllegalArgumentException(responseJson.get("errorMessage").getAsString())));
                    return;
                }
                String id = responseJson.get("id").getAsString();
                Bukkit.getScheduler().runTask(plugin, () -> callback.accept(TacticalUtils.lenientUUIDFromString(id)));
            }
        });
    }
}
