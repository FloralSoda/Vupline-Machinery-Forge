package xyz.hydroxa.vulpine_machinery.item.custom;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

import java.util.List;

public abstract class DetailedItem extends Item {
    public DetailedItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced);

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        KeyMapping shift = Minecraft.getInstance().options.keyShift;
        InputConstants.Key key = shift.getKey();
        int keyCode = key.getValue();

        if (keyCode != InputConstants.UNKNOWN.getValue()) {
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            try {
                boolean isDown = false;
                if (key.getType() == InputConstants.Type.KEYSYM) {
                    isDown = InputConstants.isKeyDown(windowHandle, keyCode);
                } else if (key.getType() == InputConstants.Type.MOUSE){
                    isDown = GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                }
                if (isDown) {
                    getDetailedTooltip(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                    return;
                }
            } catch (Exception ex) {
                VulpineMachineryMod.LOGGER.error("Failed to handle key press for detailed item tooltip", ex);
            }
        }

        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.more", shift.getKey().getDisplayName()));
    }
}
