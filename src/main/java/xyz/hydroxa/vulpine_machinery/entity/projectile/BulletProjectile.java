package xyz.hydroxa.vulpine_machinery.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.entity.ModEntities;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.CoreItem;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.HitSyncS2CPacket;

public class BulletProjectile extends Projectile {
    private static final EntityDataAccessor<ItemStack> DATA_CORE_ITEM_STACK = SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.BYTE);
    private static BulletType type = null;

    public BulletProjectile(Level pLevel, LivingEntity shooter, BulletType bulletType) {
        super(switch (bulletType) {
            case Unset -> {
                VulpineMachineryMod.LOGGER.warn("Bullet Projectile was created with an unset type. Please prefer to use a real type (Shares properties with BulletType.PISTOL)");
                yield ModEntities.BULLET_PISTOL.get();
            }
            case Pistol -> ModEntities.BULLET_PISTOL.get();
            case Heavy -> ModEntities.BULLET_HEAVY.get();
            case HandCannon -> ModEntities.BULLET_HAND_CANNON.get();
            case Muffled -> ModEntities.BULLET_MUFFLED.get();
        }, pLevel);
        type = bulletType;
        this.setOwner(shooter);
    }
    public BulletProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        type = BulletType.Unset;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_CORE_ITEM_STACK, ItemStack.EMPTY);
        this.getEntityData().define(DATA_DAMAGE, 1.0f);
        this.entityData.define(PIERCE_LEVEL, (byte)0);
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    public void setCore(ItemStack core) {
        entityData.set(DATA_CORE_ITEM_STACK, core);
    }
    public CoreItem getCore() {
        if (entityData.get(DATA_CORE_ITEM_STACK).getItem() instanceof CoreItem ci)
            return ci;
        else
            return null;
    }
    public void setDamage(float damage) {
        entityData.set(DATA_DAMAGE, damage);
    }
    public float getDamage() {
        return entityData.get(DATA_DAMAGE);
    }
    public void setPierceLevel(byte pPierceLevel) {
        entityData.set(PIERCE_LEVEL, pPierceLevel);
    }
    public byte getPierceLevel() {
        return entityData.get(PIERCE_LEVEL);
    }

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
        var core = getCore();
        if (core != null) {
            core.onCreation(this, getOwner());
        }
    }

    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        var core = getCore();
        if (core != null) {
            core.onEntityHit(this, getOwner(), pResult, getDamage());
        }
        if (getOwner() instanceof ServerPlayer sp)
            ModMessages.sendToPlayer(new HitSyncS2CPacket(true, pResult.getEntity() instanceof Player), sp);
        if (type.CanBreakShields && pResult.getEntity() instanceof Player le) {
            if (le.isBlocking()) {
                ItemStack shield = le.getMainHandItem();
                if (!(shield.getItem() instanceof ShieldItem))
                    shield = le.getOffhandItem();
                if (shield.getItem() instanceof ShieldItem) {
                    le.getCooldowns().addCooldown(shield.getItem(), 100);
                }
            }
        }
    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level.isClientSide) {
            var core = getCore();
            if (core != null) {
                core.onBlockHit(this, getOwner(), pResult);
            }
            if (pResult instanceof EntityHitResult && getPierceLevel() > 0) {
                setPierceLevel((byte) (getPierceLevel() - 1));
            } else {
                level.broadcastEntityEvent(this, (byte) 3);
                discard();
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vec3.x * 0.25D, d0 - vec3.y * 0.25D, d1 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.setDeltaMovement(vec3.scale(f));
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double)this.getGravity(), vec31.z);
        }

        this.setPos(d2, d0, d1);

        var core = getCore();
        if (core != null) {
            getCore().onTick(this, getOwner());
        }

        level.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravity() {
        return 0.03F;
    }


}
