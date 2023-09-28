package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelItem extends Item {
    public BarrelProperties Properties;
    public int ID;

    public BarrelItem(Properties pProperties, int id, BarrelProperties bProperties) {
        super(pProperties);
        this.Properties = bProperties;
        this.ID = id;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        KeyMapping shift = Minecraft.getInstance().options.keyShift;
        if (shift.isDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.capacity", Properties.Capacity));
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.shots", Properties.BulletsPerShot));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.more", shift.getKey().getDisplayName()));
        }
    }
}
