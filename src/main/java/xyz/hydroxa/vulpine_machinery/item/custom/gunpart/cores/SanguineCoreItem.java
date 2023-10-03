package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

import java.util.Collection;
import java.util.List;

public class SanguineCoreItem extends DamagingCoreItem {
    public static final int MAX_AMPLIFIER = 16;
    public static final int MAX_DURATION = 1200;

    public SanguineCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core.sanguine"));
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
                int duration = Math.min(effect.getDuration() * 2, MAX_DURATION);
                int amplifier = Math.min(effect.getAmplifier() * 2, MAX_AMPLIFIER);
                victim.addEffect(new MobEffectInstance(effect.getEffect(), duration, amplifier));
            }
            shooter.removeAllEffects();
        }
    }
}
