package xyz.hydroxa.vulpine_machinery.item.custom;

import com.mojang.math.Vector3f;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.audio.ModSoundEvents;
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletProjectile;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.*;

import java.util.List;

public class WeaponItem extends Item implements Vanishable {
    private static final String TAG_PARTS = "Parts";
    private static final String TAG_PARTS_BARREL = "Barrel";
    private static final String TAG_PARTS_CORE = "Core";
    private static final String TAG_PARTS_BRIDGE = "Bridge";
    private static final String TAG_PARTS_HANDLE = "Handle";
    private static final String TAG_BULLETS = "Bullets";
    private static final String TAG_LAST_RELOAD = "LastReload";
    private static final String TAG_BARREL_VARIANT = "BarrelVariant";

    public static final float BASE_DAMAGE = 15f;
    public WeaponProperties Properties;
    public WeaponItem(Properties pProperties, WeaponProperties properties) {
        super(pProperties);
        Properties = properties;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab pCategory, @NotNull NonNullList<ItemStack> pItems) {
        if (allowedIn(pCategory)) {
            pItems.add(getDefaultInstance());
        } else {
            super.fillItemCategory(pCategory, pItems);
        }
    }

    public BarrelItem getBarrel(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag barrel = tag.getCompound(TAG_PARTS).getCompound(TAG_PARTS_BARREL);
        Item barrelItem = ItemStack.of(barrel).getItem();
        if (barrelItem instanceof BarrelItem bi)
            return bi;
        else
            return null;
    }
    public CoreItem getCore(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag core = tag.getCompound(TAG_PARTS).getCompound(TAG_PARTS_CORE);
        Item coreItem = ItemStack.of(core).getItem();
        if (coreItem instanceof CoreItem ci)
            return ci;
        else
            return null;
    }
    public HandleItem getHandle(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag handle = tag.getCompound(TAG_PARTS).getCompound(TAG_PARTS_HANDLE);
        Item handleItem = ItemStack.of(handle).getItem();
        if (handleItem instanceof HandleItem hi)
            return hi;
        else
            return null;
    }
    public BridgeItem getBridge(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag bridge = tag.getCompound(TAG_PARTS).getCompound(TAG_PARTS_BRIDGE);
        Item bridgeItem = ItemStack.of(bridge).getItem();
        if (bridgeItem instanceof BridgeItem bi)
            return bi;
        else
            return null;
    }

    public int consumeBullets(ItemStack item, int amount) {
        int remaining = getRemainingBullets(item);
        if (amount > remaining)
            amount = remaining;
        item.getOrCreateTag().putInt(TAG_BULLETS, remaining - amount);
        return remaining - amount;
    }
    public int getRemainingBullets(ItemStack item) {
        return item.getOrCreateTag().getInt(TAG_BULLETS);
    }

    public int getBulletCapacity(ItemStack item) {
        return getBarrel(item).Properties.Capacity;
    }

    public Item getBulletItem(ItemStack item) {
        return getCore(item).Properties.BulletItem;
    }
    public long getTimeSinceLastReload(ItemStack item) {
        return item.getOrCreateTag().getLong(TAG_LAST_RELOAD);
    }
    public boolean canReload(LivingEntity user, ItemStack item) {
        if (user instanceof Player player) {
            if (player.getAbilities().instabuild)
                return true;
            else {
                Item bulletItem = getBulletItem(item);
                return player.getInventory().contains(new ItemStack(bulletItem));
            }
        } else
            return true;
    }

