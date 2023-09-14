package xyz.hydroxa.vulpine_machinery.item.custom;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.audio.SoundProvider;
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletProjectile;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.*;

import java.util.List;

public class WeaponItem extends Item implements Vanishable {
    public static final float BASE_DAMAGE = 15f;
    public SoundProvider SoundProvider;
    public WeaponItem(Properties pProperties, SoundProvider soundProvider) {
        super(pProperties);
        this.SoundProvider = soundProvider;
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
        CompoundTag barrel = tag.getCompound("Parts").getCompound("Barrel");
        Item barrelItem = ItemStack.of(barrel).getItem();
        if (barrelItem instanceof BarrelItem bi)
            return bi;
        else
            return null;
    }
    public CoreItem getCore(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag core = tag.getCompound("Parts").getCompound("Core");
        Item coreItem = ItemStack.of(core).getItem();
        if (coreItem instanceof CoreItem ci)
            return ci;
        else
            return null;
    }
    public HandleItem getHandle(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag handle = tag.getCompound("Parts").getCompound("Handle");
        Item handleItem = ItemStack.of(handle).getItem();
        if (handleItem instanceof HandleItem hi)
            return hi;
        else
            return null;
    }
    public BridgeItem getBridge(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        CompoundTag bridge = tag.getCompound("Parts").getCompound("Bridge");
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
        item.getOrCreateTag().putInt("Bullets", remaining - amount);
        return remaining - amount;
    }
    public int getRemainingBullets(ItemStack item) {
        return item.getOrCreateTag().getInt("Bullets");
    }

    public int getBulletCapacity(ItemStack item) {
        return getBarrel(item).Properties.Capacity;
    }

    public Item getBulletItem(ItemStack item) {
        return getCore(item).Properties.BulletItem;
    }
    /**
    Returns true if compatible bullets were found. Returns false if there weren't any bullets
     */
    public boolean reload(LivingEntity user, ItemStack item) {
        if (getRemainingBullets(item) < getBulletCapacity(item)) {
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

            item.getOrCreateTag().putInt("Bullets", getRemainingBullets(item) + 1);

            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (getRemainingBullets(itemstack) > -1) {
            BarrelItem barrel = getBarrel(itemstack);
            shootProjectile(pLevel, pPlayer, itemstack, barrel, getCore(itemstack));
            consumeBullets(itemstack, barrel.Properties.BulletsPerShot);
        } else {
            pPlayer.startUsingItem(pUsedHand);
        }
        return InteractionResultHolder.consume(itemstack);
    }


    private void shootProjectile(Level level, LivingEntity user, ItemStack item, BarrelItem barrel, CoreItem core) {
        if (!level.isClientSide) {
            BulletProjectile projectile = new BulletProjectile(level, user, barrel.Properties.BulletType);
            projectile.setCore(new ItemStack(core));
            projectile.setDamage(BASE_DAMAGE * barrel.Properties.DamageMultiplier);

            if (barrel.Properties.BulletType == BulletType.HandCannon) {
                projectile.setPierceLevel((byte) 5);
            }

            //TODO: Figure out why this is just spawning the bullets high into the sky
            var fireAngle = barrel.getFireAngle(level, user, item);
            Vec3 vec31 = user.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(vec31), fireAngle, true);
            Vec3 vec3 = user.getViewVector(1.0F);
            Vector3f vector3f = new Vector3f(vec3);
            vector3f.transform(quaternion);
            projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), barrel.Properties.BulletSpeed, barrel.Properties.Variance);

            level.addFreshEntity(projectile);
            level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundProvider.GetGunshotNearAudio(user, level, item), SoundSource.PLAYERS, 1.0F, 1.0f);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (pStack.getItem() instanceof WeaponItem) {
            CompoundTag tags = pStack.getOrCreateTag();
            CompoundTag parts = tags.getCompound("Parts");
            ItemStack barrel = ItemStack.of(parts.getCompound("Barrel"));
            ItemStack core = ItemStack.of(parts.getCompound("Core"));
            ItemStack bridge = ItemStack.of(parts.getCompound("Bridge"));
            ItemStack handle = ItemStack.of(parts.getCompound("Handle"));
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
            CompoundTag parts = tags.getCompound("Parts");
            ItemStack barrel = ItemStack.of(parts.getCompound("Barrel"));
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
        new ItemStack(ModItems.BARREL_REVOLVE.get()).save(barrel);

        CompoundTag core = new CompoundTag();
        new ItemStack(ModItems.CORE_STANDARD.get()).save(core);

        CompoundTag bridge = new CompoundTag();
        new ItemStack(ModItems.BRIDGE.get()).save(bridge);

        CompoundTag handle = new CompoundTag();
        new ItemStack(ModItems.HANDLE.get()).save(handle);

        parts.put("Barrel", barrel);
        parts.put("Core", core);
        parts.put("Bridge", bridge);
        parts.put("Handle", handle);

        tag.put("Parts", parts);
        return def;
    }
}
