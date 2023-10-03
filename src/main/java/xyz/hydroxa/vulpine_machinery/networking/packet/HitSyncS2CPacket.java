package xyz.hydroxa.vulpine_machinery.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;
import xyz.hydroxa.vulpine_machinery.client.ClientAmmoData;

import java.util.function.Supplier;

public class HitSyncS2CPacket {
    private final boolean hit;
    private final boolean wasPlayer;
    public HitSyncS2CPacket(boolean hit, boolean wasPlayer) {
        this.hit = hit;
        this.wasPlayer = wasPlayer;
    }

    public HitSyncS2CPacket(FriendlyByteBuf buf) {
        hit = buf.readBoolean();
        wasPlayer = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hit);
        buf.writeBoolean(wasPlayer);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (hit) {
                ClientAmmoData.set_hitmarker();
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null)
                    mc.player.playSound(wasPlayer ? SoundEvents.ARROW_HIT_PLAYER : SoundEvents.NOTE_BLOCK_HAT);
            }
        });

        return true;
    }

}
