package xyz.hydroxa.vulpine_machinery.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.ReloadC2SPacket;
import xyz.hydroxa.vulpine_machinery.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = VulpineMachineryMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        private static boolean reloadWasDown = false;
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (reloadWasDown || KeyBinding.RELOAD_WEAPON_KEY.isDown()) {
                reloadWasDown = KeyBinding.RELOAD_WEAPON_KEY.isDown();
                ModMessages.sendToServer(new ReloadC2SPacket(reloadWasDown));
            }
        }
    }
    @Mod.EventBusSubscriber(modid = VulpineMachineryMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.RELOAD_WEAPON_KEY);
        }
    }
}
