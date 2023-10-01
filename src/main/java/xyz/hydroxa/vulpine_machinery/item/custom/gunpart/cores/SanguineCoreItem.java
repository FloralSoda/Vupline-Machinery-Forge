package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

import java.util.Collection;

public class SanguineCoreItem extends DamagingCoreItem {
    public SanguineCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onCreation(Projectile entity, Entity owner) {
        super.onCreation(entity, owner);

        owner.hurt(ModDamageSource.SANGUINE_SHOT, 2);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);

        if (owner instanceof LivingEntity shooter && hitResult.getEntity() instanceof LivingEntity victim) {
            Collection<MobEffectInstance> effects = shooter.getActiveEffects();
            for (MobEffectInstance effect: effects) {
                victim.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() * 2));
            }
            shooter.removeAllEffects();
        }
    }
}
