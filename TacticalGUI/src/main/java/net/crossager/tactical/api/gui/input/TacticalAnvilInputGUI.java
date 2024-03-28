package net.crossager.tactical.api.gui.input;

import net.crossager.tactical.api.TacticalGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link TacticalInputGUI} that represents an anvil input GUI, used for retrieving a player input.
 * <pre>{@code
 * // Create a new TacticalAnvilInputGUI instance
 * TacticalAnvilInputGUI gui = TacticalAnvilInputGUI.create();
 *
 * // Set the title of the GUI
 * gui.title("Enter your name:");
 *
 * // A list of words you cant have as your name
 * List<String> bannedWords = List.of("shit", "damn");
 *
 * // Set a validator to ensure the input is not a bad word
 * gui.renamingValidator(input -> !bannedWords.contains(input));
 *
 * // Add a listener to handle the input
 * gui.onClose((player, input) -> {
 *     player.sendMessage("Hello, " + input + "!");
 * });
 *
 * // Open the GUI for the player
 * gui.open(player);}</pre>
 */
public interface TacticalAnvilInputGUI extends TacticalInputGUI<TacticalAnvilInputGUI.AnvilInputEvent, TacticalAnvilInputGUI.AnvilInputEvent> {
    /**
     * Sets the title of this anvil input GUI.
     * @param title the title of the anvil input GUI
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI title(@NotNull String title);

    /**
     * Resets the title of this anvil input GUI to its default value.
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("-> this")
    TacticalAnvilInputGUI resetTitle();

    /**
     * Sets the item in the left slot of the anvil input GUI.
     * @param itemStack the item to set in the left slot of the anvil input GUI
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI item(@NotNull ItemStack itemStack);

    /**
     * Sets whether the inventory items should be hidden when the anvil input GUI is open.
     * @param hideInventoryItems {@code true} if the inventory items should be hidden, {@code false} otherwise
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI hideInventoryItems(boolean hideInventoryItems);

    /**
     * Sets the item mapper function used to set the output item based on a name
     * @param itemMapper the item mapper function to use
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI nameToItemMapper(@NotNull Function<String, ItemStack> itemMapper);

    /**
     * Sets the item name modifier used to modify the name of the item in the right slot of the anvil input GUI.
     * @param nameModifier the name modifier to use
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI itemNameModifier(@NotNull BiConsumer<ItemStack, String> nameModifier);

    /**
     * Resets the item mapper function to its default value.
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("-> this")
    TacticalAnvilInputGUI resetItemMapper();
    /**
     * Sets the listener that will be called when the player changes the item name in the anvil input GUI.
     * @param onRenaming the listener to call when the player renames the item
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI onRenaming(@NotNull RenamingListener onRenaming);

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract("_ -> this")
    @Override
    TacticalAnvilInputGUI onClose(@NotNull TacticalInputGUIListener<TacticalAnvilInputGUI.AnvilInputEvent> listener);

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract("_ -> this")
    @Override
    TacticalAnvilInputGUI onCancel(@NotNull TacticalInputGUIListener<TacticalAnvilInputGUI.AnvilInputEvent> listener);

    /**
     * Sets the renaming validator that will be used to check the input from the player.
     * @param validator the renaming validator to set
     * @return this {@link TacticalAnvilInputGUI} instance for method chaining
     */
    @NotNull
    @Contract("_ -> this")
    TacticalAnvilInputGUI renamingValidator(@NotNull Predicate<String> validator);

    /**
     * Creates a new instance of {@link TacticalAnvilInputGUI}.
     * @return a new instance of {@link TacticalAnvilInputGUI}
     */
    @NotNull
    static TacticalAnvilInputGUI create() {
        return TacticalGUI.getInstance().getGUIFactory().createAnvilInputGUI();
    }

    /**
     * An interface defining a listener for when the player finishes renaming an item in the GUI.
     */
     interface RenamingListener {
         /**
          * Called when the player is renaming an item in the GUI.
          * @param player the player who is renaming the item
          * @param renamedText the text that the item is renamed to
         */
         void listen(@NotNull Player player, @NotNull String renamedText);
    }

    /**
     * An interface defining the event data that is passed to listeners of the {@link TacticalAnvilInputGUI}.
     */
     interface AnvilInputEvent {
         /**
          * Sets whether the event is cancelled or not.
          * @param cancelled whether the event is cancelled or not
         */
         void setCancelled(boolean cancelled);

         /**
          * Returns whether the event is cancelled or not.
          * @return {@code true} if the event is cancelled, {@code false} otherwise
         */
         boolean isCancelled();

         /**
          * Returns the text that the item was renamed to.
          * @return the text that the item was renamed to
         */
         @NotNull
         String renamedText();
     }
}
