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

public class ItemBuilder extends ItemStack {
    private ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    private ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Sets a value in the metadata of
     *
     * @param <T>      The type of value to set
     * @param value     The value to set
     * @param consumer  The consumer to apply the value to the ItemMeta
     * @return This ItemBuilder
     */
    public <T> ItemBuilder setMetaValue(T value, BiConsumer<ItemMeta, T> consumer) {
        return modifyMeta(itemMeta -> consumer.accept(itemMeta, value));
    }

    /**
     * Sets the display name
     *
     * @param name      The display name to set
     * @return This ItemBuilder
     */
    public ItemBuilder setName(String name) {
        return setMetaValue(name, ItemMeta::setDisplayName);
    }

    /**
     * Sets the lore
     *
     * @param lore      The lore to set
     * @return This ItemBuilder
     */
    public ItemBuilder setLore(List<String> lore) {
        return setMetaValue(lore, ItemMeta::setLore);
    }

    /**
     * Sets the lore by splitting the description into lines
     *
     * @param description The description to set
     * @return This ItemBuilder
     */
    public ItemBuilder setDescription(String description) {
        return setMetaValue(Arrays.asList(description.split("\n")), ItemMeta::setLore);
    }

    /**
     * Modifies the metadata
     *
     * @param modifier  The consumer to apply modifications to the ItemMeta
     * @return This ItemBuilder
     */
    public ItemBuilder modifyMeta(Consumer<ItemMeta> modifier) {
        ItemMeta meta = getItemMeta();
        modifier.accept(meta);
        setItemMeta(meta);
        return this;
    }

    /**
     * Modifies custom metadata
     *
     * @param <T>             The type of custom metadata
     * @param modifier        The consumer to apply modifications to the custom ItemMeta
     * @param customMetaClass The class representing the custom metadata type
     * @return This ItemBuilder
     * @throws IllegalArgumentException if the provided ItemMeta is not compatible with the custom metadata type
     */
    public <T extends ItemMeta> ItemBuilder modifyCustomMeta(Consumer<T> modifier, Class<T> customMetaClass) {
        ItemMeta meta = getItemMeta();
        if (!customMetaClass.isInstance(meta))
            throw new IllegalArgumentException("Provided item meta %s, is not applicable to meta type %s".formatted(meta, customMetaClass.getName()));
        modifier.accept(customMetaClass.cast(meta));
        setItemMeta(meta);
        return this;
    }

    /**
     * Sets the owner of a skull ItemStack.
     *
     * @param skullOwner The name of the skull's owner
     * @return This ItemBuilder
     */
    public ItemBuilder setSkullOwner(String skullOwner) {
        return modifyCustomMeta(itemMeta -> {
            itemMeta.setOwnerProfile(Bukkit.createPlayerProfile(skullOwner));
        }, SkullMeta.class);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material, 1);
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }
}
