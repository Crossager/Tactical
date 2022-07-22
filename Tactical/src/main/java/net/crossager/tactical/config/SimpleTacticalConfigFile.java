package net.crossager.tactical.config;

import net.crossager.tactical.api.config.*;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.checks.Checks;
import net.crossager.tactical.config.items.SimpleTacticalConfigSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleTacticalConfigFile extends SimpleTacticalConfigSection implements TacticalFileConfig {
    private TacticalConfigSerializer serializer;
    private final File file;
    private final SimpleTacticalConfigOptions options;

    public SimpleTacticalConfigFile(TacticalConfigSerializer serializer, File file) throws IOException {
        super(null);
        Checks.canReadWrite(file);
        this.serializer = serializer;
        this.file = file;
        options = new SimpleTacticalConfigOptions(true, false, 2);
    }

    private File getSavedFile() {
        if (file.getName().endsWith("." + serializer.format()))
            return file;
        else
            return new File(file.getPath() + "." + serializer.format());
    }

    @Override
    public @NotNull TacticalConfigParent parent() {
        throw new NoSuchElementException("Config file does not have a parent");
    }

    @Override
    public @NotNull File file() {
        return file;
    }

    @Override
    public void reload() throws IOException {
        reload(true);
    }

    @Override
    public void reload(boolean reset) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(getSavedFile())) {
            loadRaw(reset, new String(inputStream.readAllBytes()));
        }
    }

    @Override
    public void loadRaw(boolean reset, String raw) {
        if (reset) clearChildren();
        serializer.deserialize(raw, this);
    }

    @Override
    public void save() throws IOException {
        String data = serializer.serialize(file.getName(), options, children());
        FileOutputStream out = new FileOutputStream(getSavedFile());
        out.write(data.getBytes());
        out.close();
    }

    @Override
    public @NotNull TacticalConfigSerializer serializer() {
        return serializer;
    }

    @Override
    public void serializer(TacticalConfigSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public @NotNull TacticalConfigOptions options() {
        return options;
    }

    @Override
    public String toString() {
        return getSavedFile().getName() + ": " + children();
    }

    @Override
    public @NotNull List<TacticalConfigParent> ancestors() {
        return List.of();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Config file cannot be removed!");
    }
}
