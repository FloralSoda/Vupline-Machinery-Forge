package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.world.item.Item;
import xyz.hydroxa.vulpine_machinery.enums.ItemOrder;

public class ComparableItem extends Item implements IComparisonItem {
    ItemOrder priority;
    public ComparableItem(Properties pProperties, ItemOrder priority) {
        super(pProperties);
        this.priority = priority;
    }

    public ItemOrder getPriority() {
        return priority;
    }
}
