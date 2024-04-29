package net.crossager.tactical.api.npc;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Pose;

import java.util.Set;
import java.util.UUID;

public interface TacticalPlayerNPCMetaData {
    UUID profileId();

    int entityId();

    String profileName();

    TacticalPlayerSkin skin();

    TacticalPlayerEquipment equipment();

    Set<TacticalPlayerSkinParts> displaySkinParts();

    TacticalPlayerNPCMetaData displaySkinParts(Set<TacticalPlayerSkinParts> displaySkinParts);

    Location location();

    TacticalPlayerNPCMetaData location(Location location);

    float customHeadYaw();

    TacticalPlayerNPCMetaData customHeadYaw(float customHeadYaw);

    void resetToDefaultHeadYaw();

    boolean leftHanded();

    TacticalPlayerNPCMetaData leftHanded(boolean isLeftHanded);

    boolean onFire();

    TacticalPlayerNPCMetaData onFire(boolean isOnFire);

    boolean sprinting();

    TacticalPlayerNPCMetaData sprinting(boolean isSprinting);

    boolean invisible();

    TacticalPlayerNPCMetaData invisible(boolean isInvisible);

    boolean glowingEffect();

    TacticalPlayerNPCMetaData glowingEffect(boolean hasGlowingEffect);

    boolean flyingWithElytra();

    TacticalPlayerNPCMetaData flyingWithElytra(boolean isFlyingWithElytra);

    boolean showInTab();

    TacticalPlayerNPCMetaData showInTab(boolean showInTab);

    String customTabListName();

    TacticalPlayerNPCMetaData customTabListName(String customTabListName);

    void resetToDefaultTabListName();

    int ping();

    TacticalPlayerNPCMetaData ping(int ping);

    Pose pose();

    TacticalPlayerNPCMetaData pose(Pose pose);

    GameMode gameMode();

    TacticalPlayerNPCMetaData gameMode(GameMode gameMode);

    boolean isFrozen();

    TacticalPlayerNPCMetaData frozen(boolean isFrozen);

    int arrowsInBody();

    TacticalPlayerNPCMetaData arrowsInBody(int arrowsInBody);

    int stingersInBody();

    TacticalPlayerNPCMetaData stingersInBody(int stingersInBody);
}
