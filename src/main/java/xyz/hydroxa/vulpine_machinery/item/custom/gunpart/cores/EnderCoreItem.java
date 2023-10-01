package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;

public class EnderCoreItem extends DamagingCoreItem {

    public EnderCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onBlockHit(Projectile entity, Entity owner, HitResult hitResult) {
        Vec3 location = hitResult.getLocation();
        owner.teleportTo(location.x, location.y, location.z);

        owner.level.playSound(null, location.x, location.y, location.z, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public void onTick(Projectile entity, Entity owner) {
        super.onTick(entity, owner);
        double d0 = entity.getX() + 0.5D;
        double d1 = entity.getY() + 0.7D;
        double d2 = entity.getZ() + 0.5D;
        entity.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(0xcc0099)), 1.0F), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);
        onBlockHit(entity, owner, hitResult);
    }
}
