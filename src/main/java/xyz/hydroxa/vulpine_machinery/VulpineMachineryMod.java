package xyz.hydroxa.vulpine_machinery;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.hydroxa.vulpine_machinery.audio.ModSoundEvents;
import xyz.hydroxa.vulpine_machinery.block.ModBlocks;
import xyz.hydroxa.vulpine_machinery.client.gui.GunsmithingTableScreen;
import xyz.hydroxa.vulpine_machinery.effect.ModEffects;
import xyz.hydroxa.vulpine_machinery.entity.ModEntities;
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletRenderer;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.recipe.ModRecipes;
import xyz.hydroxa.vulpine_machinery.world.inventory.ModMenus;

@Mod(VulpineMachineryMod.MOD_ID)
public class VulpineMachineryMod
{
    public static final String MOD_ID = "vulpine_machinery";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VulpineMachineryMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSoundEvents.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
                });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.BULLET_PISTOL.get(), BulletRenderer::new_pistol);
            EntityRenderers.register(ModEntities.BULLET_MUFFLED.get(), BulletRenderer::new_muffled);
            EntityRenderers.register(ModEntities.BULLET_HEAVY.get(), BulletRenderer::new_heavy);
            EntityRenderers.register(ModEntities.BULLET_HAND_CANNON.get(), BulletRenderer::new_hand_cannon);

            event.enqueueWork( () -> {
                MenuScreens.register(ModMenus.GUNSMITHING.get(), GunsmithingTableScreen::new);
            });
        }
    }
}
