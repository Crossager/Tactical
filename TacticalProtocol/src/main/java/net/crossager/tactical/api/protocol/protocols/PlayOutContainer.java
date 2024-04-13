package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolContainer;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.util.AddedIn;
import net.crossager.tactical.api.util.RemovedIn;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the outgoing packet container for the play section of the tactical protocol.
 * This container holds methods to access various outgoing play-related packet types.
 */
public interface PlayOutContainer extends ProtocolContainer {
    @NotNull
    PacketType abilities();
    @NotNull
    PacketType advancements();
    @NotNull
    PacketType animation();
    @NotNull
    PacketType attachEntity();
    @NotNull
    PacketType autoRecipe();
    @NotNull
    PacketType blockAction();
    @NotNull
    PacketType blockBreakAnimation();
    @NotNull
    PacketType blockChange();
    @NotNull
    PacketType blockChangedAck();
    @NotNull
    PacketType boss();
    @NotNull
    PacketType camera();
    @NotNull
    PacketType clearTitles();
    @NotNull
    PacketType closeWindow();
    @NotNull
    PacketType collect();
    @NotNull
    PacketType commands();
    @NotNull
    PacketType customChatCompletions();
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType deleteChat();
    @NotNull
    PacketType disguisedChat();
    @NotNull
    PacketType entityDestroy();
    @NotNull
    PacketType entityEffect();
    @NotNull
    PacketType entityEquipment();
    @NotNull
    PacketType entityHeadRotation();
    @NotNull
    PacketType entityLook();
    @NotNull
    PacketType entityMetadata();
    @NotNull
    PacketType entitySound();
    @NotNull
    PacketType entityStatus();
    @NotNull
    PacketType entityTeleport();
    @NotNull
    PacketType entityVelocity();
    @NotNull
    PacketType experience();
    @NotNull
    PacketType explosion();
    @NotNull
    PacketType gameStateChange();
    @NotNull
    PacketType heldItemSlot();
    @NotNull
    PacketType initializeBorder();
    @NotNull
    PacketType keepAlive();
    @NotNull
    PacketType kickDisconnect();
    @NotNull
    PacketType levelChunkWithLight();
    @NotNull
    PacketType lightUpdate();
    @NotNull
    PacketType login();
    @NotNull
    PacketType lookAt();
    @NotNull
    PacketType map();
    @NotNull
    PacketType mount();
    @NotNull
    PacketType multiBlockChange();
    @NotNull
    PacketType namedSoundEffect();
    @NotNull
    PacketType nBTQuery();
    @NotNull
    PacketType openBook();
    @NotNull
    PacketType openSignEditor();
    @NotNull
    PacketType openWindow();
    @NotNull
    PacketType openWindowHorse();
    @NotNull
    PacketType openWindowMerchant();
    @NotNull
    PacketType ping();
    @NotNull
    PacketType playerChat();
    @NotNull
    PacketType playerCombatEnd();
    @NotNull
    PacketType playerCombatEnter();
    @NotNull
    PacketType playerCombatKill();
    @NotNull
    PacketType playerInfoRemove();
    @NotNull
    PacketType playerInfoUpdate();
    @NotNull
    PacketType playerListHeaderFooter();
    /** Synchronize player position */
    @NotNull
    PacketType position();
    @NotNull
    PacketType recipes();
    @NotNull
    PacketType recipeUpdate();
    @NotNull
    PacketType relEntityMove();
    @NotNull
    PacketType relEntityMoveLook();
    @NotNull
    PacketType removeEntityEffect();
    @NotNull
    PacketType resourcePackSend();
    @NotNull
    PacketType respawn();
    @NotNull
    PacketType scoreboardDisplayObjective();
    @NotNull
    PacketType scoreboardObjective();
    @NotNull
    PacketType scoreboardScore();
    @NotNull
    PacketType scoreboardTeam();
    @NotNull
    PacketType selectAdvancementTab();
    @NotNull
    PacketType serverData();
    @NotNull
    PacketType serverDifficulty();
    @NotNull
    PacketType setActionBarText();
    @NotNull
    PacketType setBorderCenter();
    @NotNull
    PacketType setBorderLerpSize();
    @NotNull
    PacketType setBorderSize();
    @NotNull
    PacketType setBorderWarningDelay();
    @NotNull
    PacketType setBorderWarningDistance();
    @NotNull
    PacketType setCooldown();
    @NotNull
    PacketType setSimulationDistance();
    @NotNull
    PacketType setSlot();
    @NotNull
    PacketType setSubtitleText();
    @NotNull
    PacketType setTitlesAnimation();
    @NotNull
    PacketType setTitleText();
    @NotNull
    PacketType spawnEntity();
    @NotNull
    PacketType spawnEntityExperienceOrb();
    @NotNull
    PacketType spawnPosition();
    @NotNull
    PacketType statistic();
    @NotNull
    PacketType stopSound();
    @NotNull
    PacketType systemChat();
    @NotNull
    PacketType tabComplete();
    @NotNull
    PacketType tags();
    @NotNull
    PacketType tileEntityData();
    @NotNull
    PacketType unloadChunk();
    @NotNull
    PacketType updateAttributes();
    @NotNull
    PacketType updateHealth();
    @NotNull
    PacketType updateTime();
    @NotNull
    PacketType vehicleMove();
    @NotNull
    PacketType viewCentre();
    @NotNull
    PacketType viewDistance();
    @NotNull
    PacketType windowData();
    @NotNull
    PacketType windowItems();
    @NotNull
    PacketType worldEvent();
    @NotNull
    PacketType worldParticles();

    @AddedIn("1.19.4")
    @NotNull
    PacketType bundleDelimiter();
    @AddedIn("1.19.4")
    @NotNull
    PacketType chunksBiomes();
    @AddedIn("1.19.4")
    @NotNull
    PacketType damageEvent();
    @AddedIn("1.19.4")
    @NotNull
    PacketType hurtAnimation();
    @AddedIn("1.20.2")
    @NotNull
    PacketType chunkBatchFinished();
    @AddedIn("1.20.2")
    @NotNull
    PacketType chunkBatchStart();
    @AddedIn("1.20.2")
    @NotNull
    PacketType pong();
    @AddedIn("1.20.2")
    @NotNull
    PacketType startConfiguration();

    @RemovedIn("1.20.2")
    @NotNull
    PacketType namedEntitySpawn();
    @RemovedIn("1.20.2")
    @NotNull
    PacketType updateEnabledFeatures();
}
