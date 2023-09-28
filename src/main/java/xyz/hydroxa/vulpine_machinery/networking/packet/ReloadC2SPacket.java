package xyz.hydroxa.vulpine_machinery.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;

import java.util.function.Supplier;

public class ReloadC2SPacket {
    private final boolean key_pressed;
    public ReloadC2SPacket(boolean pressed) {
        key_pressed = pressed;
    }
    public ReloadC2SPacket(FriendlyByteBuf buf) {
        key_pressed = buf.readBoolean();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(key_pressed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ItemStack item = player.getMainHandItem();
                if (item.getItem() instanceof WeaponItem wi) {
                    if (key_pressed && !player.isUsingItem()) {
                        wi.setReloading(item, true);
                        player.startUsingItem(InteractionHand.MAIN_HAND);
                    }
                    else {
                        wi.setReloading(item, false);
                        player.stopUsingItem();
                    }
                } else {
                    item = player.getOffhandItem();
                    if (item.getItem() instanceof WeaponItem wi) {
                        if (key_pressed && !player.isUsingItem()) {
                            wi.setReloading(item, true);
                            player.startUsingItem(InteractionHand.OFF_HAND);
                        }
                        else {
                            wi.setReloading(item, false);
                            player.stopUsingItem();
                        }
                    }
                }
            }
        });

        return true;
    }
}
