package xyz.hydroxa.vulpine_machinery.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletProjectile;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VulpineMachineryMod.MOD_ID);
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET_PISTOL = ENTITY_TYPES.register("bullet_pistol",
            () -> EntityType.Builder.<BulletProjectile>of(BulletProjectile::new, MobCategory.MISC)
            .sized(0.15F, 0.15F)
            .clientTrackingRange(4)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(1)
            .build(new ResourceLocation(VulpineMachineryMod.MOD_ID, "bullet_pistol").toString()));
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET_MUFFLED = ENTITY_TYPES.register("bullet_muffled",
            () -> EntityType.Builder.<BulletProjectile>of(BulletProjectile::new, MobCategory.MISC)
                    .sized(0.15F, 0.15F)
                    .clientTrackingRange(4)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .build(new ResourceLocation(VulpineMachineryMod.MOD_ID, "bullet_muffled").toString()));
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET_HEAVY = ENTITY_TYPES.register("bullet_heavy",
            () -> EntityType.Builder.<BulletProjectile>of(BulletProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .build(new ResourceLocation(VulpineMachineryMod.MOD_ID, "bullet_heavy").toString()));
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET_HAND_CANNON = ENTITY_TYPES.register("bullet_hand_cannon",
            () -> EntityType.Builder.<BulletProjectile>of(BulletProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .build(new ResourceLocation(VulpineMachineryMod.MOD_ID, "bullet_hand_cannon").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
