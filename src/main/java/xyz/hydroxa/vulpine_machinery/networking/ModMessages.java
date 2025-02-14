package xyz.hydroxa.vulpine_machinery.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.networking.packet.AmmoSyncS2CPacket;
import xyz.hydroxa.vulpine_machinery.networking.packet.ComboSyncS2CPacket;
import xyz.hydroxa.vulpine_machinery.networking.packet.HitSyncS2CPacket;
import xyz.hydroxa.vulpine_machinery.networking.packet.ReloadC2SPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(VulpineMachineryMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ReloadC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReloadC2SPacket::new)
                .encoder(ReloadC2SPacket::toBytes)
                .consumerMainThread(ReloadC2SPacket::handle)
                .add();
        net.messageBuilder(AmmoSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AmmoSyncS2CPacket::new)
                .encoder(AmmoSyncS2CPacket::toBytes)
                .consumerMainThread(AmmoSyncS2CPacket::handle)
                .add();
        net.messageBuilder(HitSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(HitSyncS2CPacket::new)
                .encoder(HitSyncS2CPacket::toBytes)
                .consumerMainThread(HitSyncS2CPacket::handle)
                .add();
        net.messageBuilder(ComboSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ComboSyncS2CPacket::new)
                .encoder(ComboSyncS2CPacket::toBytes)
                .consumerMainThread(ComboSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
