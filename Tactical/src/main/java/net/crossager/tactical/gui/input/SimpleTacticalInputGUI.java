package net.crossager.tactical.gui.input;

import net.crossager.tactical.api.gui.input.TacticalInputGUI;
import net.crossager.tactical.api.gui.input.TacticalInputGUIListener;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleTacticalInputGUI<T, C, G extends TacticalInputGUI<T, C>> implements TacticalInputGUI<T, C> {
    protected TacticalInputGUIListener<T> onCloseListener = TacticalInputGUIListener.none();
    protected TacticalInputGUIListener<C> onCancelListener = TacticalInputGUIListener.none();

    @Override
    public @NotNull G onClose(@NotNull TacticalInputGUIListener<T> listener) {
        this.onCloseListener = listener;
        return getThis();
    }

    @Override
    public @NotNull G onCancel(@NotNull TacticalInputGUIListener<C> listener) {
        this.onCancelListener = listener;
        return getThis();
    }

    protected abstract G getThis();
}
