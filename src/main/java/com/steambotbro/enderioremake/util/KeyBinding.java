package com.steambotbro.enderioremake.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding
{
    public static final String KEY_CATEGORY = "key.category.enderioremake.keybinds";
    public static final String KEY_SPAWN_COW = "key.enderioremake.spawn_cow";

    public static final KeyMapping SPAWN_COW_KEY = new KeyMapping(KEY_SPAWN_COW, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY);


}
