package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.PlayOutContainer;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

public class ProtocolPlayOutContainer extends ProtocolContainerBase implements PlayOutContainer {
    public ProtocolPlayOutContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType abilities = get("Abilities", "SPacketPlayerAbilities");
    private final PacketType advancements = get("Advancements", "SPacketAdvancementInfo");
    private final PacketType animation = get("Animation", "SPacketAnimation");
    private final PacketType attachEntity = get("AttachEntity", "SPacketEntityAttach");
    private final PacketType autoRecipe = get("AutoRecipe", "SPacketPlaceGhostRecipe");
    private final PacketType blockAction = get("BlockAction", "SPacketBlockAction");
    private final PacketType blockBreakAnimation = get("BlockBreakAnimation", "SPacketBlockBreakAnim");
    private final PacketType blockChange = get("BlockChange", "SPacketBlockChange");
    private final PacketType blockChangedAck = get("BlockChangedAck");
    private final PacketType boss = get("Boss", "SPacketUpdateBossInfo");
    private final PacketType camera = get("Camera", "SPacketCamera");
    private final PacketType clearTitles = get("ClearTitles");
    private final PacketType closeWindow = get("CloseWindow", "SPacketCloseWindow");
    private final PacketType collect = get("Collect", "SPacketCollectItem");
    private final PacketType commands = get("Commands");
    private final PacketType customChatCompletions = get("CustomChatCompletions");
    private final PacketType customPayload = get("CustomPayload", "SPacketCustomPayload");
    private final PacketType deleteChat = get("DeleteChat");
    private final PacketType disguisedChat = get("DisguisedChat");
    private final PacketType entityDestroy = get("EntityDestroy", "SPacketDestroyEntities");
    private final PacketType entityEffect = get("EntityEffect", "SPacketEntityEffect");
    private final PacketType entityEquipment = get("EntityEquipment", "SPacketEntityEquipment");
    private final PacketType entityHeadRotation = get("EntityHeadRotation", "SPacketEntityHeadLook");
    private final PacketType entityLook = get("Entity$EntityLook", "Entity$PacketPlayOutEntityLook");
    private final PacketType entityMetadata = get("EntityMetadata", "SPacketEntityMetadata");
    private final PacketType entitySound = get("EntitySound", "SPacketSoundEffect");
    private final PacketType entityStatus = get("EntityStatus", "SPacketEntityStatus");
    private final PacketType entityTeleport = get("EntityTeleport", "SPacketEntityTeleport");
    private final PacketType entityVelocity = get("EntityVelocity", "SPacketEntityVelocity");
    private final PacketType experience = get("Experience", "SPacketSetExperience");
    private final PacketType explosion = get("Explosion", "SPacketExplosion");
    private final PacketType gameStateChange = get("GameStateChange", "SPacketChangeGameState");
    private final PacketType heldItemSlot = get("HeldItemSlot", "SPacketHeldItemChange");
    private final PacketType initializeBorder = get("InitializeBorder");
    private final PacketType keepAlive = get("KeepAlive", "SPacketKeepAlive");
    private final PacketType kickDisconnect = get("KickDisconnect", "SPacketDisconnect");
    private final PacketType levelChunkWithLight = get("LevelChunkWithLight", "MapChunk", "SPacketChunkData");
    private final PacketType lightUpdate = get("LightUpdate");
    private final PacketType login = get("Login", "SPacketJoinGame");
    private final PacketType lookAt = get("LookAt", "SPacketPlayerPosLook");
    private final PacketType map = get("Map", "SPacketMaps");
    private final PacketType mount = get("Mount", "SPacketSetPassengers");
    private final PacketType multiBlockChange = get("MultiBlockChange", "SPacketMultiBlockChange");
    private final PacketType namedEntitySpawn = get("NamedEntitySpawn", "SPacketSpawnPlayer");
    private final PacketType namedSoundEffect = get("NamedSoundEffect");
    private final PacketType nBTQuery = get("NBTQuery");
    private final PacketType openBook = get("OpenBook");
    private final PacketType openSignEditor = get("OpenSignEditor", "SPacketSignEditorOpen");
    private final PacketType openWindow = get("OpenWindow", "SPacketOpenWindow");
    private final PacketType openWindowHorse = get("OpenWindowHorse");
    private final PacketType openWindowMerchant = get("OpenWindowMerchant");
    private final PacketType ping = get("Ping");
    private final PacketType playerChat = get("PlayerChat", "Chat", "SPacketChat");
    private final PacketType playerCombatEnd = get("PlayerCombatEnd");
    private final PacketType playerCombatEnter = get("PlayerCombatEnter");
    private final PacketType playerCombatKill = get("PlayerCombatKill");
    private final PacketType playerInfoRemove = get("PlayerInfoRemove");
    private final PacketType playerInfoUpdate = get("PlayerInfoUpdate", "PlayerInfo");
    private final PacketType playerListHeaderFooter = get("PlayerListHeaderFooter", "SPacketPlayerListHeaderFooter");
    private final PacketType position = get("Position");
    private final PacketType recipes = get("Recipes", "SPacketRecipeBook");
    private final PacketType recipeUpdate = get("RecipeUpdate");
    private final PacketType relEntityMove = get("Entity$RelEntityMove", "Entity$PacketPlayOutRelEntityMove");
    private final PacketType relEntityMoveLook = get("Entity$RelEntityMoveLook", "Entity$PacketPlayOutRelEntityMoveLook");
    private final PacketType removeEntityEffect = get("RemoveEntityEffect", "SPacketRemoveEntityEffect");
    private final PacketType resourcePackSend = get("ResourcePackSend", "SPacketResourcePackSend");
    private final PacketType respawn = get("Respawn", "SPacketRespawn");
    private final PacketType scoreboardDisplayObjective = get("ScoreboardDisplayObjective", "SPacketDisplayObjective");
    private final PacketType scoreboardObjective = get("ScoreboardObjective", "SPacketScoreboardObjective");
    private final PacketType scoreboardScore = get("ScoreboardScore", "SPacketUpdateScore");
    private final PacketType scoreboardTeam = get("ScoreboardTeam", "SPacketTeams");
    private final PacketType selectAdvancementTab = get("SelectAdvancementTab", "SPacketSelectAdvancementsTab");
    private final PacketType serverData = get("ServerData");
    private final PacketType serverDifficulty = get("ServerDifficulty", "SPacketServerDifficulty");
    private final PacketType setActionBarText = get("SetActionBarText");
    private final PacketType setBorderCenter = get("SetBorderCenter");
    private final PacketType setBorderLerpSize = get("SetBorderLerpSize");
    private final PacketType setBorderSize = get("SetBorderSize");
    private final PacketType setBorderWarningDelay = get("SetBorderWarningDelay");
    private final PacketType setBorderWarningDistance = get("SetBorderWarningDistance");
    private final PacketType setCooldown = get("SetCooldown", "SPacketCooldown");
    private final PacketType setSimulationDistance = get("SetSimulationDistance");
    private final PacketType setSlot = get("SetSlot", "SPacketSetSlot");
    private final PacketType setSubtitleText = get("SetSubtitleText");
    private final PacketType setTitlesAnimation = get("SetTitlesAnimation");
    private final PacketType setTitleText = get("SetTitleText");
    private final PacketType spawnEntity = get("SpawnEntity", "SPacketSpawnObject");
    private final PacketType spawnEntityExperienceOrb = get("SpawnEntityExperienceOrb", "SPacketSpawnExperienceOrb");
    private final PacketType spawnPosition = get("SpawnPosition", "SPacketSpawnPosition");
    private final PacketType statistic = get("Statistic", "SPacketStatistics");
    private final PacketType stopSound = get("StopSound");
    private final PacketType systemChat = get("SystemChat");
    private final PacketType tabComplete = get("TabComplete", "SPacketTabComplete");
    private final PacketType tags = get("Tags");
    private final PacketType tileEntityData = get("TileEntityData", "SPacketUpdateTileEntity");
    private final PacketType unloadChunk = get("UnloadChunk", "SPacketUnloadChunk");
    private final PacketType updateAttributes = get("UpdateAttributes", "SPacketEntityProperties");
    private final PacketType updateEnabledFeatures = get("UpdateEnabledFeatures");
    private final PacketType updateHealth = get("UpdateHealth", "SPacketUpdateHealth");
    private final PacketType updateTime = get("UpdateTime", "SPacketTimeUpdate");
    private final PacketType vehicleMove = get("VehicleMove", "SPacketMoveVehicle");
    private final PacketType viewCentre = get("ViewCentre");
    private final PacketType viewDistance = get("ViewDistance");
    private final PacketType windowData = get("WindowData", "SPacketWindowProperty");
    private final PacketType windowItems = get("WindowItems", "SPacketWindowItems");
    private final PacketType worldEvent = get("WorldEvent", "SPacketEffect");
    private final PacketType worldParticles = get("WorldParticles", "SPacketParticles");
    private final PacketType bundleDelimiter = get(true, "Delimiter", "BundleDelimiterPacket");
    private final PacketType chunksBiomes = get(true, "ChunksBiomes", "ClientboundChunksBiomesPacket");
    private final PacketType damageEvent = get(true, "DamageEvent", "ClientboundDamageEventPacket");
    private final PacketType hurtAnimation = get(true, "HurtAnimation", "ClientboundHurtAnimationPacket");

