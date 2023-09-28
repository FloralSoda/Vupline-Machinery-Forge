package xyz.hydroxa.vulpine_machinery.world;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ModDamageSource {
    public static DamageSource shot_with_gun(Entity pSource, @Nullable Entity pIndirectEntity) {
        return (new IndirectEntityDamageSource("shot_with_gun", pSource, pIndirectEntity)).setProjectile().bypassInvul().damageHelmet();
    }
}
