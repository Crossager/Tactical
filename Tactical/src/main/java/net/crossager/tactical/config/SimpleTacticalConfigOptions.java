package net.crossager.tactical.config;

import net.crossager.tactical.api.config.TacticalConfigOptions;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalConfigOptions implements TacticalConfigOptions {
    private boolean prettyPrint;
    private boolean autoSave;
    private int tabSize;

    public SimpleTacticalConfigOptions(boolean prettyPrint, boolean autoSave, int tabSize) {
        this.prettyPrint = prettyPrint;
        this.autoSave = autoSave;
        this.tabSize = tabSize;
    }

    @Override
    public boolean prettyPrint() {
        return prettyPrint;
    }

    @Override
    public @NotNull SimpleTacticalConfigOptions prettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        return this;
    }

    public boolean autoSave() {
        return autoSave;
    }

    public SimpleTacticalConfigOptions autoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    @Override
    public int tabSize() {
        return tabSize;
    }

    @Override
    public @NotNull SimpleTacticalConfigOptions tabSize(int tabSize) {
        this.tabSize = tabSize;
        return this;
    }

    @Override
    public String toString() {
        return "ConfigOptions{" +
                "prettyPrint=" + prettyPrint +
                ", tabSize=" + tabSize +
                '}';
    }
}
