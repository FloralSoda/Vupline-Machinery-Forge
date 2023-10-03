package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

import java.util.List;

public class WitherCoreItem extends DamagingCoreItem {
    public static final int BASE_DURATION = 200;
    public static final int BASE_AMPLIFIER = 3;

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core.wither", BASE_AMPLIFIER - 1, BASE_DURATION / 20));
    }

    public WitherCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);

        if (hitResult.getEntity() instanceof LivingEntity live) {
            live.addEffect(new MobEffectInstance(MobEffects.WITHER, BASE_DURATION, BASE_AMPLIFIER));
        }
    }
}
