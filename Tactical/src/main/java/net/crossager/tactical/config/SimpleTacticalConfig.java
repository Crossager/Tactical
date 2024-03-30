package net.crossager.tactical.config;

import net.crossager.tactical.api.config.*;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.util.Checks;
import net.crossager.tactical.config.items.SimpleTacticalConfigSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleTacticalConfig extends SimpleTacticalConfigSection implements TacticalConfig {
    private TacticalConfigSerializer serializer;
    private final File file;
    private final SimpleTacticalConfigOptions options;

    public SimpleTacticalConfig(TacticalConfigSerializer serializer, File file) throws IOException {
        super(null);
        Checks.canReadWrite(file);
        this.serializer = serializer;
        this.file = file;
        options = new SimpleTacticalConfigOptions(true, false, 2);
        reload();
    }

    private File normalizeFile(File file) {
        if (file.getName().endsWith("." + serializer.format()))
            return file;
        else
            return new File(file.getAbsolutePath() + "." + serializer.format());
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
        if (!normalizeFile(file).exists()) return;
        try (FileInputStream inputStream = new FileInputStream(normalizeFile(file))) {
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
        File file = normalizeFile(this.file.getAbsoluteFile());
        file.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(file);
        out.write(saveAsString().getBytes());
        out.close();
    }

    @Override
    public @NotNull String saveAsString() throws IOException {
        return serializer.serialize(file.getName(), options, children());
    }

    @Override
    public @NotNull TacticalConfigSerializer serializer() {
        return serializer;
    }

    @Override
    public void serializer(@NotNull TacticalConfigSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public @NotNull TacticalConfigOptions options() {
        return options;
    }

    @Override
    public String toString() {
        return file.getName() + ": " + children();
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
