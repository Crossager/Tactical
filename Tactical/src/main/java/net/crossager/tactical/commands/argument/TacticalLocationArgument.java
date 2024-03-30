package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.api.util.TacticalUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TacticalLocationArgument extends TacticalCommandArgumentBase {
    private final String formatString;

    public TacticalLocationArgument(String name, int decimals) {
        this.formatString = "%s.%sf %s.%sf %s.%sf".formatted('%', decimals, '%', decimals, '%', decimals);
        name(name);
        argumentLength(3);
        shouldAutoFilterTabCompletion(false);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context) {
        if (context.arguments().get(0).startsWith("^"))
            return split("^ ^ ^", context.arguments());
        return TacticalUtils.addTo(!context.hasTargetLocation() ? List.of() : split(formatString.formatted(
                context.targetLocation().getX(),
                context.targetLocation().getY(),
                context.targetLocation().getZ()
        ), context.arguments()), split("~ ~ ~", context.arguments()));
    }

    private List<String> split(String str, List<String> arguments) {
        String[] split = str.split(" ");
        List<String> result = new ArrayList<>();
        int argIndex = arguments.size() - 1;
        for (int i = 0; i < 3 - argIndex; i++) {
            String[] ar = new String[i + 1];
            for (int j = 0; j < ar.length; j++) {
                ar[j] = j == 0 && !arguments.get(argIndex).isEmpty() ?
                                arguments.get(argIndex) :
                                split[argIndex + j];
            }
            result.add(String.join(" ", ar));
        }
        return result;
    }

    @Override
    public Object parse(@NotNull TacticalCommandArgumentActionContext context) {
        double x, y, z;
        Location relative = context.sender() instanceof Entity entity ?
                entity.getLocation() :
                new Location(null, 0, 0, 0);
        String xs = context.arguments().get(0);
        String ys = context.arguments().get(1);
        String zs = context.arguments().get(2);
        {
            if (xs.charAt(0) == '~')
                x = parseRelativePosition(xs, relative.getX());
            else
                x = Double.parseDouble(xs);

            if (ys.charAt(0) == '~')
                y = parseRelativePosition(ys, relative.getY());
            else
                y = Double.parseDouble(ys);

            if (zs.charAt(0) == '~')
                z = parseRelativePosition(zs, relative.getZ());
            else
                z = Double.parseDouble(zs);
        }

        return new Location(
                relative.getWorld(),
                x, y, z,
                relative.getYaw(),
                relative.getPitch()
        );
    }

    private double parseRelativePosition(String in, double delta) {
        if (in.length() == 1)
            return delta;
        else
            return delta + Double.parseDouble(in.substring(1));
    }
}
