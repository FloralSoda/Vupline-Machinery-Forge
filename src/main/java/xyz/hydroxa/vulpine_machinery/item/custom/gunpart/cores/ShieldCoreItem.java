package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreItem;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

public class ShieldCoreItem extends CoreItem {
    public static final int BASE_DURATION = 36000;
    public static final int BASE_AMPLIFIER = 2;

    public ShieldCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onTick(Projectile entity, Entity owner) {
        super.onTick(entity, owner);

        double d0 = entity.getX() + 0.5D;
        double d1 = entity.getY() + 0.7D;
        double d2 = entity.getZ() + 0.5D;
        entity.level.addParticle(ParticleTypes.DRIPPING_HONEY, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);
        if (hitResult.getEntity() instanceof LivingEntity le) {
            le.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, BASE_DURATION, BASE_AMPLIFIER));
        } else {
            hitResult.getEntity().hurt(ModDamageSource.shot_with_gun(entity, owner), baseDamage);
        }
    }
}
