package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.enums.ItemOrder;
import xyz.hydroxa.vulpine_machinery.item.custom.DetailedItem;

import java.util.List;

public class BarrelItem extends DetailedItem {
    public BarrelProperties Properties;
    public int ID;

    public BarrelItem(Properties pProperties, BarrelProperties bProperties) {
        super(pProperties);
        this.Properties = bProperties;
        this.ID = Math.abs(bProperties.BarrelName.hashCode() % 16);
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        StringBuilder builder = new StringBuilder();
        for (TagKey<Item> tag : pStack.getTags().toList()) {
            String name = tag.location().toString();
            if (name.endsWith("_barrels")) {
                String blueprint_id = "blueprint." + name.substring(name.indexOf('/') + 1, name.lastIndexOf('_'));
                builder.append(Component.translatable(blueprint_id).getString());
                builder.append(", ");
            }
        }
        String compatibilityList = builder.toString();
        if (compatibilityList.length() <= 2) {
            compatibilityList = "None";
        } else {
            compatibilityList = compatibilityList.substring(0, compatibilityList.length() - 2);
        }

        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.guns", compatibilityList));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.capacity", Properties.Capacity));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.shots", Properties.BulletsPerShot));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.reload", 20f / Properties.TicksPerBulletReloaded));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.rate", 20f / Properties.TicksPerShot));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.accuracy", Properties.Variance));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.damage", Properties.DamageMultiplier));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.speed", Properties.BulletSpeedMultiplier));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.recoil", Properties.BulletsPerShot));
    }
    public ItemOrder getPriority() {
        return ItemOrder.Barrels;
    }
}
