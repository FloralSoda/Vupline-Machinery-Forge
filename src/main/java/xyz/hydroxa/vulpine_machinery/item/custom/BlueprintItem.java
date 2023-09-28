package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlueprintItem extends Item {
    public static final String TAG_PRINT_ID = "Print";

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
