package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RenderHelperItem extends Item {
    public RenderHelperItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        pEntity.sendSystemMessage(Component.translatable("message.vulpine_machinery.render_helper_obtained"));
        if (!pLevel.isClientSide) {
            if (pEntity instanceof Player player) {
                var inventory = player.getInventory();
                inventory.clearOrCountMatchingItems((itemStack -> itemStack.is(pStack.getItem())), 0, inventory);
            } else {
                pStack.setCount(0);
            }
        }
    }
}
