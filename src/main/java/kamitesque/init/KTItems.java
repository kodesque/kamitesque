package kamitesque.init;

import kamitesque.common.items.ItemAidedEye;
import kamitesque.common.items.ItemDebug;
import kamitesque.common.items.ItemGlyphTablet;
import kamitesque.common.items.ItemIchoriumNeedle;
import kamitesque.common.templates.ItemKTBase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class KTItems {

    /* "tablet", "sigil", "tome", "blade" */
    public static Item seal_printed;
    public static Item glyph_tablet;
    public static Item pure_shard;
    public static Item cerebral_pearls;

    public static Item augment_eye;
    public static Item augment_discharger;

    public static Item ichorium_needle;
    public static Item ichorium_hoe;
    public static Item awakened_ichorium_hoe;

    public static Item crystal_cluster;

    public static Item debug;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(augment_eye = new ItemAidedEye("augment_eye"));
        iForgeRegistry.register(pure_shard = new ItemKTBase("pure_shard"));

        iForgeRegistry.register(seal_printed = new ItemKTBase("seal_printed", "tablet", "sigil", "tome", "blade"));
        iForgeRegistry.register(glyph_tablet = new ItemGlyphTablet("glyph_tablet"));

        iForgeRegistry.register(augment_discharger = new ItemKTBase("augment_discharger"));
        iForgeRegistry.register(cerebral_pearls = new ItemKTBase("cerebral_pearls"));

        iForgeRegistry.register(ichorium_needle = new ItemIchoriumNeedle("ichorium_needle"));
        iForgeRegistry.register(ichorium_needle = new ItemKTBase("crystal_cluster"));

        iForgeRegistry.register(debug = new ItemDebug("debug"));

    }

}
