package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.ProtocolContainer;
import net.crossager.tactical.api.util.AddedIn;
import org.jetbrains.annotations.NotNull;
/**
 * Represents the incoming packet container for the play section of the tactical protocol.
 * This container holds methods to access various incoming play-related packet types.
 */
public interface PlayInContainer extends ProtocolContainer {
    @NotNull
    PacketType abilities();
    @NotNull
    PacketType advancements();
    @NotNull
    PacketType armAnimation();
    @NotNull
    PacketType autoRecipe();
    @NotNull
    PacketType beacon();
    @NotNull
    PacketType bookEdit();
    @NotNull
    PacketType blockDig();
    @NotNull
    PacketType blockPlace();
    @NotNull
    PacketType boatMove();
    @NotNull
    PacketType chat();
    @NotNull
    PacketType chatAck();
    @NotNull
    PacketType chatCommand();
    @NotNull
    PacketType chatSessionUpdate();
    @NotNull
    PacketType clientCommand();
    @NotNull
    PacketType closeWindow();
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType ground();
    @NotNull
    PacketType difficultyChange();
    @NotNull
    PacketType difficultyLock();
    @NotNull
    PacketType enchantItem();
    @NotNull
    PacketType entityAction();
    @NotNull
    PacketType entityNBTQuery();
    @NotNull
    PacketType heldItemSlot();
    @NotNull
    PacketType itemName();
    @NotNull
    PacketType jigsawGenerate();
    @NotNull
    PacketType keepAlive();
    @NotNull
    PacketType look();
    @NotNull
    PacketType pickItem();
    @NotNull
    PacketType pong();
    @NotNull
    PacketType position();
    @NotNull
    PacketType positionLook();
    @NotNull
    PacketType recipeDisplayed();
    @NotNull
    PacketType recipeSettings();
    @NotNull
    PacketType resourcePackStatus();
    @NotNull
    PacketType setCommandBlock();
    @NotNull
    PacketType setCommandMinecart();
    @NotNull
    PacketType setCreativeSlot();
    @NotNull
    PacketType setJigsaw();
    @NotNull
    PacketType settings();
    @NotNull
    PacketType spectate();
    @NotNull
    PacketType steerVehicle();
    @NotNull
    PacketType struct();
    @NotNull
    PacketType tabComplete();
    @NotNull
    PacketType teleportAccept();
    @NotNull
    PacketType tileNBTQuery();
    @NotNull
    PacketType trSel();
    @NotNull
    PacketType updateSign();
    @NotNull
    PacketType useEntity();
    @NotNull
    PacketType useItem();
    @NotNull
    PacketType vehicleMove();
    @NotNull
    PacketType windowClick();

    @AddedIn("1.20.2")
    @NotNull
    PacketType chunkBatchReceived();
    @AddedIn("1.20.2")
    @NotNull
    PacketType configurationAcknowledged();
    @AddedIn("1.20.2")
    @NotNull
    PacketType pingRequest();
    @AddedIn("1.20.3")
    @NotNull
    PacketType changeContainerSlotState();
}
