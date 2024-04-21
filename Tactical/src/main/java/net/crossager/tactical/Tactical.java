package net.crossager.tactical;

import net.crossager.tactical.api.TacticalAPI;
import net.crossager.tactical.api.TacticalCommands;
import net.crossager.tactical.api.TacticalRegistrar;
import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandFactory;
import net.crossager.tactical.api.config.TacticalConfigFactory;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.music.MidiParsingException;
import net.crossager.tactical.api.music.TacticalMidiDrumKit;
import net.crossager.tactical.api.music.TacticalMidiParsingOptions;
import net.crossager.tactical.api.music.TacticalNoteSequence;
import net.crossager.tactical.api.npc.TacticalPlayerSkin;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.ProtocolSection;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.UnknownPacketListener;
import net.crossager.tactical.api.reflect.TacticalReflectionFactory;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.commands.SimpleTacticalCommandFactory;
import net.crossager.tactical.commands.TacticalCommandHandler;
import net.crossager.tactical.commons.SimpleTacticalReflectionFactory;
import net.crossager.tactical.config.SimpleTacticalConfigFactory;
import net.crossager.tactical.config.serializers.JsonSerializer;
import net.crossager.tactical.config.serializers.XmlSerializer;
import net.crossager.tactical.config.serializers.YamlSerializer;
import net.crossager.tactical.gui.TacticalGUIManager;
import net.crossager.tactical.music.SimpleTacticalMidiDrumKit;
import net.crossager.tactical.music.SimpleTacticalMidiParsingOptions;
import net.crossager.tactical.music.TacticalMusicManager;
import net.crossager.tactical.nbt.SimpleTacticalNBTManager;
import net.crossager.tactical.npc.SimpleTacticalPlayerSkin;
import net.crossager.tactical.protocol.ProtocolUtils;
import net.crossager.tactical.protocol.TacticalProtocolManager;
import net.crossager.tactical.util.PlayerMapManager;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.MidiSystem;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tactical implements TacticalAPI {
    private static Tactical INSTANCE;
    private static final Pattern VERSION_MATCHER = Pattern.compile(".*\\(.*MC.\\s*([a-zA-z0-9\\-.]+).*");

    private final SimpleTacticalConfigFactory configFactory = new SimpleTacticalConfigFactory();
    private final SimpleTacticalCommandFactory commandFactory = new SimpleTacticalCommandFactory();
    private final SimpleTacticalReflectionFactory reflectionFactory = new SimpleTacticalReflectionFactory();
    private final List<TacticalConfigSerializer> configSerializers = List.of(new JsonSerializer(), new XmlSerializer(), new YamlSerializer());
    private final TacticalProtocolManager protocolManager;
    private final SimpleTacticalNBTManager nbtManager = new SimpleTacticalNBTManager();
    private final TacticalGUIManager guiManager;
    private final Logger logger;
    private final PlayerMapManager playerMapManager;
    private final TacticalMusicManager musicManager;
    private final JavaPlugin plugin;

    public Tactical(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.protocolManager = new TacticalProtocolManager();
        this.guiManager = new TacticalGUIManager(plugin);
        this.playerMapManager = new PlayerMapManager(plugin);
        this.musicManager = new TacticalMusicManager(plugin);
    }

    public Tactical(Logger logger) {
        this.plugin = null;
        this.logger = logger;
        this.protocolManager = null;
        this.guiManager = null;
        this.playerMapManager = null;
        this.musicManager = null;
    }

    public Tactical() {
        this(Logger.getLogger("Tactical"));
    }

    public void init() {
        TacticalRegistrar.setInstance(this);
        INSTANCE = this;
    }

    public void disable() {
        if (protocolManager != null) {
            protocolManager.disable();
        }
        TacticalRegistrar.removeInstance();
        INSTANCE = null;
    }

    @Override
    public @NotNull TacticalConfigFactory getConfigFactory() {
        return configFactory;
    }

    @Override
    public @NotNull List<TacticalConfigSerializer> getConfigSerializers() {
        return configSerializers;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }

    @Override
    public @NotNull TacticalCommands registerCommand(@NotNull TacticalCommand command) {
        TacticalCommandHandler commandHandler = new TacticalCommandHandler(command);
        if (command.options().hasPluginCommand()) {
            command.options().pluginCommand().setExecutor(commandHandler);
            command.options().pluginCommand().setTabCompleter(commandHandler);
        } else {
            CraftBukkitReflection.GET_COMMAND_MAP.invoke(Bukkit.getServer()).register(command.options().prefix(), commandHandler);
        }
        return this;
    }

    @Override
    public @NotNull TacticalCommandFactory getCommandFactory() {
        return commandFactory;
    }

    @Override
    public @NotNull TacticalReflectionFactory getReflectionFactory() {
        return reflectionFactory;
    }

    @Override
    public @NotNull TacticalNBTManager getNBTManager() {
        return nbtManager;
    }

    @Override
    public @NotNull String getServerVersion() {
        String version = Bukkit.getVersion();
        Matcher matcher = VERSION_MATCHER.matcher(version);
        if (!matcher.matches()) throw new IllegalStateException("Invalid server version '" + version + '\'');
        return matcher.group(1);
    }

    private void checkProtocolManager() {
        if (protocolManager == null) throw new IllegalStateException("TacticalProtocol is not available without a plugin");
    }

    @Override
    @NotNull
    public ProtocolSection<?, ?> getProtocolSection(Protocol protocol) {
        checkProtocolManager();
        return protocolManager.getProtocolSection(protocol);
    }

    @Override
    public @NotNull ProtocolManager getProtocolManager(@NotNull Protocol protocol, @NotNull Sender sender) {
        checkProtocolManager();
        return protocolManager.getProtocolManager(protocol, sender);
    }

    @Override
    public @NotNull PacketType getPacketType(@NotNull Class<?> packetClass) {
        checkProtocolManager();
        return protocolManager.getPacketType(packetClass);
    }

    @Override
    public void addUnknownPacketListener(@NotNull UnknownPacketListener listener) {
        checkProtocolManager();
        protocolManager.registerUnknownPacketListener(listener);
    }

    @Override
    public void removeUnknownPacketListener(@NotNull UnknownPacketListener listener) {
        checkProtocolManager();
        protocolManager.unregisterUnknownPacketListener(listener);
    }

    @Override
    public void addPacketListener(@NotNull PacketListener listener) {
        checkProtocolManager();
        protocolManager.registerPacketListener(listener);
    }

    @Override
    public void removePacketListener(@NotNull PacketListener listener) {
        checkProtocolManager();
        protocolManager.unregisterPacketListener(listener);
    }

    @Override
    public void sendPacket(@NotNull Player player, @NotNull Object packet) {
        ProtocolUtils.sendPacket(player, packet);
    }

    @Override
    public @NotNull TacticalGUIManager getGUIFactory() {
        if (guiManager == null) throw new IllegalStateException("TacticalGUI is not available without a registered plugin");
        return guiManager;
    }

    public PlayerMapManager getPlayerMapManager() {
        if (playerMapManager == null) throw new IllegalStateException("Player maps are not available without a registered plugin");
        return playerMapManager;
    }

    @Override
    public @NotNull TacticalNoteSequence loadFromJsonFile(@NotNull Path path) throws IOException {
        return loadFromJson(Files.readString(path));
    }

    @Override
    public @NotNull TacticalNoteSequence loadFromJson(@NotNull String json) {
        return musicManager.loadFromJson(json);
    }

    @Override
    public @NotNull String saveToJsonFile(@NotNull TacticalNoteSequence sequence, @NotNull Path path) throws IOException {
        String json = saveToJson(sequence);
        Files.writeString(path, json);
        return json;
    }

    @Override
    public @NotNull String saveToJson(@NotNull TacticalNoteSequence sequence) {
        return musicManager.saveToJson(sequence);
    }

    @Override
    public @NotNull TacticalNoteSequence loadFromMidiFile(@NotNull Path path, @NotNull TacticalMidiParsingOptions tacticalMidiParsingOptions) throws MidiParsingException {
        try {
            return musicManager.loadFromMidi(MidiSystem.getSequence(path.toFile()), tacticalMidiParsingOptions);
        } catch (Exception e) {
            throw new MidiParsingException("Error parsing " + path, e);
        }
    }

    @Override
    public @NotNull TacticalNoteSequence loadFromMidiFile(@NotNull URL url, @NotNull TacticalMidiParsingOptions tacticalMidiParsingOptions) throws MidiParsingException {
        try {
            return musicManager.loadFromMidi(MidiSystem.getSequence(url), tacticalMidiParsingOptions);
        } catch (Exception e) {
            throw new MidiParsingException("Error parsing " + url, e);
        }
    }

    @Override
    public @NotNull TacticalMidiParsingOptions createDefaultMidiParsingOptions() {
        return SimpleTacticalMidiParsingOptions.createDefault();
    }

    @Override
    public @NotNull TacticalMidiParsingOptions createMidiParsingOptions() {
        return new SimpleTacticalMidiParsingOptions();
    }

    @Override
    public @NotNull TacticalMidiDrumKit createDrumKit() {
        return new SimpleTacticalMidiDrumKit();
    }

    @Override
    public @NotNull TacticalMidiDrumKit createDefaultDrumKit() {
        return SimpleTacticalMidiDrumKit.createDefault();
    }

    @Override
    public @NotNull TacticalPlayerSkin createPlayerSkin(@NotNull String texture, @NotNull String signature) {
        return new SimpleTacticalPlayerSkin(texture, signature);
    }

    @Override
    public void fetchSkinByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback) {
        fetchSkinByUsername(username, callback, t -> {
            Tactical.getInstance().getLogger().severe("Exception whilst trying to fetch UUID from username '%s'".formatted(username));
            t.printStackTrace();
        });

    }

    @Override
    public void fetchSkinByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback) {
        fetchSkinByUUID(uuid, callback, t -> {
            Tactical.getInstance().getLogger().severe("Exception whilst trying to fetch skin from uuid '%s'".formatted(uuid));
            t.printStackTrace();
        });
    }

    @Override
    public void fetchSkinByUsername(@NotNull String username, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        if (plugin == null) throw new IllegalStateException("TacticalNPC is not available without a plugin");
        SimpleTacticalPlayerSkin.fetchSkinByUsername(plugin, username, callback, onError);
    }

    @Override
    public void fetchSkinByUUID(@NotNull UUID uuid, @NotNull Consumer<TacticalPlayerSkin> callback, @NotNull Consumer<Throwable> onError) {
        if (plugin == null) throw new IllegalStateException("TacticalNPC is not available without a plugin");
        SimpleTacticalPlayerSkin.fetchSkinByUUID(plugin, uuid, callback, onError);
    }

    public static Tactical getInstance() {
        return INSTANCE;
    }
}
