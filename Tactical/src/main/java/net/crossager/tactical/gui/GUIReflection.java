package net.crossager.tactical.gui;

import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.MinecraftClasses;

public class GUIReflection {
    public static final MethodInvoker<Integer> NEXT_CONTAINER_COUNTER = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getChatSerializerClass(), int.class);
}
