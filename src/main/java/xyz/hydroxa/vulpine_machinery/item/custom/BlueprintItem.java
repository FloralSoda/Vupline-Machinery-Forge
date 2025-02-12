package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueprintItem extends DetailedItem {
    public static final String TAG_PRINT_ID = "Print";

    public BlueprintItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        //TODO
        if (pLevel != null) {
            CompoundTag tags = pStack.getOrCreateTag();
            String print = tags.getString(TAG_PRINT_ID);
            var recipe = pLevel.getRecipeManager().byKey(new ResourceLocation(print));
            if (recipe.isPresent() && recipe.get().getResultItem().getItem() instanceof WeaponItem weaponItem) {
                if (weaponItem.Properties.Automatic)
                    pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.automatic"));
                if (!weaponItem.Properties.CanHipFire)
                    pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.nohipfire"));

                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.aimspeed", Math.round((weaponItem.Properties.AimCarryModifierAttribute.getAmount() + 1) * 100) / 100f));
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.hipspeed", Math.round((weaponItem.Properties.HipCarryModifierAttribute.getAmount() + 1) * 100) / 100f));
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.blueprint.recoil", Math.round((weaponItem.Properties.RecoilInDegrees) * 100) / 100f));
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.blueprint.bulletspeed", Math.round((weaponItem.Properties.BulletSpeed) * 100) / 100f));
            }
        }
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab pCategory, @NotNull NonNullList<ItemStack> pItems) {
        if (allowedIn(pCategory)) {
            pItems.add(getDefaultInstance());
        } else {
            super.fillItemCategory(pCategory, pItems);
        }
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        if (pStack.getItem() instanceof BlueprintItem) {
            CompoundTag tags = pStack.getOrCreateTag();
            String print = tags.getString(TAG_PRINT_ID);
            String description = "blueprint." + print.substring(print.indexOf("/") + 1);
            return Component.translatable(this.getDescriptionId(), Component.translatable(description));
        }

        return super.getName(pStack);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack def = new ItemStack(this);
        CompoundTag tag = def.getOrCreateTag();

        tag.putString(TAG_PRINT_ID, "vulpine_machinery:weaponry/pepperbox");
        return def;
    }
}
