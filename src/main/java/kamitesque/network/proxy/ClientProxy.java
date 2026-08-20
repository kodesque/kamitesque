package kamitesque.network.proxy;

import kamitesque.client.renderer.tiles.RenderIchorflameNitor;
import kamitesque.client.renderer.tiles.RenderRootCrystal;
import kamitesque.common.tiles.TileIchorflameNitor;
import kamitesque.common.tiles.TileRootCrystal;
import kamitesque.init.KTRender;
import kamitesque.network.packets.KTNetwork;
import kamitesque.root.Main;
import kamitesque.root.MissingKamiAddonException;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Main.MODID);

        if (!Main.isRebornLoaded() && !Main.isUnofficialLoaded()) {
                throw new MissingKamiAddonException();
        }

        KTNetwork.preInitPacketsClient();

        ClientRegistry.bindTileEntitySpecialRenderer(TileIchorflameNitor.class, new RenderIchorflameNitor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRootCrystal.class, new RenderRootCrystal());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        KTRender.initEntityRender();
    }

}
