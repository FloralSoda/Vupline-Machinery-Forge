package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.world.item.Item;
import xyz.hydroxa.vulpine_machinery.item.ModItems;

public class CoreProperties {
    /**
     Whether the core should operate on all bullets, or just one
     */
    public boolean IsSingular;
    public Item BulletItem;

    public CoreProperties() {
        IsSingular = false;
        BulletItem = ModItems.BULLET.get();
    }

    public CoreProperties isSingular(boolean value) {
        IsSingular = value;
        return this;
    }
    public CoreProperties bulletItem(Item item) {
        BulletItem = item;
        return this;
    }
}
