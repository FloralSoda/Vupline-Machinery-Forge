package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.item.custom.DetailedItem;

import java.util.List;

public abstract class CoreItem extends DetailedItem {
    public CoreProperties Properties;

    public CoreItem(Properties pProperties, CoreProperties properties) {
        super(pProperties);
        this.Properties = properties;
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel.damage", Properties.DamageMultiplier));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bullet_requirement", Properties.BulletItem.getDefaultInstance().getDisplayName().getString().replaceAll("[\\[\\]]", "").trim()));
        pTooltipComponents.add(Component.literal(""));
    }

    public void onTick(Projectile entity, Entity owner) {}
    public void onCreation(Projectile entity, Entity owner) {}
    public void onEntityHit(Projectile entity, Entity owner, EntityHitResult hitResult, float baseDamage) {}

    public void onBlockHit(Projectile entity, Entity owner, HitResult hitResult) {}
}
