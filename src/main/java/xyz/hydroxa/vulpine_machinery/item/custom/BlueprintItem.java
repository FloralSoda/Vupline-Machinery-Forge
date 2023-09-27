package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.item.ModItems;

public class BlueprintItem extends Item {
    private static final String TAG_PRINT_ID = "Print";

    public BlueprintItem(Properties pProperties) {
        super(pProperties);
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
            CompoundTag print = tags.getCompound(TAG_PRINT_ID);
            ItemStack target = ItemStack.of(print);
            if (target.getItem() instanceof WeaponItem) {
                return Component.translatable(this.getDescriptionId(), Component.translatable(target.getDescriptionId()));
            }
        }

        return super.getName(pStack);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack def = new ItemStack(this);
        CompoundTag tag = def.getOrCreateTag();
        CompoundTag print = new CompoundTag();
        ModItems.PEPPERBOX.get().getDefaultInstance().save(print);

        tag.put(TAG_PRINT_ID, print);
        return def;
    }
}
