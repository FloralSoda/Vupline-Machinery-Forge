package xyz.hydroxa.vulpine_machinery.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

import java.awt.*;

public class AmmoHudOverlay {
    private static final int CENTER_OFFSET_X = 8;
    private static final int CENTER_OFFSET_Y = -2;
    private static final int SEGMENTS = 12;
    private static final Color LOW_AMMO_COLOR = Color.getHSBColor(0, 1.0f, 0.8f);
    private static final Color HIGH_AMMO_COLOR = Color.getHSBColor(0, 0.0f, 0.8f);


    private static final ResourceLocation FILLED = new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/gui/hud/ammobar_filled.png");
    private static final ResourceLocation EMPTY = new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/gui/hud/ammobar_empty.png");

    private static float floatify(int value, float upper) {
        return value / upper;
    }
    private static Color gradientColor(float percentage, Color from, Color to) {
        return new Color((floatify(from.getRed(), 255) * percentage) + (floatify(to.getRed(),255) * (1 - percentage)), (floatify(from.getGreen(),255) * percentage) + (floatify(to.getGreen(),255) * (1 - percentage)), (floatify(from.getBlue(),255) * percentage) + (floatify(to.getBlue(),255) * (1 - percentage)));
    }

    public static final IGuiOverlay HUD_AMMO = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if (ClientAmmoData.isVisible()) {
            int x = screenWidth / 2 + CENTER_OFFSET_X;
            int y = screenHeight / 2 + CENTER_OFFSET_Y;

            float fillPercentage = ((float) ClientAmmoData.getAmmoLevel() / ClientAmmoData.getCapacity());
            Color interp = gradientColor(fillPercentage, HIGH_AMMO_COLOR, LOW_AMMO_COLOR);
            float red = interp.getRed() / 255f;
            float green = interp.getGreen() / 255f;
            float blue = interp.getBlue() / 255f;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(red, green, blue, 1.0f);
            RenderSystem.setShaderTexture(0, FILLED);

            int filledSegments = (int)(SEGMENTS * fillPercentage);
            for (int i = 0; i < SEGMENTS; ++i) {
                if (i == filledSegments)
                    RenderSystem.setShaderTexture(0, EMPTY);
                GuiComponent.blit(poseStack, x, (y + (SEGMENTS / 2)) - i, 0, 0, 2, 1, 1, 2);
            }
        }
    });
}
