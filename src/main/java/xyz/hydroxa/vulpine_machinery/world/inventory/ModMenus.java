package xyz.hydroxa.vulpine_machinery.world.inventory;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, VulpineMachineryMod.MOD_ID);

    //public static final RegistryObject<MenuType<GunsmithingMenu>> GUNSMITHING =
            //MENU_TYPES.register("gunsmithing", () -> new GunsmithingMenu());

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
