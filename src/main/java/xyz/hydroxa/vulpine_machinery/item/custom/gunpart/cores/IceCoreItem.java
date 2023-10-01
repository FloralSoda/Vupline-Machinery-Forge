package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import xyz.hydroxa.vulpine_machinery.effect.ModEffects;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;

public class IceCoreItem extends DamagingCoreItem {
    public static final int BASE_DURATION_FREEZE = 100;
    public static final int BASE_DURATION_SLOWNESS = 600;
    public static final int BASE_AMPLIFIER_SLOWNESS = 2;

    public IceCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);

        if (hitResult.getEntity() instanceof LivingEntity live) {
            live.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, BASE_DURATION_SLOWNESS, BASE_AMPLIFIER_SLOWNESS));
            live.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), BASE_DURATION_FREEZE, 0));
        }
    }
}
