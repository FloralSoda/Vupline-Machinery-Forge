package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

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

    public static final RegistryObject<Item> BLUEPRINT = ITEMS.register("blueprint",
            () -> new Item(new Item.Properties().stacksTo(4).tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> BEANS = ITEMS.register("beans",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().fast().nutrition(1).saturationMod(0).build()).tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> BRIDGE = ITEMS.register("bridge",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> TWEAKED_BRIDGE = ITEMS.register("tweaked_bridge",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> MECHANIZED_BRIDGE = ITEMS.register("mechanized_bridge",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> HANDLE = ITEMS.register("handle",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> BARREL_SLUG = ITEMS.register("barrel_slug",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BARREL_MUFFLE = ITEMS.register("barrel_muffle",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BARREL_SHOTGUN = ITEMS.register("barrel_shotgun",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> BARREL_REVOLVE = ITEMS.register("barrel_revolve",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> CORE_ASSASSIN = ITEMS.register("core_assassin",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_BEAN = ITEMS.register("core_bean",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_CURSED = ITEMS.register("core_cursed",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_ENDER = ITEMS.register("core_ender",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_FLAME = ITEMS.register("core_flame",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_HEALTH = ITEMS.register("core_health",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_ICE = ITEMS.register("core_ice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_PRISMARINE = ITEMS.register("core_prismarine",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_SHIELD = ITEMS.register("core_shield",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_SLIME = ITEMS.register("core_slime",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_STANDARD = ITEMS.register("core_standard",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));
    public static final RegistryObject<Item> CORE_WITHER = ITEMS.register("core_wither",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static final RegistryObject<Item> PEPPERBOX = ITEMS.register("pepperbox",
            () -> new Item(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.MACHINERY_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
