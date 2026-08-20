package kamitesque.root;

import kamitesque.init.KTEntities;
import kamitesque.init.KTResearch;
import kamitesque.init.KTTiles;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.proxy.CommonProxy;
import kamitesque.util.HoeCache;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, dependencies =
        "required-after:thaumcraft;" +
        "required-after:thaumicaugmentation;" +
        "required-after:thaumicapi;" +
        "after:kami;" +
//        "after:new-crimson-revelations;" +
//        "after:isorropia;" +
        "after:thaumictinkerer;"
//        "after:planarartifice;" +
//        "after:thaumicbases;" +
//        "after:thaumicforever;" +
//        "after:thaumicattempts"
        ,
        version = Main.VERSION, name = Main.NAME)

public class Main {
    public static final String MODID = "kamitesque";
    public static final String NAME = "Kamitesque";
    public static final String VERSION = "0.1.4";

    @SidedProxy(clientSide = "kamitesque.network.proxy.ClientProxy", serverSide = "kamitesque.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        checkDependencies(event);

        KTNetwork.preInitPacketsServer();
        KTTiles.preInitTiles();
        KTEntities.preInitEntities();

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        KTResearch.initResearch();
        KTResearch.initScans();

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        HoeCache.fillHoeCache();

    }

    private void checkDependencies(FMLPreInitializationEvent event) {

        if (!isRebornLoaded() && !isUnofficialLoaded()) {
            throw new MissingModsException("Kami: Reborn / Thaumic Tinkerer Unofficial", "kami-1.0.6 or above / thaumictinkerer-1.12.2-5.9.15 or above");
        }
    }

    public static boolean isRebornLoaded() {
        return Loader.isModLoaded("kami");
    }

    public static boolean isUnofficialLoaded() {
        return Loader.isModLoaded("thaumictinkerer");
    }
}
