package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreItem;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

public class StandardCoreItem extends CoreItem {
    public StandardCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        Entity victim = hitResult.getEntity();
        victim.hurt(ModDamageSource.shot_with_gun(entity, owner), baseDamage);
    }
}
