package kamitesque.root;

import kamitesque.init.KTEntities;
import kamitesque.init.KTItems;
import kamitesque.init.KTResearch;
import kamitesque.init.KTTiles;
import kamitesque.network.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Main.MODID, dependencies =
//        "required-after:thaumcraft;" +
//        "required-after:thaumic-augmentation;" +
//        "required-after:kami-reborn;" +
        "after:new-crimson-revelations;" +
//        "required-after:thaumic-isorropia-kedition;" +
        "after:thaumic-tinkerer-reborn;" +
        "after:planar-artifice-reflected;" +
        "after:thaumic-bases-unofficial;" +
//        "after:thaumic-forever;" +
        "after:thaumic-attempts",
        version = Main.VERSION, name = Main.NAME)

public class Main {
    public static final String MODID = "kamitesque";
    public static final String NAME = "Kamitesque";
    public static final String VERSION = "0.0.1-ALPHA";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "kamitesque.network.proxy.ClientProxy", serverSide = "kamitesque.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit(event);

        KTTiles.preInitTiles();
        KTEntities.preInitEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        KTItems.fillCreativeTab();

        KTResearch.initResearch();
        KTResearch.initScans();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
