package net.crossager.tactical.commands.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.api.util.TacticalUtils;
import net.minecraft.commands.arguments.coordinates.ArgumentVectorPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TacticalLocationArgument extends TacticalCommandArgumentBase {
    private static final Field bField;
    private static final Field cField;
    private static final Field dField;

    static {
        try {
            bField = ArgumentVectorPosition.class.getDeclaredField("b");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        try {
            cField = ArgumentVectorPosition.class.getDeclaredField("c");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        try {
            dField = ArgumentVectorPosition.class.getDeclaredField("d");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

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
        if (context.arguments().get(0).charAt(0) == '^') {
            // tbf i dont know any of what it does, its just mojangs own implementation of local positions.
            ArgumentVectorPosition vectorPosition;
            try {
                vectorPosition = ArgumentVectorPosition.a(new StringReader(context.argumentString()));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            double d;
            double c;
            double b;
            try {
                d = (double) dField.get(vectorPosition);
                c = (double) cField.get(vectorPosition);
                b = (double) bField.get(vectorPosition);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Vec2F var1 = new Vec2F(relative.getYaw(), relative.getPitch());
            Vec3D var2 = new Vec3D(relative.getX(), relative.getY(), relative.getZ());
            float var3 = MathHelper.b((var1.j + 90.0F) * 0.017453292F);
            float var4 = MathHelper.a((var1.j + 90.0F) * 0.017453292F);
            float var5 = MathHelper.b(-var1.i * 0.017453292F);
            float var6 = MathHelper.a(-var1.i * 0.017453292F);
            float var7 = MathHelper.b((-var1.i + 90.0F) * 0.017453292F);
            float var8 = MathHelper.a((-var1.i + 90.0F) * 0.017453292F);
            Vec3D var9 = new Vec3D(var3 * var5, var6, var4 * var5);
            Vec3D var10 = new Vec3D(var3 * var7, var8, var4 * var7);
            Vec3D var11 = var9.c(var10).a(-1.0);
            double var12 = var9.c * d + var10.c * c + var11.c * b;
            double var14 = var9.d * d + var10.d * c + var11.d * b;
            double var16 = var9.e * d + var10.e * c + var11.e * b;
            Vec3D result = new Vec3D(var2.c + var12, var2.d + var14, var2.e + var16);
            return new Location(
                    relative.getWorld(),
                    result.c,
                    result.d,
                    result.e,
                    relative.getYaw(),
                    relative.getPitch()
            );
        }
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
