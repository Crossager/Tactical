package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.PlayInContainer;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

public class ProtocolPlayInContainer extends ProtocolContainerBase implements PlayInContainer {
    public ProtocolPlayInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType abilities = get("Abilities", "CPacketPlayerAbilities");
    private final PacketType advancements = get("Advancements", "CPacketSeenAdvancements");
    private final PacketType armAnimation = get("ArmAnimation", "CPacketAnimation");
    private final PacketType autoRecipe = get("AutoRecipe", "CPacketPlaceRecipe");
    private final PacketType beacon = get("Beacon");
    private final PacketType bookEdit = get("BEdit");
    private final PacketType blockDig = get("BlockDig", "CPacketPlayerDigging");
    private final PacketType blockPlace = get("BlockPlace", "CPacketPlayerTryUseItem");
    private final PacketType boatMove = get("BoatMove", "CPacketSteerBoat");
    private final PacketType chat = get("Chat", "CPacketChatMessage");
    private final PacketType chatAck = get("ChatAck");
    private final PacketType chatCommand = get("ChatCommand");
    private final PacketType chatSessionUpdate = get("ChatSessionUpdate");
    private final PacketType clientCommand = get("ClientCommand", "CPacketClientStatus");
    private final PacketType closeWindow = get("CloseWindow", "CPacketCloseWindow");
    private final PacketType customPayload = get("CustomPayload", "CPacketCustomPayload");
    private final PacketType ground = get("Flying$d");
    private final PacketType difficultyChange = get("DifficultyChange");
    private final PacketType difficultyLock = get("DifficultyLock");
    private final PacketType enchantItem = get("EnchantItem", "CPacketEnchantItem");
    private final PacketType entityAction = get("EntityAction", "CPacketEntityAction");
    private final PacketType entityNBTQuery = get("EntityNBTQuery");
    private final PacketType heldItemSlot = get("HeldItemSlot", "CPacketHeldItemChange");
    private final PacketType itemName = get("ItemName");
    private final PacketType jigsawGenerate = get("JigsawGenerate");
    private final PacketType keepAlive = get("KeepAlive", "CPacketKeepAlive");
    private final PacketType look = get("Flying$PacketPlayInLook", "Flying$Look", "CPacketPlayer$Rotation");
    private final PacketType pickItem = get("PickItem");
    private final PacketType pong = get("Pong");
    private final PacketType position = get("Flying$PacketPlayInPosition", "Flying$Position", "CPacketPlayer$Position");
    private final PacketType positionLook = get("Flying$PacketPlayInPositionLook", "Flying$PositionLook", "CPacketPlayer$PositionRotation");
    private final PacketType recipeDisplayed = get("RecipeDisplayed", "CPacketRecipeInfo");
    private final PacketType recipeSettings = get("RecipeSettings");
    private final PacketType resourcePackStatus = get("ResourcePackStatus", "CPacketResourcePackStatus");
    private final PacketType setCommandBlock = get("SetCommandBlock");
    private final PacketType setCommandMinecart = get("SetCommandMinecart");
    private final PacketType setCreativeSlot = get("SetCreativeSlot", "CPacketCreativeInventoryAction");
    private final PacketType setJigsaw = get("SetJigsaw");
    private final PacketType settings = get("Settings", "CPacketClientSettings");
    private final PacketType spectate = get("Spectate", "CPacketSpectate");
    private final PacketType steerVehicle = get("SteerVehicle", "CPacketInput");
    private final PacketType struct = get("Struct");
    private final PacketType tabComplete = get("TabComplete", "CPacketTabComplete");
    private final PacketType teleportAccept = get("TeleportAccept", "CPacketConfirmTeleport");
    private final PacketType tileNBTQuery = get("TileNBTQuery");
    private final PacketType trSel = get("TrSel");
    private final PacketType updateSign = get("UpdateSign", "CPacketUpdateSign");
    private final PacketType useEntity = get("UseEntity", "CPacketUseEntity");
    private final PacketType useItem = get("UseItem", "CPacketPlayerTryUseItemOnBlock");
    private final PacketType vehicleMove = get("VehicleMove", "CPacketVehicleMove");
    private final PacketType windowClick = get("WindowClick", "CPacketClickWindow");

    private final PacketType chunkBatchReceived = get("ChunkBatchReceived");
    private final PacketType configurationAcknowledged = get("ConfigurationAcknowledged");
    private final PacketType pingRequest = get("PingRequest");
    private final PacketType changeContainerSlotState = get("ContainerSlotStateChangedPacket");

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
    public PacketType armAnimation() {
        return armAnimation;
    }

    @NotNull
    @Override
    public PacketType autoRecipe() {
        return autoRecipe;
    }

    @NotNull
    @Override
    public PacketType beacon() {
        return beacon;
    }

