package kamitesque.init;

import kamitesque.common.blocks.BlockIchorflameNitor;
import kamitesque.common.blocks.BlockRootCrystal;
import kamitesque.common.tiles.TileIchorflameNitor;
import kamitesque.common.tiles.TileRootCrystal;
import kamitesque.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class KTTiles {

    public static void preInitTiles() {
        GameRegistry.registerTileEntity(TileIchorflameNitor.class, new ResourceLocation(Main.MODID, BlockIchorflameNitor.id));
        GameRegistry.registerTileEntity(TileRootCrystal.class, new ResourceLocation(Main.MODID, BlockRootCrystal.id));
    }

}