    @NotNull
    @Override
    public PacketType abilities() {
        return abilities;
    }

    @NotNull
    @Override
    public PacketType advancements() {
        return advancements;
    }

    @NotNull
    @Override
    public PacketType animation() {
        return animation;
    }

    @NotNull
    @Override
    public PacketType attachEntity() {
        return attachEntity;
    }

    @NotNull
    @Override
    public PacketType autoRecipe() {
        return autoRecipe;
    }

    @NotNull
    @Override
    public PacketType blockAction() {
        return blockAction;
    }

    @NotNull
    @Override
    public PacketType blockBreakAnimation() {
        return blockBreakAnimation;
    }

    @NotNull
    @Override
    public PacketType blockChange() {
        return blockChange;
    }

    @NotNull
    @Override
    public PacketType blockChangedAck() {
        return blockChangedAck;
    }

    @NotNull
    @Override
    public PacketType boss() {
        return boss;
    }

    @NotNull
    @Override
    public PacketType camera() {
        return camera;
    }

    @NotNull
    @Override
    public PacketType clearTitles() {
        return clearTitles;
    }

    @NotNull
    @Override
    public PacketType closeWindow() {
        return closeWindow;
    }

    @NotNull
    @Override
    public PacketType collect() {
        return collect;
    }

    @NotNull
    @Override
    public PacketType commands() {
        return commands;
    }

