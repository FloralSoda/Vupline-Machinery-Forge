package xyz.hydroxa.vulpine_machinery.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_VULPINE_MACHINERY = "key.category.vulpine_machinery.vulpine_machinery";
    public static final String KEY_RELOAD_WEAPON = "key.vulpine_machinery.reload";

    public static final KeyMapping RELOAD_WEAPON_KEY = new KeyMapping(KEY_RELOAD_WEAPON, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_VULPINE_MACHINERY);
}