    /**
    Returns true if compatible bullets were found. Returns false if there weren't any bullets
     */
    public boolean reload(LivingEntity user, ItemStack item) {
        int remainingBullets = getRemainingBullets(item);
        CompoundTag tags = item.getOrCreateTag();
        if (user.getLevel().getGameTime() - tags.getLong(TAG_LAST_RELOAD) >= getReloadInterval(item) && remainingBullets < getBulletCapacity(item)) {
            int bulletLocation = -1;

            if (user instanceof Player player) {
                if (!player.getAbilities().instabuild) {
                    Item bulletItem = getBulletItem(item);
                    var inventory = player.getInventory().items;
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).is(bulletItem)) {
                            bulletLocation = i;
                            break;
                        }
                    }
                    if (bulletLocation == -1) {
                        return false;
                    }

                    inventory.get(bulletLocation).shrink(1);
                }
            }

            tags.putInt(TAG_BULLETS, remainingBullets + 1);
            tags.putLong(TAG_LAST_RELOAD, user.getLevel().getGameTime());
            return true;
        }
        return false;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.isUsingItem() && itemstack.getItem() instanceof WeaponItem) {
            if (!pLevel.isClientSide) {
                if (getRemainingBullets(itemstack) > 0 && pPlayer.getLevel().getGameTime() - getTimeSinceLastReload(itemstack) >= getReloadInterval(itemstack)) {
                    BarrelItem barrel = getBarrel(itemstack);
                    shootProjectile(pLevel, pPlayer, itemstack, barrel, getCore(itemstack), getBridge(itemstack), getHandle(itemstack), pUsedHand);
                    consumeBullets(itemstack, barrel.Properties.BulletsPerShot);
                } else {
                    if (canReload(pPlayer, itemstack)) {
                        pPlayer.startUsingItem(pUsedHand);
                    } else {
                        pLevel.playSound(null, pPlayer.blockPosition(), getBarrel(itemstack).Properties.SoundProvider.GetEmptyFireAudio(pPlayer, pLevel, itemstack), SoundSource.PLAYERS,1.0f, 1.0f);
                    }
                }
            }
            return InteractionResultHolder.consume(itemstack);
        } else
            return InteractionResultHolder.pass(itemstack);
    }

    public int getReloadInterval(ItemStack item) {
        BarrelItem barrel = getBarrel(item);
        BridgeItem bridge = getBridge(item);
        return (int)((float)barrel.Properties.TicksPerBulletReloaded / bridge.Properties.ReloadSpeedMultiplier);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        BarrelItem barrel = getBarrel(stack);
        if (count % getReloadInterval(stack) == 0 && getRemainingBullets(stack) < barrel.Properties.Capacity) {
            if (reload(player, stack))
                player.getLevel().playSound(null, player.blockPosition(), barrel.Properties.SoundProvider.GetReloadAudio(player, player.level, stack), SoundSource.PLAYERS,1.0f, 1.0f);
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide && pEntity instanceof LivingEntity lEntity) {
            var attribute = lEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                if (pIsSelected) {
                    if (lEntity.isCrouching() && !attribute.hasModifier(Properties.AimCarryModifier)) {
                        if (attribute.hasModifier(Properties.HipCarryModifier)) {
                            attribute.removeModifier(Properties.HipCarryModifier);
                        }
                        attribute.addTransientModifier(Properties.AimCarryModifier);
                    } else if (!lEntity.isCrouching() && !attribute.hasModifier(Properties.HipCarryModifier)) {
                        if (attribute.hasModifier(Properties.AimCarryModifier)) {
                            attribute.removeModifier(Properties.AimCarryModifier);
                        }
                        attribute.addTransientModifier(Properties.HipCarryModifier);
                    }
                } else {
                    if (attribute.hasModifier(Properties.HipCarryModifier))
                        attribute.removeModifier(Properties.HipCarryModifier);
                    if (attribute.hasModifier(Properties.AimCarryModifier))
                        attribute.removeModifier(Properties.AimCarryModifier);
                }
            }
        }
    }

    private void shootProjectile(Level level, LivingEntity user, ItemStack item, BarrelItem barrel, CoreItem core, BridgeItem bridge, HandleItem handle, InteractionHand hand) {
        if (!level.isClientSide) {
            BulletProjectile projectile = new BulletProjectile(level, user, barrel.Properties.BulletType);
            projectile.setCore(new ItemStack(core));
            projectile.setDamage(BASE_DAMAGE * barrel.Properties.DamageMultiplier);

            if (barrel.Properties.BulletType == BulletType.HandCannon) {
                projectile.setPierceLevel((byte) 5);
            }

            Vec3 lookVector = user.getViewVector(1.0F);

            Vec3 bulletOffset;
            if (user.isCrouching()) {
                bulletOffset = new Vec3(0,1,0);
            } else {
                bulletOffset = lookVector.cross(new Vec3(0,1,0)).multiply(0.5,1,0.5);
                if ((user.getMainArm() == HumanoidArm.LEFT && hand == InteractionHand.MAIN_HAND) || (user.getMainArm() == HumanoidArm.RIGHT && hand == InteractionHand.OFF_HAND)) {
                    bulletOffset = bulletOffset.reverse();
                }
                bulletOffset = bulletOffset.add(0,1.4,0);
            }
            projectile.setPos(user.getPosition(1.0f).add(bulletOffset));

            Vector3f lookVectorF = new Vector3f(lookVector);
            for (int i = 0; i < Math.min(barrel.Properties.BulletsPerShot, getRemainingBullets(item)); i++) {
                projectile.shoot(lookVectorF.x(), lookVectorF.y(), lookVectorF.z(), Properties.BulletSpeed * barrel.Properties.BulletSpeedMultiplier * bridge.Properties.BulletSpeedMultiplier, barrel.Properties.Variance / handle.Properties.AccuracyMultiplier);
            }

            level.addFreshEntity(projectile);
            level.playSound(null, user.blockPosition(), ModSoundEvents.GUNSHOT_NEAR.get(), SoundSource.PLAYERS, 1.0F, 1.0f);
        } else {
            user.playSound(barrel.Properties.SoundProvider.GetGunshotNearAudio(user, level, item), 1.0F, 1.0f);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (pStack.getItem() instanceof WeaponItem) {
            CompoundTag tags = pStack.getOrCreateTag();
            CompoundTag parts = tags.getCompound(TAG_PARTS);
            ItemStack barrel = ItemStack.of(parts.getCompound(TAG_PARTS_BARREL));
            ItemStack core = ItemStack.of(parts.getCompound(TAG_PARTS_CORE));
            ItemStack bridge = ItemStack.of(parts.getCompound(TAG_PARTS_BRIDGE));
            ItemStack handle = ItemStack.of(parts.getCompound(TAG_PARTS_HANDLE));
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel", barrel.getDisplayName()));
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core", core.getDisplayName()));
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bridge", bridge.getDisplayName()));
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.handle", handle.getDisplayName()));
            if (core.getItem() instanceof CoreItem coreItem) {
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bullet_requirement", coreItem.Properties.BulletItem.getDefaultInstance().getDisplayName().getString().replaceAll("[\\[\\]]", "")));
            }
        }
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        if (pStack.getItem() instanceof WeaponItem) {
            CompoundTag tags = pStack.getOrCreateTag();
            CompoundTag parts = tags.getCompound(TAG_PARTS);
            ItemStack barrel = ItemStack.of(parts.getCompound(TAG_PARTS_BARREL));
            if (barrel.getItem() instanceof BarrelItem bi) {
                return Component.translatable(this.getDescriptionId(), bi.Properties.BarrelName);
            }
        }

        return super.getName(pStack);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack def = new ItemStack(this);
        CompoundTag tag = def.getOrCreateTag();
        CompoundTag parts = new CompoundTag();

        CompoundTag barrel = new CompoundTag();
        new ItemStack(Properties.DefaultBarrel).save(barrel);

        CompoundTag core = new CompoundTag();
        new ItemStack(Properties.DefaultCore).save(core);

        CompoundTag bridge = new CompoundTag();
        new ItemStack(Properties.DefaultBridge).save(bridge);

        CompoundTag handle = new CompoundTag();
        new ItemStack(Properties.DefaultHandle).save(handle);

        parts.put(TAG_PARTS_BARREL, barrel);
        parts.put(TAG_PARTS_CORE, core);
        parts.put(TAG_PARTS_BRIDGE, bridge);
        parts.put(TAG_PARTS_HANDLE, handle);

        tag.put(TAG_PARTS, parts);

        tag.putLong(TAG_LAST_RELOAD, 0);
        tag.putInt(TAG_BULLETS, 0);
        if (Properties.DefaultBarrel instanceof BarrelItem bi)
            tag.putInt(TAG_BARREL_VARIANT, bi.ID);
        return def;
    }
}
