package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.audio.HandCannonSoundProvider;
import xyz.hydroxa.vulpine_machinery.audio.HeavySoundProvider;
import xyz.hydroxa.vulpine_machinery.audio.MuffledSoundProvider;
import xyz.hydroxa.vulpine_machinery.audio.PistolSoundProvider;
import xyz.hydroxa.vulpine_machinery.enums.ItemOrder;
import xyz.hydroxa.vulpine_machinery.item.custom.*;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.*;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VulpineMachineryMod.MOD_ID);

    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new ComparableItem(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB), ItemOrder.Bullets));
    public static final RegistryObject<Item> BULLET_ENDER = ITEMS.register("bullet_ender",
            () -> new ComparableItem(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB), ItemOrder.Bullets));
    public static final RegistryObject<Item> BULLET_PRISMARINE = ITEMS.register("bullet_prismarine",
            () -> new ComparableItem(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB), ItemOrder.Bullets));
    public static final RegistryObject<Item> BULLET_SOUL = ITEMS.register("bullet_soul",
            () -> new ComparableItem(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB), ItemOrder.Bullets));

    public static final RegistryObject<Item> BEANS = ITEMS.register("beans",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().fast().nutrition(1).saturationMod(0).build()).tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> BRIDGE = ITEMS.register("bridge",
            () -> new BridgeItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BridgeProperties()));
    public static final RegistryObject<Item> TWEAKED_BRIDGE = ITEMS.register("tweaked_bridge",
            () -> new BridgeItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BridgeProperties()
                    .bulletSpeedMultiplier(1.5f)));
    public static final RegistryObject<Item> MECHANIZED_BRIDGE = ITEMS.register("mechanized_bridge",
            () -> new BridgeItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BridgeProperties().
                    reloadSpeedMultiplier(2f)));
    public static final RegistryObject<Item> HANDLE = ITEMS.register("handle",
            () -> new HandleItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new HandleProperties()));

    public static final RegistryObject<Item> BARREL_REVOLVE = ITEMS.register("barrel_revolve",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BarrelProperties("Revolving", new PistolSoundProvider())
                    .bulletType(BulletType.Pistol)
                    .capacity(6)
                    .variance(1f)
                    .ticksPerBulletReloaded(10)));
    public static final RegistryObject<Item> BARREL_SHOTGUN = ITEMS.register("barrel_shotgun",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BarrelProperties("Shotgun", new HeavySoundProvider())
                    .bulletType(BulletType.Heavy)
                    .variance(12f)
                    .damageMultiplier(1f/2f)
                    .ticksPerBulletReloaded(10)
                    .capacity(6)
                    .bulletsPerShot(6)
                    .ticksPerShot(5)
                    .recoilMultiplier(1.25f)));
    public static final RegistryObject<Item> BARREL_DOUBLE = ITEMS.register("barrel_double",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB),  new BarrelProperties("Double", new HeavySoundProvider())
                    .bulletType(BulletType.Heavy)
                    .variance(12f)
                    .damageMultiplier(1f/2f)
                    .ticksPerBulletReloaded(10)
                    .capacity(2)
                    .bulletsPerShot(2)
                    .ticksPerShot(5)
                    .recoilMultiplier(1.25f)));
    public static final RegistryObject<Item> BARREL_SLUG = ITEMS.register("barrel_slug",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BarrelProperties("Slug", new HandCannonSoundProvider())
                    .damageMultiplier(2f)
                    .bulletType(BulletType.HandCannon)
                    .variance(6)
                    .ticksPerBulletReloaded(20)
                    .ticksPerShot(15)
                    .bulletSpeedMultiplier(0.5f)
                    .recoilMultiplier(1.5f)));
    public static final RegistryObject<Item> BARREL_MUFFLE = ITEMS.register("barrel_muffle",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new BarrelProperties("Muffled", new MuffledSoundProvider())
                    .bulletType(BulletType.Muffled)
                    .variance(0.5f)
                    .ticksPerBulletReloaded(8)
                    .ticksPerShot(25)
                    .recoilMultiplier(0.75f)));

    public static final RegistryObject<Item> CORE_STANDARD = ITEMS.register("core_standard",
            () -> new DamagingCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_ASSASSIN = ITEMS.register("core_assassin",
            () -> new AssassinCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())
                    .damageMult(0)));
    public static final RegistryObject<Item> CORE_BEAN = ITEMS.register("core_bean",
            () -> new BeanCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_CURSED = ITEMS.register("core_cursed",
            () -> new NotYetImplementedItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)/*, new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())*/));
    public static final RegistryObject<Item> CORE_ENDER = ITEMS.register("core_ender",
            () -> new EnderCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_ENDER.get())
                    .damageMult(0.25f)));
    public static final RegistryObject<Item> CORE_FLAME = ITEMS.register("core_flame",
            () -> new FlameCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_HEALTH = ITEMS.register("core_health",
            () -> new HealthCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_ICE = ITEMS.register("core_ice",
            () -> new IceCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_PRISMARINE = ITEMS.register("core_prismarine",
            () -> new NotYetImplementedItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB/*, new CoreProperties()
                    .bulletItem(BULLET_PRISMARINE.get()*/))); //TODO
    public static final RegistryObject<Item> CORE_REDSTONE = ITEMS.register("core_redstone",
            () -> new RedstoneCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_SHIELD = ITEMS.register("core_shield",
            () -> new ShieldCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_SLIME = ITEMS.register("core_slime",
            () -> new SlimeCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_PRISMARINE.get())));
    public static final RegistryObject<Item> CORE_SANGUINE = ITEMS.register("core_sanguine",
            () -> new SanguineCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_WITHER = ITEMS.register("core_wither",
            () -> new WitherCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));


    public static final RegistryObject<Item> PEPPERBOX = ITEMS.register("pepperbox",
            () -> new WeaponItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.MACHINERY_TAB),
                    new WeaponProperties(
                            new ResourceLocation(VulpineMachineryMod.MOD_ID, "weaponry/pepperbox"),
                            BARREL_REVOLVE.get(),
                            CORE_STANDARD.get(),
                            BRIDGE.get(),
                            HANDLE.get())
                    .carrySpeedModifier(0, -0.3f)
                    .recoil(4f)));
    public static final RegistryObject<Item> RIFLE = ITEMS.register("rifle",
            () -> new WeaponItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.MACHINERY_TAB),
                    new WeaponProperties(
                            new ResourceLocation(VulpineMachineryMod.MOD_ID, "weaponry/rifle"),
                            BARREL_REVOLVE.get(),
                            CORE_STANDARD.get(),
                            BRIDGE.get(),
                            HANDLE.get())
                            .carrySpeedModifier(0, -0.8f)));


    public static final RegistryObject<Item> BLUEPRINT = ITEMS.register("blueprint",
            () -> new BlueprintItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BULLET_PROJECTILE_SMALL = ITEMS.register("z_bullet_render_helper_small",
            () -> new RenderHelperItem(new Item.Properties().stacksTo(1000000)));
    public static final RegistryObject<Item> BULLET_PROJECTILE_LARGE = ITEMS.register("z_bullet_render_helper_large",
            () -> new RenderHelperItem(new Item.Properties().stacksTo(1000000)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
