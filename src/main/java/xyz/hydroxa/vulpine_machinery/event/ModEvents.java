package xyz.hydroxa.vulpine_machinery.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.AmmoSyncS2CPacket;

@Mod.EventBusSubscriber(modid = VulpineMachineryMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide) {
            if (event.getEntity() instanceof ServerPlayer player) {
                ItemStack item = player.getMainHandItem();
                if (!(item.getItem() instanceof WeaponItem))
                    item = player.getOffhandItem();

                if (item.getItem() instanceof WeaponItem wi)
                    ModMessages.sendToPlayer(new AmmoSyncS2CPacket(wi.getRemainingBullets(item), wi.getBulletCapacity(item)), player);
            }
        }
    }
}
