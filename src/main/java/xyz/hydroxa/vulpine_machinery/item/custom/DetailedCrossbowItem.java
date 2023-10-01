package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DetailedCrossbowItem extends CrossbowItem implements IDetailedItem {
    public DetailedCrossbowItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced);

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        DetailedItem.hoverTextAppender(this, pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
