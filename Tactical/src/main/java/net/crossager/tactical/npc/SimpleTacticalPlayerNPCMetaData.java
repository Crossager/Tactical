package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Pose;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

public final class SimpleTacticalPlayerNPCMetaData implements TacticalPlayerNPCMetaData {
    private final EnumSet<ChangedMetaData> changedMetaData;

    private final UUID profileId;
    private final int entityId;
    private final String profileName;
    private final TacticalPlayerSkin skin;
    private final SimpleTacticalPlayerEquipment equipment;
    private Location location;
    private boolean useCustomHeadRotation = false;
    private float customHeadYaw = 0;
    private Set<TacticalPlayerSkinParts> displaySkinParts = EnumSet.allOf(TacticalPlayerSkinParts.class);
    private boolean isLeftHanded = false;
    private boolean isOnFire = false;
    private boolean isSprinting = false;
    private boolean isInvisible = false;
    private boolean hasGlowingEffect = false;
    private boolean isFlyingWithElytra = false;
    private Pose pose = Pose.STANDING;
    private String customTabListName = "";
    private boolean useCustomTabListName = false;
    private boolean showInTab = true;
    private int ping = 0;
    private GameMode gameMode = GameMode.SURVIVAL;
    private boolean isFrozen = false;
    private int arrowsInBody = 0;
    private int stingersInBody = 0;

    public SimpleTacticalPlayerNPCMetaData(
            EnumSet<ChangedMetaData> changedMetaData,
            Location location,
            int entityId,
            String profileName,
            TacticalPlayerSkin skin) {
        this.changedMetaData = changedMetaData;
        this.skin = skin;
        this.profileId = UUID.randomUUID();
        this.location = location;
        this.entityId = entityId;
        this.profileName = profileName;
        equipment = new SimpleTacticalPlayerEquipment(() -> changedMetaData.add(ChangedMetaData.EQUIPMENT));
    }

    @Override
    public UUID profileId() {
        return profileId;
    }

    @Override
    public Location location() {
        return location.clone();
    }

    public boolean useCustomHeadRotation() {
        return useCustomHeadRotation;
    }

    @Override
    public float customHeadYaw() {
        return customHeadYaw;
    }

    @Override
    public int entityId() {
        return entityId;
    }

    @Override
    public TacticalPlayerSkin skin() {
        return skin;
    }

    @Override
    public TacticalPlayerEquipment equipment() {
        return equipment;
    }

    @Override
    public String profileName() {
        return profileName;
    }

    @Override
    public Set<TacticalPlayerSkinParts> displaySkinParts() {
        return displaySkinParts;
    }

    @Override
    public boolean leftHanded() {
        return isLeftHanded;
    }

    @Override
    public boolean onFire() {
        return isOnFire;
    }

    @Override
    public boolean sprinting() {
        return isSprinting;
    }

    @Override
    public boolean invisible() {
        return isInvisible;
    }

    @Override
    public boolean glowingEffect() {
        return hasGlowingEffect;
    }

    @Override
    public boolean flyingWithElytra() {
        return isFlyingWithElytra;
    }

    @Override
    public boolean showInTab() {
        return showInTab;
    }

    @Override
    public String customTabListName() {
        return customTabListName;
    }

    public boolean useCustomTabListName() {
        return useCustomTabListName;
    }

    @Override
    public int ping() {
        return ping;
    }

    @Override
    public Pose pose() {
        return pose;
    }

    @Override
    public GameMode gameMode() {
        return gameMode;
    }

    @Override
    public TacticalPlayerNPCMetaData displaySkinParts(Set<TacticalPlayerSkinParts> displaySkinParts) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.displaySkinParts = displaySkinParts;
        return null;
    }

    @Override
    public TacticalPlayerNPCMetaData location(Location location) {
        changedMetaData.add(ChangedMetaData.LOCATION);
        this.location = location.clone();
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData customHeadYaw(float customHeadYaw) {
        changedMetaData.add(ChangedMetaData.LOCATION);
        useCustomHeadRotation = true;
        this.customHeadYaw = customHeadYaw;
        return this;
    }

    @Override
    public void resetToDefaultHeadYaw() {
        changedMetaData.add(ChangedMetaData.LOCATION);
        useCustomHeadRotation = false;
    }

    @Override
    public TacticalPlayerNPCMetaData leftHanded(boolean isLeftHanded) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isLeftHanded = isLeftHanded;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData onFire(boolean isOnFire) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isOnFire = isOnFire;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData sprinting(boolean isSprinting) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isSprinting = isSprinting;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData invisible(boolean isInvisible) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isInvisible = isInvisible;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData glowingEffect(boolean hasGlowingEffect) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.hasGlowingEffect = hasGlowingEffect;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData flyingWithElytra(boolean isFlyingWithElytra) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isFlyingWithElytra = isFlyingWithElytra;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData showInTab(boolean showInTab) {
        changedMetaData.add(ChangedMetaData.PLAYER_INFO_LISTED);
        this.showInTab = showInTab;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData customTabListName(String customTabListName) {
        changedMetaData.add(ChangedMetaData.PLAYER_INFO_DISPLAY_NAME);
        this.useCustomTabListName = true;
        this.customTabListName = customTabListName;
        return this;
    }

    @Override
    public void resetToDefaultTabListName() {
        changedMetaData.add(ChangedMetaData.PLAYER_INFO_DISPLAY_NAME);
        this.useCustomTabListName = false;
    }

    @Override
    public TacticalPlayerNPCMetaData ping(int ping) {
        changedMetaData.add(ChangedMetaData.PLAYER_INFO_LATENCY);
        this.ping = ping;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData pose(Pose pose) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.pose = pose;
        return this;
    }

    @Override
    public TacticalPlayerNPCMetaData gameMode(GameMode gameMode) {
        changedMetaData.add(ChangedMetaData.PLAYER_INFO_GAME_MODE);
        this.gameMode = gameMode;
        return this;
    }

    @Override
    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public TacticalPlayerNPCMetaData frozen(boolean isFrozen) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.isFrozen = isFrozen;
        return this;
    }

    @Override
    public int arrowsInBody() {
        return arrowsInBody;
    }

    @Override
    public TacticalPlayerNPCMetaData arrowsInBody(int arrowsInBody) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.arrowsInBody = arrowsInBody;
        return this;
    }

    @Override
    public int stingersInBody() {
        return stingersInBody;
    }

    @Override
    public TacticalPlayerNPCMetaData stingersInBody(int stingersInBody) {
        changedMetaData.add(ChangedMetaData.META_DATA);
        this.stingersInBody = stingersInBody;
        return this;
    }

    public enum ChangedMetaData {
        LOCATION,
        META_DATA,
        PLAYER_INFO_LISTED,
        PLAYER_INFO_LATENCY,
        PLAYER_INFO_GAME_MODE,
        PLAYER_INFO_DISPLAY_NAME,
        EQUIPMENT
    }
}
