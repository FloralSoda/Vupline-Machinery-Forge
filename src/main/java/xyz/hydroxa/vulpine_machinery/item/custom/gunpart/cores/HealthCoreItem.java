package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreItem;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

import java.util.List;

public class HealthCoreItem extends CoreItem {
    public HealthCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onTick(Projectile entity, Entity owner) {
        super.onTick(entity, owner);

        double d0 = entity.getX() + 0.5D;
        double d1 = entity.getY() + 0.7D;
        double d2 = entity.getZ() + 0.5D;
        entity.level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core.health"));
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);
        if (hitResult.getEntity() instanceof LivingEntity le) {
            le.heal(baseDamage);
        } else {
            hitResult.getEntity().hurt(ModDamageSource.shot_with_gun(entity, owner), baseDamage);
        }
    }
}
