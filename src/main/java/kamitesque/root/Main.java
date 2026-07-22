package kamitesque.root;

import kamitesque.init.KTEntities;
import kamitesque.init.KTRecipes;
import kamitesque.init.KTResearch;
import kamitesque.init.KTTiles;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Main.MODID, dependencies =
        "required-after:thaumcraft;" +
        "required-after:thaumicaugmentation;" +
        "required-after:kami;" +
        "after:new-crimson-revelations;" +
        "after:isorropia;" +
        "after:thaumictinkerer;" +
        "after:planarartifice;" +
        "after:thaumicbases;" +
        "after:thaumicforever;" +
        "after:thaumicattempts",
        version = Main.VERSION, name = Main.NAME)

public class Main {
    public static final String MODID = "kamitesque";
    public static final String NAME = "Kamitesque";
    public static final String VERSION = "0.0.7-ALPHA";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "kamitesque.network.proxy.ClientProxy", serverSide = "kamitesque.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        KTNetwork.preInitPackets();

        KTTiles.preInitTiles();
        KTEntities.preInitEntities();

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        KTResearch.initResearch();
        KTResearch.initScans();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        KTRecipes.initFurnace();

    }
}