    @NotNull
    @Override
    public PacketType bookEdit() {
        return bookEdit;
    }

    @NotNull
    @Override
    public PacketType blockDig() {
        return blockDig;
    }

    @NotNull
    @Override
    public PacketType blockPlace() {
        return blockPlace;
    }

    @NotNull
    @Override
    public PacketType boatMove() {
        return boatMove;
    }

    @NotNull
    @Override
    public PacketType chat() {
        return chat;
    }

    @NotNull
    @Override
    public PacketType chatAck() {
        return chatAck;
    }

    @NotNull
    @Override
    public PacketType chatCommand() {
        return chatCommand;
    }

    @NotNull
    @Override
    public PacketType chatSessionUpdate() {
        return chatSessionUpdate;
    }

    @NotNull
    @Override
    public PacketType clientCommand() {
        return clientCommand;
    }

    @NotNull
    @Override
    public PacketType closeWindow() {
        return closeWindow;
    }

    @NotNull
    @Override
    public PacketType customPayload() {
        return customPayload;
    }

    @NotNull
    @Override
    public PacketType ground() {
        return ground;
    }

    @NotNull
    @Override
    public PacketType difficultyChange() {
        return difficultyChange;
    }

    @NotNull
    @Override
    public PacketType difficultyLock() {
        return difficultyLock;
    }

    @NotNull
    @Override
    public PacketType enchantItem() {
        return enchantItem;
    }

    @NotNull
    @Override
    public PacketType entityAction() {
        return entityAction;
    }

    @NotNull
    @Override
    public PacketType entityNBTQuery() {
        return entityNBTQuery;
    }

    @NotNull
    @Override
    public PacketType heldItemSlot() {
        return heldItemSlot;
    }

    @NotNull
    @Override
    public PacketType itemName() {
        return itemName;
    }

    @NotNull
    @Override
    public PacketType jigsawGenerate() {
        return jigsawGenerate;
    }

    @NotNull
    @Override
    public PacketType keepAlive() {
        return keepAlive;
    }

    @NotNull
    @Override
    public PacketType look() {
        return look;
    }

    @NotNull
    @Override
    public PacketType pickItem() {
        return pickItem;
    }

    @NotNull
    @Override
    public PacketType pong() {
        return pong;
    }

    @NotNull
    @Override
    public PacketType position() {
        return position;
    }

    @NotNull
    @Override
    public PacketType positionLook() {
        return positionLook;
    }

    @NotNull
    @Override
    public PacketType recipeDisplayed() {
        return recipeDisplayed;
    }

    @NotNull
    @Override
    public PacketType recipeSettings() {
        return recipeSettings;
    }

    @NotNull
    @Override
    public PacketType resourcePackStatus() {
        return resourcePackStatus;
    }

    @NotNull
    @Override
    public PacketType setCommandBlock() {
        return setCommandBlock;
    }

    @NotNull
    @Override
    public PacketType setCommandMinecart() {
        return setCommandMinecart;
    }

    @NotNull
    @Override
    public PacketType setCreativeSlot() {
        return setCreativeSlot;
    }

    @NotNull
    @Override
    public PacketType setJigsaw() {
        return setJigsaw;
    }

    @NotNull
    @Override
    public PacketType settings() {
        return settings;
    }

    @NotNull
    @Override
    public PacketType spectate() {
        return spectate;
    }

    @NotNull
    @Override
    public PacketType steerVehicle() {
        return steerVehicle;
    }

    @NotNull
    @Override
    public PacketType struct() {
        return struct;
    }

    @NotNull
    @Override
    public PacketType tabComplete() {
        return tabComplete;
    }

    @NotNull
    @Override
    public PacketType teleportAccept() {
        return teleportAccept;
    }

    @NotNull
    @Override
    public PacketType tileNBTQuery() {
        return tileNBTQuery;
    }

    @NotNull
    @Override
    public PacketType trSel() {
        return trSel;
    }

    @NotNull
    @Override
    public PacketType updateSign() {
        return updateSign;
    }

    @NotNull
    @Override
    public PacketType useEntity() {
        return useEntity;
    }

    @NotNull
    @Override
    public PacketType useItem() {
        return useItem;
    }

    @NotNull
    @Override
    public PacketType vehicleMove() {
        return vehicleMove;
    }

    @NotNull
    @Override
    public PacketType windowClick() {
        return windowClick;
    }

    @Override
    public @NotNull PacketType chunkBatchReceived() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return chunkBatchReceived;
    }

    @Override
    public @NotNull PacketType configurationAcknowledged() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return configurationAcknowledged;
    }

    @Override
    public @NotNull PacketType pingRequest() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return pingRequest;
    }

    @Override
    public @NotNull PacketType changeContainerSlotState() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_3);
        return changeContainerSlotState;
    }
}
