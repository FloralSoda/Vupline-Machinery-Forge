package xyz.hydroxa.vulpine_machinery.item.custom;

import com.mojang.math.Vector3f;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;
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
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletProjectile;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.*;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.AmmoSyncS2CPacket;

import java.util.List;

public class WeaponItem extends DetailedCrossbowItem implements Vanishable {
    public static final String TAG_PARTS = "Parts";
    public static final String TAG_PARTS_BARREL = "Barrel";
    public static final String TAG_PARTS_CORE = "Core";
    public static final String TAG_PARTS_BRIDGE = "Bridge";
    public static final String TAG_PARTS_HANDLE = "Handle";
    public static final String TAG_BULLETS = "Bullets";
    public static final String TAG_LAST_RELOAD = "LastReload";
    public static final String TAG_LAST_SHOT = "LastShot";
    public static final String TAG_BARREL_VARIANT = "CustomModelData";
    public static final String TAG_RELOADING = "IsReloading";

    public static final float BASE_DAMAGE = 15f;
    public final WeaponProperties Properties;
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

    public void changeAmmoLevel(LivingEntity user, ItemStack item, int amount) {
        CompoundTag tags = item.getOrCreateTag();
        int toSet = tags.getInt(TAG_BULLETS) + amount;
        int capacity = getBulletCapacity(item);
        if (toSet < 0)
            toSet = 0;
        else if (toSet > capacity)
            toSet = capacity;

        tags.putInt(TAG_BULLETS, toSet);

        if (user instanceof ServerPlayer player)
            ModMessages.sendToPlayer(new AmmoSyncS2CPacket(toSet, capacity), player);
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

            changeAmmoLevel(user, item, 1);
            tags.putLong(TAG_LAST_RELOAD, user.getLevel().getGameTime());
            return true;
        }
        return false;
    }
    public boolean getReloading(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(TAG_RELOADING);
    }
    public void setReloading(ItemStack stack, boolean state) {
        stack.getOrCreateTag().putBoolean(TAG_RELOADING, state);
    }
    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    public boolean isInvalid(ItemStack weaponItem) {
        return getBarrel(weaponItem) == null || getCore(weaponItem) == null || getBridge(weaponItem) == null || getHandle(weaponItem) == null;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (isInvalid(itemstack)) {
            if (pLevel.isClientSide)
                pPlayer.sendSystemMessage(getBadGunObtainedText());
            return InteractionResultHolder.pass(itemstack);
        }
        if (!pPlayer.isUsingItem() && itemstack.getItem() instanceof WeaponItem) {
            if (getRemainingBullets(itemstack) > 0 &&
                    pPlayer.getLevel().getGameTime() - getTimeSinceLastReload(itemstack) >= getReloadInterval(itemstack)) {
                if (pLevel.getGameTime() - getFireCooldown(itemstack) >= getBarrel(itemstack).Properties.TicksPerShot) {
                    if (Properties.Automatic) {
                        setReloading(itemstack, false);
                        pPlayer.startUsingItem(pUsedHand);
                    } else {
                        setReloading(itemstack, false);
                        BarrelItem barrel = getBarrel(itemstack);
                        shootProjectile(pLevel, pPlayer, itemstack, barrel, getCore(itemstack), getBridge(itemstack), getHandle(itemstack), pUsedHand);
                    }
                }
            } else if (canReload(pPlayer, itemstack)) {
                setReloading(itemstack, true);
                pPlayer.startUsingItem(pUsedHand);
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

    public MutableComponent getBadGunObtainedText() {
        String url = "https://github.com/FloralSoda/Vulpine-Machinery-Forge/issues";

        return Component.translatable("message.vulpine_machinery.invalid_gun_obtained").withStyle(Style.EMPTY
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(url)))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                .withColor(TextColor.fromRgb(0x8888FF))
                .withUnderlined(true));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        if (isInvalid(stack)) {
            if (player.level.isClientSide)
                player.sendSystemMessage(getBadGunObtainedText());
            player.stopUsingItem();
            return;
        }

        BarrelItem barrel = getBarrel(stack);
        if (getReloading(stack)) {
            if (count % getReloadInterval(stack) == 0 && getRemainingBullets(stack) < barrel.Properties.Capacity) {
                if (reload(player, stack))
                    player.getLevel().playSound(null, player.blockPosition(), barrel.Properties.SoundProvider.GetReloadAudio(player, player.level, stack), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        } else if (count % barrel.Properties.TicksPerShot == 0) {
            shootProjectile(player.level, player, stack, barrel, getCore(stack), getBridge(stack), getHandle(stack), player.getUsedItemHand());
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (getReloading(pStack))
            setReloading(pStack, false);

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public void setAiming(LivingEntity user, ItemStack stack, boolean state) {
        if (isAiming(stack) != state) {
            setCharged(stack, state);
            var attribute = user.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                if (state) {
                    if (attribute.hasModifier(Properties.HipCarryModifierAttribute))
                        attribute.removeModifier(Properties.HipCarryModifierAttribute);

                    if (!attribute.hasModifier(Properties.AimCarryModifierAttribute))
                        attribute.addTransientModifier(Properties.AimCarryModifierAttribute);
                } else {
                    if (attribute.hasModifier(Properties.AimCarryModifierAttribute))
                        attribute.removeModifier(Properties.AimCarryModifierAttribute);

                    if (!attribute.hasModifier(Properties.HipCarryModifierAttribute))
                        attribute.addTransientModifier(Properties.HipCarryModifierAttribute);
                }
            }
        }
    }
    public boolean isAiming(ItemStack stack) {
        return isCharged(stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide) {
            if (pEntity instanceof LivingEntity lEntity) {
                if (pIsSelected) {
                    setAiming(lEntity, pStack, lEntity.isCrouching());
                } else {
                    setAiming(lEntity, pStack, false);
                }
            }
        }
    }
    private long getFireCooldown(ItemStack item) {
        return item.getOrCreateTag().getLong(TAG_LAST_SHOT);
    }
    private void setFireCooldown(ItemStack item, Level level) {
        item.getOrCreateTag().putLong(TAG_LAST_SHOT, level.getGameTime());
    }

    private float getBulletSpeed(BarrelItem barrel, BridgeItem bridge) {
        return Properties.BulletSpeed * barrel.Properties.BulletSpeedMultiplier * bridge.Properties.BulletSpeedMultiplier;
    }
    private float getRecoil(BarrelItem barrel, HandleItem handle) {
        return Properties.RecoilInDegrees * barrel.Properties.RecoilMultiplier * handle.Properties.RecoilMultiplier;
    }

    private float getVariance(BarrelItem barrel, HandleItem handle) {
        return barrel.Properties.Variance / handle.Properties.AccuracyMultiplier;
    }

    private float getBaseDamage(BarrelItem barrel, CoreItem core) {
        return BASE_DAMAGE * barrel.Properties.DamageMultiplier * core.Properties.DamageMultiplier;
    }

    private void shootProjectile(Level level, LivingEntity user, ItemStack item, BarrelItem barrel, CoreItem core, BridgeItem bridge, HandleItem handle, InteractionHand hand) {
        if (!level.isClientSide) {
            for (int i = 0; i < Math.min(barrel.Properties.BulletsPerShot, getRemainingBullets(item)); i++) {
                BulletProjectile projectile = new BulletProjectile(level, user, barrel.Properties.BulletType);
                projectile.setCore(new ItemStack(core));
                projectile.setDamage(getBaseDamage(barrel, core));

                if (barrel.Properties.BulletType == BulletType.HandCannon) {
                    projectile.setPierceLevel((byte) 5);
                }

                Vec3 lookVector = user.getViewVector(1.0F);

                Vec3 bulletOffset;
                if (user.isCrouching()) {
                    bulletOffset = new Vec3(0, 1, 0);
                } else {
                    bulletOffset = lookVector.cross(new Vec3(0, 1, 0)).multiply(0.5, 1, 0.5);
                    if ((user.getMainArm() == HumanoidArm.LEFT && hand == InteractionHand.MAIN_HAND) || (user.getMainArm() == HumanoidArm.RIGHT && hand == InteractionHand.OFF_HAND)) {
                        bulletOffset = bulletOffset.reverse();
                    }
                    bulletOffset = bulletOffset.add(0, 1.4, 0);
                }
                projectile.setPos(user.getPosition(1.0f).add(bulletOffset));

                Vector3f lookVectorF = new Vector3f(lookVector);
                projectile.shoot(lookVectorF.x(), lookVectorF.y(), lookVectorF.z(), getBulletSpeed(barrel, bridge), level.random.nextFloat() * getVariance(barrel, handle));
                level.addFreshEntity(projectile);
            }
            changeAmmoLevel(user, item, -barrel.Properties.BulletsPerShot);

            user.setXRot(user.getXRot() - getRecoil(barrel, handle));
            setFireCooldown(item, level);
            level.playSound(null, user.blockPosition(), barrel.Properties.SoundProvider.GetGunshotNearAudio(user, level, item), SoundSource.PLAYERS, 1.0F, 1.0f);
        } else {
            user.setXRot(user.getXRot() - getRecoil(barrel, handle));
        }
    }

    @Override
    public void getDetailedTooltip(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (Properties.Automatic)
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.automatic"));
        if (!Properties.CanHipFire)
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.nohipfire"));

        BarrelItem barrel = getBarrel(pStack);
        CoreItem core = getCore(pStack);
        BridgeItem bridge = getBridge(pStack);
        HandleItem handle = getHandle(pStack);

        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.aimspeed", Math.round((Properties.AimCarryModifierAttribute.getAmount() + 1) * 100) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.hipspeed", Math.round((Properties.HipCarryModifierAttribute.getAmount() + 1) * 100) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.bulletspeed", Math.round(getBulletSpeed(barrel, bridge) * 100) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.recoil", Math.round(getRecoil(barrel, handle) * 100) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.variance", Math.round(getVariance(barrel, handle) * 100) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.dmg", Math.round(getBaseDamage(barrel, core) * 50) / 100f));
        pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.weapon.reload", Math.round(200f / getReloadInterval(pStack)) / 100f));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (pStack.getItem() instanceof WeaponItem) {
            CompoundTag tags = pStack.getOrCreateTag();
            CompoundTag parts = tags.getCompound(TAG_PARTS);
            ItemStack barrel = ItemStack.of(parts.getCompound(TAG_PARTS_BARREL));
            ItemStack core = ItemStack.of(parts.getCompound(TAG_PARTS_CORE));
            ItemStack bridge = ItemStack.of(parts.getCompound(TAG_PARTS_BRIDGE));
            ItemStack handle = ItemStack.of(parts.getCompound(TAG_PARTS_HANDLE));
            if (!barrel.isEmpty())
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.barrel", barrel.getDisplayName()));
            if (!core.isEmpty())
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.core", core.getDisplayName()));
            if (!bridge.isEmpty())
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bridge", bridge.getDisplayName()));
            if (!handle.isEmpty())
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.handle", handle.getDisplayName()));

            if (core.getItem() instanceof CoreItem coreItem) {
                pTooltipComponents.add(Component.literal(""));
                pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bullet_requirement", coreItem.Properties.BulletItem.getDefaultInstance().getDisplayName().getString().replaceAll("[\\[\\]]", "")));
            }
        } else if (isInvalid(pStack)) {
            pTooltipComponents.add(Component.translatable("tooltip.vulpine_machinery.bad_gun"));
        }
        pTooltipComponents.add(Component.literal(""));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
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
        } else if (isInvalid(pStack)) {
            return Component.literal(Component.translatable(this.getDescriptionId()).getString().substring(2));
        }

        return super.getName(pStack);
    }

    public ItemStack outfitWith(ItemStack weaponItem, ItemStack barrelItem, ItemStack coreItem, ItemStack bridgeItem, ItemStack handleItem) {
        CompoundTag tag = weaponItem.getOrCreateTag();
        CompoundTag parts = tag.getCompound(TAG_PARTS);

        CompoundTag barrel_tag = new CompoundTag();
        barrelItem.save(barrel_tag);

        CompoundTag core_tag = new CompoundTag();
        coreItem.save(core_tag);

        CompoundTag bridge_tag = new CompoundTag();
        bridgeItem.save(bridge_tag);

        CompoundTag handle_tag = new CompoundTag();
        handleItem.save(handle_tag);

        parts.put(TAG_PARTS_BARREL, barrel_tag);
        parts.put(TAG_PARTS_CORE, core_tag);
        parts.put(TAG_PARTS_BRIDGE, bridge_tag);
        parts.put(TAG_PARTS_HANDLE, handle_tag);

        tag.put(TAG_PARTS, parts);

        tag.putLong(TAG_LAST_RELOAD, 0);
        tag.putInt(TAG_BULLETS, 0);
        if (barrelItem.getItem() instanceof BarrelItem bi)
            tag.putInt(TAG_BARREL_VARIANT, bi.ID);

        setReloading(weaponItem, false);

        return weaponItem;
    }

    public ItemStack outfitWith(ItemStack weaponItem, @NotNull Item barrel, @NotNull Item core, @NotNull Item bridge, @NotNull Item handle) {
        return outfitWith(weaponItem, new ItemStack(barrel), new ItemStack(core), new ItemStack(bridge), new ItemStack(handle));
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack def = new ItemStack(this);

        return outfitWith(def, Properties.DefaultBarrel, Properties.DefaultCore, Properties.DefaultBridge, Properties.DefaultHandle);
    }
}
