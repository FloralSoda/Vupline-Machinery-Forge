package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

public class BarrelItem extends Item {
    public BarrelProperties Properties;

    public BarrelItem(Properties pProperties, BarrelProperties bProperties) {
        super(pProperties);
        this.Properties = bProperties;
    }

    public Pair<Float, Float> getFireAngle(Level level, LivingEntity user, ItemStack stack) {
        float randomX = level.random.nextFloat() * Properties.Variance;
        float randomY = level.random.nextFloat() * Properties.Variance;

        return new Pair<>(randomX, randomY);
    }
}
