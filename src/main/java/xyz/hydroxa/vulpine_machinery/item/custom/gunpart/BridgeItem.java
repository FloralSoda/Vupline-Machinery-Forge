package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.item.custom.DetailedItem;

import java.util.List;

public class BridgeItem extends DetailedItem {
    public BridgeProperties Properties;

    public BridgeItem(Properties pProperties, BridgeProperties properties) {
        super(pProperties);
        this.Properties = properties;
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bridge.reload", Properties.ReloadSpeedMultiplier));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bridge.recoil", Properties.BulletSpeedMultiplier));
    }
}
