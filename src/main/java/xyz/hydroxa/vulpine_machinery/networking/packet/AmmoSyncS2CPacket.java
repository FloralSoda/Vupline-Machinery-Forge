package xyz.hydroxa.vulpine_machinery.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.hydroxa.vulpine_machinery.client.ClientAmmoData;

import java.util.function.Supplier;

public class AmmoSyncS2CPacket {
    private final int ammo;
    private final int capacity;
    public AmmoSyncS2CPacket(int ammo, int capacity) {
        this.ammo = ammo;
        this.capacity = capacity;
    }

    public AmmoSyncS2CPacket(FriendlyByteBuf buf) {
        this.ammo = buf.readInt();
        this.capacity = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(ammo);
        buf.writeInt(capacity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ammo < 0)
                ClientAmmoData.set_visible(false);
            else {
                ClientAmmoData.set_ammo(ammo);
                ClientAmmoData.set_capacity(capacity);
                ClientAmmoData.set_visible(true);
            }
        });

        return true;
    }

}
