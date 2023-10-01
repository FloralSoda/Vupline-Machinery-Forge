package xyz.hydroxa.vulpine_machinery.mixin;

import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;
import xyz.hydroxa.vulpine_machinery.networking.ModMessages;
import xyz.hydroxa.vulpine_machinery.networking.packet.AmmoSyncS2CPacket;

@Mixin(ServerGamePacketListenerImpl.class)
public class CarriedItemMixin {
    @Shadow public ServerPlayer player;

    @Inject(method = "handleSetCarriedItem(Lnet/minecraft/network/protocol/game/ServerboundSetCarriedItemPacket;)V", at = @At(value = "JUMP", ordinal = 1, shift = At.Shift.AFTER))
    public void syncAmmoData(ServerboundSetCarriedItemPacket pPacket, CallbackInfo ci) {
        ItemStack heldItem = player.getInventory().getItem(pPacket.getSlot());
        if (heldItem.getItem() instanceof WeaponItem wi) {
            ModMessages.sendToPlayer(new AmmoSyncS2CPacket(wi.getRemainingBullets(heldItem), wi.getBulletCapacity(heldItem)), player);
        } else {
            ModMessages.sendToPlayer(new AmmoSyncS2CPacket(-1, 0), player);
        }
    }
}
