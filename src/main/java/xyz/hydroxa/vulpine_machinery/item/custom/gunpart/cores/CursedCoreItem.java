package xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.client.ClientComboData;
import xyz.hydroxa.vulpine_machinery.entity.capabilities.EntityCombo;
import xyz.hydroxa.vulpine_machinery.entity.capabilities.EntityComboProvider;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreProperties;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.ComboSyncS2CPacket;
import xyz.hydroxa.vulpine_machinery.world.ModDamageSource;

import java.util.List;

public class CursedCoreItem extends DamagingCoreItem {
    public CursedCoreItem(Item.Properties pProperties, CoreProperties properties) {
        super(pProperties, properties);
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (pLevel.isClientSide()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(EntityComboProvider.ENTITY_COMBO).ifPresent(entityCombo ->
                        pTooltipComponents.add(
                                Component.translatable(
                                        "tooltip.vulpine_machinery.core.cursed.damage",
                                        EntityCombo.BASE_COMBO_MULTIPLIER + (EntityCombo.COMBO_MULTIPLIER * ClientComboData.combo),
                                        ClientComboData.combo)));
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (pLevel.isClientSide()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(EntityComboProvider.ENTITY_COMBO).ifPresent(entityCombo ->
                        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core.cursed")));
            }
        }
    }
    @Override
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {
        super.onEntityHit(entity, owner, hitResult, baseDamage);

        if (hitResult.getEntity() instanceof LivingEntity live) {
            owner.getCapability(EntityComboProvider.ENTITY_COMBO).ifPresent(entityCombo -> {
                live.hurt(ModDamageSource.shot_with_gun(entity, owner), entityCombo.getComboMult() * baseDamage);
                entityCombo.setHasHitEntity(true);
                if (live.isDeadOrDying()) {
                    entityCombo.addCombo(1);
                    if (owner instanceof ServerPlayer player) {
                        ModMessages.sendToPlayer(new ComboSyncS2CPacket(entityCombo.getCombo()), player);
                    }
                }
            });
        }
    }

    public void onBlockHit(Projectile entity, Entity owner, HitResult hitResult) {
        owner.getCapability(EntityComboProvider.ENTITY_COMBO).ifPresent(entityCombo -> {
            if (!entityCombo.hasHitEntity()) {
                entityCombo.resetCombo();
                if (owner instanceof ServerPlayer player) {
                    ModMessages.sendToPlayer(new ComboSyncS2CPacket(entityCombo.getCombo()), player);
                }
            }
            entityCombo.setHasHitEntity(false);
        });
    }

    @Override
    public void onTick(Projectile entity, Entity owner) {
        super.onTick(entity, owner);
        double d0 = entity.getX() + 0.5D;
        double d1 = entity.getY() + 0.7D;
        double d2 = entity.getZ() + 0.5D;
        entity.level.addParticle(ParticleTypes.ASH, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}