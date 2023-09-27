package xyz.hydroxa.vulpine_machinery.item;

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
import xyz.hydroxa.vulpine_machinery.item.custom.BlueprintItem;
import xyz.hydroxa.vulpine_machinery.item.custom.RenderHelperItem;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponProperties;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.*;
import xyz.hydroxa.vulpine_machinery.item.custom.gunpart.cores.StandardCoreItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VulpineMachineryMod.MOD_ID);

    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BULLET_ENDER = ITEMS.register("bullet_ender",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BULLET_PRISMARINE = ITEMS.register("bullet_prismarine",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BULLET_SOUL = ITEMS.register("bullet_soul",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.MACHINERY_TAB)));

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
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), 0, new BarrelProperties("Revolving", new PistolSoundProvider())
                    .bulletType(BulletType.Pistol)
                    .capacity(6)
                    .variance(1f)
                    .ticksPerBulletReloaded(10)));
    public static final RegistryObject<Item> BARREL_SHOTGUN = ITEMS.register("barrel_shotgun",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), 1, new BarrelProperties("Shotgun", new HeavySoundProvider())
                    .bulletType(BulletType.Heavy)
                    .variance(6)
                    .damageMultiplier(1f/6f)
                    .ticksPerBulletReloaded(10)
                    .capacity(6)
                    .bulletsPerShot(6)
                    .ticksPerShot(5)
                    .recoilMultiplier(1.25f)));
    public static final RegistryObject<Item> BARREL_SLUG = ITEMS.register("barrel_slug",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), 2, new BarrelProperties("Slug", new HandCannonSoundProvider())
                    .damageMultiplier(2f)
                    .bulletType(BulletType.HandCannon)
                    .variance(2)
                    .ticksPerBulletReloaded(20)
                    .ticksPerShot(15)
                    .bulletSpeedMultiplier(0.5f)
                    .recoilMultiplier(1.5f)));
    public static final RegistryObject<Item> BARREL_MUFFLE = ITEMS.register("barrel_muffle",
            () -> new BarrelItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), 3, new BarrelProperties("Muffled", new MuffledSoundProvider())
                    .bulletType(BulletType.Muffled)
                    .variance(0.5f)
                    .ticksPerBulletReloaded(8)
                    .ticksPerShot(25)
                    .recoilMultiplier(0.75f)));

    public static final RegistryObject<Item> CORE_STANDARD = ITEMS.register("core_standard",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_ASSASSIN = ITEMS.register("core_assassin",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_BEAN = ITEMS.register("core_bean",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_CURSED = ITEMS.register("core_cursed",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_ENDER = ITEMS.register("core_ender",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_ENDER.get())));
    public static final RegistryObject<Item> CORE_FLAME = ITEMS.register("core_flame",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_HEALTH = ITEMS.register("core_health",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_ICE = ITEMS.register("core_ice",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_PRISMARINE = ITEMS.register("core_prismarine",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_PRISMARINE.get())));
    public static final RegistryObject<Item> CORE_SHIELD = ITEMS.register("core_shield",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET.get())));
    public static final RegistryObject<Item> CORE_SLIME = ITEMS.register("core_slime",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_PRISMARINE.get())));
    public static final RegistryObject<Item> CORE_SANGUINE = ITEMS.register("core_sanguine",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));
    public static final RegistryObject<Item> CORE_WITHER = ITEMS.register("core_wither",
            () -> new StandardCoreItem(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB), new CoreProperties()
                    .bulletItem(BULLET_SOUL.get())));


    public static final RegistryObject<Item> PEPPERBOX = ITEMS.register("pepperbox",
            () -> new WeaponItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.MACHINERY_TAB), new WeaponProperties(BARREL_REVOLVE.get(), CORE_STANDARD.get(), BRIDGE.get(), HANDLE.get())
                    .carrySpeedMultiplier(0, -0.3f)));


    public static final RegistryObject<Item> BLUEPRINT = ITEMS.register("blueprint",
            () -> new BlueprintItem(new Item.Properties().stacksTo(4).tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BULLET_PROJECTILE_SMALL = ITEMS.register("z_bullet_render_helper_small",
            () -> new RenderHelperItem(new Item.Properties().stacksTo(1000000)));
    public static final RegistryObject<Item> BULLET_PROJECTILE_LARGE = ITEMS.register("z_bullet_render_helper_large",
            () -> new RenderHelperItem(new Item.Properties().stacksTo(1000000)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
