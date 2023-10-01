package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.item.custom.DetailedItem;

import java.util.List;

public class HandleItem extends DetailedItem {
    public HandleProperties Properties;
    public HandleItem(Properties pProperties, HandleProperties properties) {
        super(pProperties);
        Properties = properties;
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.handle.accuracy", Properties.AccuracyMultiplier));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.handle.recoil", Properties.RecoilMultiplier));
    }
}
