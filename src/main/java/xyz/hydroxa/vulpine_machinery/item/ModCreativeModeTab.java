package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.enums.ItemOrder;
import xyz.hydroxa.vulpine_machinery.item.custom.IComparisonItem;

public class ModCreativeModeTab {
    public static final CreativeModeTab MACHINERY_TAB = new CreativeModeTab("vulpine_machinery_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.PEPPERBOX.get());
        }

        @Override
        public void fillItemList(@NotNull NonNullList<ItemStack> pItems) {
            NonNullList<ItemStack> my_items = NonNullList.create();

            for(Item item : Registry.ITEM) {
                item.fillItemCategory(this, my_items);
            }

            my_items.sort(ModCreativeModeTab::compare_items);
            pItems.addAll(my_items);
        }
    };

    public static int compare_items(ItemStack a, ItemStack b) {
        ItemOrder a_priority;
        if (a.getItem() instanceof IComparisonItem comparableItem) {
            a_priority = comparableItem.getPriority();
        } else {
            a_priority = ItemOrder.Item;
        }

        ItemOrder b_priority;
        if (b.getItem() instanceof IComparisonItem comparableItem) {
            b_priority = comparableItem.getPriority();
        } else {
            b_priority = ItemOrder.Item;
        }

        if (a_priority == b_priority) {
            return compare_names(a.getDisplayName().getString(), b.getDisplayName().getString());
        } else {
            return a_priority.compare(b_priority);
        }
    }
    public static int compare_names(String a, String b) {
        String longer = a.length() > b.length() ? a : b;
        String shorter = a.length() > b.length() ? b : a;

        if (longer.startsWith(shorter) || longer.endsWith(shorter)) {
            return shorter.equals(a) ? -1 : 1;
        } else {
            return a.compareTo(b);
        }
    }
}