    @NotNull
    @Override
    public PacketType customChatCompletions() {
        return customChatCompletions;
    }

    @NotNull
    @Override
    public PacketType customPayload() {
        return customPayload;
    }

    @NotNull
    @Override
    public PacketType deleteChat() {
        return deleteChat;
    }

    @NotNull
    @Override
    public PacketType disguisedChat() {
        return disguisedChat;
    }

    @NotNull
    @Override
    public PacketType entityDestroy() {
        return entityDestroy;
    }

    @NotNull
    @Override
    public PacketType entityEffect() {
        return entityEffect;
    }

    @NotNull
    @Override
    public PacketType entityEquipment() {
        return entityEquipment;
    }

    @NotNull
    @Override
    public PacketType entityHeadRotation() {
        return entityHeadRotation;
    }

    @NotNull
    @Override
    public PacketType entityLook() {
        return entityLook;
    }

    @NotNull
    @Override
    public PacketType entityMetadata() {
        return entityMetadata;
    }

    @NotNull
    @Override
    public PacketType entitySound() {
        return entitySound;
    }

    @NotNull
    @Override
    public PacketType entityStatus() {
        return entityStatus;
    }

    @NotNull
    @Override
    public PacketType entityTeleport() {
        return entityTeleport;
    }

    @NotNull
    @Override
    public PacketType entityVelocity() {
        return entityVelocity;
    }

    @NotNull
    @Override
    public PacketType experience() {
        return experience;
    }

    @NotNull
    @Override
    public PacketType explosion() {
        return explosion;
    }

    @NotNull
    @Override
    public PacketType gameStateChange() {
        return gameStateChange;
    }

