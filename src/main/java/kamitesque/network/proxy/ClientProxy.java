package kamitesque.network.proxy;

import kamitesque.client.renderer.tiles.RenderIchorflameNitor;
import kamitesque.common.tiles.TileIchorflameNitor;
import kamitesque.root.Main;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Main.MODID);

        ClientRegistry.bindTileEntitySpecialRenderer(TileIchorflameNitor.class, new RenderIchorflameNitor());
    }

}
