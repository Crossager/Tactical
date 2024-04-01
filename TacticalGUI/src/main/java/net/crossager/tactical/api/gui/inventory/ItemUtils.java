package net.crossager.tactical.api.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Utility class for manipulating ItemStack metadata.
 */
public class ItemUtils {

    /**
     * Represents an ItemStack with air material, indicating an empty slot.
     */
    public static final ItemStack AIR = new ItemStack(Material.AIR);

    /**
     * Sets a value in the metadata of an ItemStack.
     *
     * @param <T>      The type of value to set
     * @param itemStack The ItemStack to modify
     * @param value     The value to set
     * @param consumer  The consumer to apply the value to the ItemMeta
     * @return The modified ItemStack
     */
    public static <T> ItemStack setMetaValue(ItemStack itemStack, T value, BiConsumer<ItemMeta, T> consumer) {
        return modifyMeta(itemStack, itemMeta -> consumer.accept(itemMeta, value));
    }

    /**
     * Sets the display name of an ItemStack.
     *
     * @param itemStack The ItemStack to modify
     * @param name      The display name to set
     * @return The modified ItemStack
     */
    public static ItemStack setName(ItemStack itemStack, String name) {
        return setMetaValue(itemStack, name, ItemMeta::setDisplayName);
    }

    /**
     * Sets the lore of an ItemStack.
     *
     * @param itemStack The ItemStack to modify
     * @param lore      The lore to set
     * @return The modified ItemStack
     */
    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        return setMetaValue(itemStack, lore, ItemMeta::setLore);
    }

    /**
     * Sets the lore of an ItemStack by splitting the description into lines
     *
     * @param description The description to set
     * @return The modified ItemStack
     */
    public static ItemStack setDescription(ItemStack itemStack, String description) {
        return setMetaValue(itemStack, Arrays.asList(description.split("\n")), ItemMeta::setLore);
    }

    /**
     * Modifies the metadata of an ItemStack.
     *
     * @param itemStack The ItemStack to modify
     * @param modifier  The consumer to apply modifications to the ItemMeta
     * @return The modified ItemStack
     */
    public static ItemStack modifyMeta(ItemStack itemStack, Consumer<ItemMeta> modifier) {
        ItemMeta meta = itemStack.getItemMeta();
        modifier.accept(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Modifies custom metadata of an ItemStack.
     *
     * @param <T>             The type of custom metadata
     * @param itemStack       The ItemStack to modify
     * @param modifier        The consumer to apply modifications to the custom ItemMeta
     * @param customMetaClass The class representing the custom metadata type
     * @return The modified ItemStack
     * @throws IllegalArgumentException if the provided ItemMeta is not compatible with the custom metadata type
     */
    public static <T extends ItemMeta> ItemStack modifyCustomMeta(ItemStack itemStack, Consumer<T> modifier, Class<T> customMetaClass) {
        ItemMeta meta = itemStack.getItemMeta();
        if (!customMetaClass.isInstance(meta))
            throw new IllegalArgumentException("Provided item meta %s, is not applicable to meta type %s".formatted(meta, customMetaClass.getName()));
        modifier.accept(customMetaClass.cast(meta));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Sets the owner of a skull ItemStack.
     *
     * @param itemStack  The ItemStack representing a skull
     * @param skullOwner The name of the skull's owner
     * @return The modified ItemStack
     */
    public static ItemStack setSkullOwner(ItemStack itemStack, String skullOwner) {
        return modifyCustomMeta(itemStack, itemMeta -> {
            itemMeta.setOwnerProfile(Bukkit.createPlayerProfile(skullOwner));
        }, SkullMeta.class);
    }
}