    @NotNull
    @Override
    public PacketType heldItemSlot() {
        return heldItemSlot;
    }

    @NotNull
    @Override
    public PacketType initializeBorder() {
        return initializeBorder;
    }

    @NotNull
    @Override
    public PacketType keepAlive() {
        return keepAlive;
    }

    @NotNull
    @Override
    public PacketType kickDisconnect() {
        return kickDisconnect;
    }

    @NotNull
    @Override
    public PacketType levelChunkWithLight() {
        return levelChunkWithLight;
    }

    @NotNull
    @Override
    public PacketType lightUpdate() {
        return lightUpdate;
    }

    @NotNull
    @Override
    public PacketType login() {
        return login;
    }

    @NotNull
    @Override
    public PacketType lookAt() {
        return lookAt;
    }

    @NotNull
    @Override
    public PacketType map() {
        return map;
    }

    @NotNull
    @Override
    public PacketType mount() {
        return mount;
    }

    @NotNull
    @Override
    public PacketType multiBlockChange() {
        return multiBlockChange;
    }

    @NotNull
    @Override
    public PacketType namedEntitySpawn() {
        return namedEntitySpawn;
    }

    @NotNull
    @Override
    public PacketType namedSoundEffect() {
        return namedSoundEffect;
    }

    @NotNull
    @Override
    public PacketType nBTQuery() {
        return nBTQuery;
    }

    @NotNull
    @Override
    public PacketType openBook() {
        return openBook;
    }

    @NotNull
    @Override
    public PacketType openSignEditor() {
        return openSignEditor;
    }

    @NotNull
    @Override
    public PacketType openWindow() {
        return openWindow;
    }

    @NotNull
    @Override
    public PacketType openWindowHorse() {
        return openWindowHorse;
    }

    @NotNull
    @Override
    public PacketType openWindowMerchant() {
        return openWindowMerchant;
    }

    @NotNull
    @Override
    public PacketType ping() {
        return ping;
    }

    @NotNull
    @Override
    public PacketType playerChat() {
        return playerChat;
    }

    @NotNull
    @Override
    public PacketType playerCombatEnd() {
        return playerCombatEnd;
    }

    @NotNull
    @Override
    public PacketType playerCombatEnter() {
        return playerCombatEnter;
    }

    @NotNull
    @Override
    public PacketType playerCombatKill() {
        return playerCombatKill;
    }

    @NotNull
    @Override
    public PacketType playerInfoRemove() {
        return playerInfoRemove;
    }

    @NotNull
    @Override
    public PacketType playerInfoUpdate() {
        return playerInfoUpdate;
    }

    @NotNull
    @Override
    public PacketType playerListHeaderFooter() {
        return playerListHeaderFooter;
    }

    @NotNull
    @Override
    public PacketType position() {
        return position;
    }

    @NotNull
    @Override
    public PacketType recipes() {
        return recipes;
    }

    @NotNull
    @Override
    public PacketType recipeUpdate() {
        return recipeUpdate;
    }

    @NotNull
    @Override
    public PacketType relEntityMove() {
        return relEntityMove;
    }

    @NotNull
    @Override
    public PacketType relEntityMoveLook() {
        return relEntityMoveLook;
    }

    @NotNull
    @Override
    public PacketType removeEntityEffect() {
        return removeEntityEffect;
    }

    @NotNull
    @Override
    public PacketType resourcePackSend() {
        return resourcePackSend;
    }

    @NotNull
    @Override
    public PacketType respawn() {
        return respawn;
    }

    @NotNull
    @Override
    public PacketType scoreboardDisplayObjective() {
        return scoreboardDisplayObjective;
    }

    @NotNull
    @Override
    public PacketType scoreboardObjective() {
        return scoreboardObjective;
    }

    @NotNull
    @Override
    public PacketType scoreboardScore() {
        return scoreboardScore;
    }

    @NotNull
    @Override
    public PacketType scoreboardTeam() {
        return scoreboardTeam;
    }

    @NotNull
    @Override
    public PacketType selectAdvancementTab() {
        return selectAdvancementTab;
    }

    @NotNull
    @Override
    public PacketType serverData() {
        return serverData;
    }

