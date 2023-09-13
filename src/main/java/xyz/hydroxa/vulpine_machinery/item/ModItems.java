package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.world.item.CreativeModeTab;
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
            () -> new Item(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_COMBAT)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
