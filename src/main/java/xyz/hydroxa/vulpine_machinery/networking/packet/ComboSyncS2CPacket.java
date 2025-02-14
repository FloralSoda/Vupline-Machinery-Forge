package xyz.hydroxa.vulpine_machinery.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.hydroxa.vulpine_machinery.client.ClientComboData;

import java.util.function.Supplier;

public class ComboSyncS2CPacket {
    private final int combo;
    public ComboSyncS2CPacket(int combo) {
        this.combo = combo;
    }

    public ComboSyncS2CPacket(FriendlyByteBuf buf) {
        this.combo = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(combo);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientComboData.combo = combo);

        return true;
    }
}
