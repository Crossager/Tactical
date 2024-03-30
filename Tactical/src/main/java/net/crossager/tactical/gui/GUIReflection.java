package net.crossager.tactical.gui;

import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.MinecraftClasses;

public class GUIReflection {
//    public static final Gson CHAT_MESSAGE_GSON = DynamicReflection.getField(MinecraftClasses.getChatSerializerClass(), Gson.class).read(null);
    public static final MethodInvoker<Integer> NEXT_CONTAINER_COUNTER = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getChatSerializerClass(), int.class);
}
