package kamitesque.network.packets;

import kamitesque.root.Main;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class KTNetwork {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);

    private static int id = 0;

    public static void preInitPackets() {

        INSTANCE.registerMessage(PacketFXSmokeBurst.class, PacketFXSmokeBurst.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketParticleDust.class, PacketParticleDust.class, id++, Side.CLIENT);
    }
}
