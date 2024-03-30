package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.TacticalConfigParent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseConfigItem {
    protected transient TacticalConfigParent parent;
    private boolean exists = true;

    protected BaseConfigItem(TacticalConfigParent parent) {
        this.parent = parent;
    }

    public @NotNull TacticalConfigParent parent() {
        checkExists();
        if (parent == null) throw new IllegalStateException("The current item has no parents");
        return parent;
    }

    public boolean hasParent() {
        checkExists();
        return parent != null;
    }

    public @NotNull List<TacticalConfigParent> ancestors() {
        checkExists();
        List<TacticalConfigParent> ancestors = new ArrayList<>();
        TacticalConfigParent parent = parent();
        while (parent.hasParent()) {
            ancestors.add(parent);
            parent = parent.parent();
        }
        Collections.reverse(ancestors);
        return Collections.unmodifiableList(ancestors);
    }

    public void remove() {
        checkExists();
        parent = null;
        exists = false;
    }

    public boolean exists() {
        checkExists();
        return exists;
    }

    protected void checkExists() {
        if (!exists) throw new IllegalStateException("Item does not exist");
    }
}
