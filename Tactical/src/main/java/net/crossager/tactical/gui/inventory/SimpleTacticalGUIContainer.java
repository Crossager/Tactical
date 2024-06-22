package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.ItemMoveAction;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIContainer;
import net.crossager.tactical.api.gui.inventory.components.TacticalItemGUIComponent;
import net.crossager.tactical.gui.GUIUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public class SimpleTacticalGUIContainer implements TacticalGUIContainer {
    private final int width;
    private final int height;
    private final ComponentRepresentation[][] components;

    public SimpleTacticalGUIContainer(int width, int height) {
        GUIUtils.checkDimensions(width, height);
        this.width = width;
        this.height = height;
        this.components = new ComponentRepresentation[width + (height * width)][];
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public @NotNull ItemStack itemAt(@NotNull Player player, int x, int y, int ticksSinceLastUpdate) {
        GUIUtils.notOutOfBounds(x, y, this);
        return row(x)[y].item(player, ticksSinceLastUpdate);
    }

    @Override
    public void click(@NotNull TacticalGUIClickEvent event) {
        GUIUtils.notOutOfBounds(event.x(), event.y(), this);
        ComponentRepresentation representation = row(event.x())[event.y()];
        if (representation == ComponentRepresentation.EMPTY) return;
        representation.component().click(new SimpleTacticalFromGUIClickEvent(event, representation.modX(), representation.modY()));
    }

    @Override
    public boolean canItemBeMovedTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        GUIUtils.notOutOfBounds(x, y, this);
        ComponentRepresentation representation = row(x)[y];
        if (representation == ComponentRepresentation.EMPTY) return false;
        return representation.component().canItemBeMovedTo(player, representation.modX(), representation.modY(), itemStack, action);
    }

    @Override
    public void moveItemTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        GUIUtils.notOutOfBounds(x, y, this);
        ComponentRepresentation representation = row(x)[y];
        if (representation == ComponentRepresentation.EMPTY) return;
        representation.component().moveItemTo(player, representation.modX(), representation.modY(), itemStack, action);
    }

    @Override
    public boolean canCollectItemAt(@NotNull Player player, int x, int y) {
        GUIUtils.notOutOfBounds(x, y, this);
        ComponentRepresentation representation = row(x)[y];
        if (representation == ComponentRepresentation.EMPTY) return false;
        return representation.component().canCollectItemAt(player, representation.modX(), representation.modY());
    }

    @Override
    public Optional<Object> collectItemAt(@NotNull Player player, int x, int y) {
        GUIUtils.notOutOfBounds(x, y, this);
        ComponentRepresentation representation = row(x)[y];
        if (representation == ComponentRepresentation.EMPTY) return Optional.empty();
        return representation.component().collectItemAt(player, representation.modX(), representation.modY());
    }

    @Override
    public @NotNull TacticalGUIContainer setComponent(int x, int y, @NotNull TacticalGUIComponent component) {
        GUIUtils.checkComponent(component);
        GUIUtils.notOutOfBounds(x, y, this);
        GUIUtils.fits(x, y, component, this);
        removeComponent(x, y);
        if (component.width() == 1 && component.height() == 1) {
            row(x)[y] = new ComponentRepresentation(component, 0, 0);
            return this;
        }
        for (int ix = x; ix < x + component.width(); ix++) {
            for (int iy = y; iy < y + component.height(); iy++) {
                row(ix)[iy] = new ComponentRepresentation(component, ix - x, iy - y);
            }
        }
        return this;
    }

    @Override
    public @NotNull TacticalGUIContainer setItem(int x, int y, @NotNull ItemStack itemStack) {
        return setComponent(x, y, TacticalItemGUIComponent.of(itemStack));
    }

    @Override
    public @NotNull TacticalGUIContainer removeComponent(int x, int y) {
        GUIUtils.notOutOfBounds(x, y, this);
        ComponentRepresentation rep = row(x)[y];
        if (rep == ComponentRepresentation.EMPTY) return this;
        if (rep.component().width() == 1 && rep.component().height() == 1) {
            row(x)[y] = ComponentRepresentation.EMPTY;
            return this;
        }
        for (int ix = x - rep.modX(); ix < x - rep.modX() + rep.component().width(); ix++) {
            ComponentRepresentation[] row = row(ix);
            for (int iy = y - rep.modY(); iy < y - rep.modY() + rep.component().height(); iy++) {
                row[iy] = ComponentRepresentation.EMPTY;
            }
        }
        return this;
    }

    @Override
    public @NotNull TacticalGUIContainer createBorder(@NotNull TacticalGUIComponent component) {
        GUIUtils.checkComponent(component);
        if (component.width() != 1 || component.height() != 1)
            throw new IllegalArgumentException("Border component must have dimensions (1,1), component had (%s,%s)".formatted(component.width(), component.height()));
        for (int x = 0; x < width; x++) {
            setComponent(x, 0, component);
            if (height > 1)
                setComponent(x, height - 1, component);
        }
        if (height < 3) return this;
        for (int y = 1; y < height - 1; y++) {
            setComponent(0, y, component);
            setComponent(width - 1, y, component);
        }
        return this;
    }

    @Override
    public @NotNull TacticalGUIContainer createBorder(@NotNull ItemStack itemStack) {
        return createBorder(TacticalItemGUIComponent.of(itemStack));
    }

    @Override
    public @NotNull TacticalGUIContainer fillComponent(int fromX, int fromY, int toX, int toY, @NotNull TacticalGUIComponent component) {
        GUIUtils.checkComponent(component);
        GUIUtils.notOutOfBounds(fromX, fromY, this);
        GUIUtils.notOutOfBounds(toX, toY, this);
        if (component.width() != 1 || component.height() != 1)
            throw new IllegalArgumentException("Fill component must have dimensions (1,1), component had (%s,%s)".formatted(component.width(), component.height()));
        if (fromX > toX) throw new IllegalArgumentException("fromX (%s) cannot be larger than toX (%s)".formatted(fromY, toY));
        if (fromY > toY) throw new IllegalArgumentException("fromY (%s) cannot be larger than toY (%s)".formatted(fromY, toY));
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                setComponent(x, y, component);
            }
        }
        return this;
    }

    @Override
    public @NotNull Optional<TacticalGUIComponent> getComponent(int x, int y) {
        ComponentRepresentation representation = row(x)[y];
        if (representation == ComponentRepresentation.EMPTY) return Optional.empty();
        return Optional.of(representation.component());
    }

    protected ComponentRepresentation[] row(int column) {
        if (components[column] == null) {
            ComponentRepresentation[] componentRepresentations = new ComponentRepresentation[width];
            Arrays.fill(componentRepresentations, ComponentRepresentation.EMPTY);
            return components[column] = componentRepresentations;
        }
        return components[column];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append('\n');
        for (int y = 0; y < height; y++) {
            builder.append(" [");
            for (int x = 0; x < width; x++) {
                ComponentRepresentation representation = row(x)[y];
                if (representation == ComponentRepresentation.EMPTY)
                    builder.append("EMPTY");
                else
                    builder.append(representation.component());

                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length() - 1);
            builder.append("]\n");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
