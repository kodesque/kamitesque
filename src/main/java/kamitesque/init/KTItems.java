package kamitesque.init;

import kamitesque.common.items.ItemAidedEye;
import kamitesque.common.items.ItemGlyphTablet;
import kamitesque.common.templates.ItemKTBase;
import mod.emt.kami.Kami;
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

    public static Item debug;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(augment_eye = new ItemAidedEye("augment_eye"));
        iForgeRegistry.register(augment_discharger = new ItemKTBase("augment_discharger"));

        iForgeRegistry.register(seal_printed = new ItemKTBase("seal_printed", "tablet", "sigil", "tome", "blade"));
        iForgeRegistry.register(glyph_tablet = new ItemGlyphTablet("glyph_tablet"));

        iForgeRegistry.register(pure_shard = new ItemKTBase("pure_shard"));
        iForgeRegistry.register(cerebral_pearls = new ItemKTBase("cerebral_pearls"));

    }

    public static void fillCreativeTab() {
        for (int i = 0; i < KTItems.ITEMS.size(); i++) {
            ITEMS.get(i).setCreativeTab(Kami.tabKAMI);
        }
    }

}
