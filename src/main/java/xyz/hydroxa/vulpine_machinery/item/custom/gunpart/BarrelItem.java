package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BarrelItem extends Item {
    public BarrelProperties Properties;
    public int ID;

    public BarrelItem(Properties pProperties, int id, BarrelProperties bProperties) {
        super(pProperties);
        this.Properties = bProperties;
        this.ID = id;
    }

    public float getFireAngle(Level level, LivingEntity user, ItemStack stack) {

        return level.random.nextFloat() * Properties.Variance;
    }
}
