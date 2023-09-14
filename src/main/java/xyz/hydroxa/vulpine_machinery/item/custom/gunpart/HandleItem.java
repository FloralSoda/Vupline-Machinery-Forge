package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.world.item.Item;

public class HandleItem extends Item {
    public HandleProperties Properties;
    public HandleItem(Properties pProperties, HandleProperties properties) {
        super(pProperties);
        Properties = properties;
    }
}
