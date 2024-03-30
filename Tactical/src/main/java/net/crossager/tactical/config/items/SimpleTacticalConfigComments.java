package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.TacticalConfigComments;
import net.crossager.tactical.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SimpleTacticalConfigComments implements TacticalConfigComments {
    private final List<String> inlineComments = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();

    @Override
    public @NotNull List<String> comments() {
        return comments;
    }

    @Override
    public boolean hasInlineComments() {
        return !inlineComments.isEmpty();
    }

    @Override
    public boolean hasComments() {
        return !comments.isEmpty();
    }

    @Override
    public @NotNull List<String> inlineComments() {
        return inlineComments;
    }

    @Override
    public void removeInlineComments() {
        this.inlineComments.clear();
    }

    @Override
    public void removeComments() {
        this.comments.clear();
    }

    @Override
    public void addInlineComments(@NotNull String inlineComment, @NotNull String... inlineComments) {
        this.inlineComments.add(Checks.notNull(inlineComment));
        this.inlineComments.addAll(Arrays.asList(Checks.notNull(inlineComments)));
    }

    @Override
    public void addInlineComments(@NotNull Collection<String> comments) {
        this.inlineComments.addAll(Checks.notNull(comments));
    }

    @Override
    public void addComments(@NotNull String comment, @NotNull String... comments) {
        this.comments.add(Checks.notNull(comment));
        this.comments.addAll(Arrays.asList(Checks.notNull(comments)));
    }

    @Override
    public void addComments(@NotNull Collection<String> comments) {
        this.comments.addAll(Checks.notNull(comments));
    }
}