    @NotNull
    @Override
    public PacketType serverDifficulty() {
        return serverDifficulty;
    }

    @NotNull
    @Override
    public PacketType setActionBarText() {
        return setActionBarText;
    }

    @NotNull
    @Override
    public PacketType setBorderCenter() {
        return setBorderCenter;
    }

    @NotNull
    @Override
    public PacketType setBorderLerpSize() {
        return setBorderLerpSize;
    }

    @NotNull
    @Override
    public PacketType setBorderSize() {
        return setBorderSize;
    }

    @NotNull
    @Override
    public PacketType setBorderWarningDelay() {
        return setBorderWarningDelay;
    }

    @NotNull
    @Override
    public PacketType setBorderWarningDistance() {
        return setBorderWarningDistance;
    }

    @NotNull
    @Override
    public PacketType setCooldown() {
        return setCooldown;
    }

    @NotNull
    @Override
    public PacketType setSimulationDistance() {
        return setSimulationDistance;
    }

    @NotNull
    @Override
    public PacketType setSlot() {
        return setSlot;
    }

    @NotNull
    @Override
    public PacketType setSubtitleText() {
        return setSubtitleText;
    }

    @NotNull
    @Override
    public PacketType setTitlesAnimation() {
        return setTitlesAnimation;
    }

    @NotNull
    @Override
    public PacketType setTitleText() {
        return setTitleText;
    }

    @NotNull
    @Override
    public PacketType spawnEntity() {
        return spawnEntity;
    }

    @NotNull
    @Override
    public PacketType spawnEntityExperienceOrb() {
        return spawnEntityExperienceOrb;
    }

    @NotNull
    @Override
    public PacketType spawnPosition() {
        return spawnPosition;
    }

    @NotNull
    @Override
    public PacketType statistic() {
        return statistic;
    }

    @NotNull
    @Override
    public PacketType stopSound() {
        return stopSound;
    }

    @NotNull
    @Override
    public PacketType systemChat() {
        return systemChat;
    }

    @NotNull
    @Override
    public PacketType tabComplete() {
        return tabComplete;
    }

    @NotNull
    @Override
    public PacketType tags() {
        return tags;
    }

    @NotNull
    @Override
    public PacketType tileEntityData() {
        return tileEntityData;
    }

    @NotNull
    @Override
    public PacketType unloadChunk() {
        return unloadChunk;
    }

    @NotNull
    @Override
    public PacketType updateAttributes() {
        return updateAttributes;
    }

    @NotNull
    @Override
    public PacketType updateEnabledFeatures() {
        return updateEnabledFeatures;
    }

    @NotNull
    @Override
    public PacketType updateHealth() {
        return updateHealth;
    }

    @NotNull
    @Override
    public PacketType updateTime() {
        return updateTime;
    }

    @NotNull
    @Override
    public PacketType vehicleMove() {
        return vehicleMove;
    }

    @NotNull
    @Override
    public PacketType viewCentre() {
        return viewCentre;
    }

    @NotNull
    @Override
    public PacketType viewDistance() {
        return viewDistance;
    }

    @NotNull
    @Override
    public PacketType windowData() {
        return windowData;
    }

    @NotNull
    @Override
    public PacketType windowItems() {
        return windowItems;
    }

    @NotNull
    @Override
    public PacketType worldEvent() {
        return worldEvent;
    }

    @NotNull
    @Override
    public PacketType worldParticles() {
        return worldParticles;
    }

    @Override
    public @NotNull PacketType bundleDelimiter() {
        MinecraftVersion.ensureHasVersion(MinecraftVersion.v1_19_4);
        return bundleDelimiter;
    }

    @Override
    public @NotNull PacketType chunksBiomes() {
        MinecraftVersion.ensureHasVersion(MinecraftVersion.v1_19_4);
        return chunksBiomes;
    }

    @Override
    public @NotNull PacketType damageEvent() {
        MinecraftVersion.ensureHasVersion(MinecraftVersion.v1_19_4);
        return damageEvent;
    }

    @Override
    public @NotNull PacketType hurtAnimation() {
        MinecraftVersion.ensureHasVersion(MinecraftVersion.v1_19_4);
        return hurtAnimation;
    }
}
