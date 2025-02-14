package xyz.hydroxa.vulpine_machinery.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.entity.capabilities.EntityComboProvider;
import xyz.hydroxa.vulpine_machinery.entity.projectile.BulletProjectile;
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
                else
                    ModMessages.sendToPlayer(AmmoSyncS2CPacket.as_invisible(), player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof BulletProjectile) {
            event.getEntity().invulnerableTime = 0;
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            if (player.getMainHandItem().getItem() instanceof WeaponItem wi) {
                ModMessages.sendToPlayer(new AmmoSyncS2CPacket(wi.getRemainingBullets(player.getMainHandItem()), wi.getBulletCapacity(player.getMainHandItem())), player);
            } else if (player.getOffhandItem().getItem() instanceof WeaponItem wi) {
                ModMessages.sendToPlayer(new AmmoSyncS2CPacket(wi.getRemainingBullets(player.getOffhandItem()), wi.getBulletCapacity(player.getOffhandItem())), player);
            } else {
                ModMessages.sendToPlayer(AmmoSyncS2CPacket.as_invisible(), player);
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!event.getObject().getCapability(EntityComboProvider.ENTITY_COMBO).isPresent()) {
            event.addCapability(new ResourceLocation(VulpineMachineryMod.MOD_ID, "properties"), new EntityComboProvider());
        }
    }
}