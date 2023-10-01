package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.world.item.ItemStack;

public interface IConditionalBobber {
    boolean shouldBob(ItemStack stack);
}
