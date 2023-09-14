package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.world.item.Item;

public class BridgeItem extends Item {
    public BridgeProperties Properties;

    public BridgeItem(Properties pProperties, BridgeProperties properties) {
        super(pProperties);
        this.Properties = properties;
    }
}
