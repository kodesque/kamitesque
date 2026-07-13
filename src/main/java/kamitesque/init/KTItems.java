package kamitesque.init;

import kamitesque.common.items.ItemAidedEye;
import kamitesque.common.items.ItemDebug;
import kamitesque.common.items.ItemGlyphTablet;
import kamitesque.common.templates.ItemKTBase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class KTItems {

    /* "tablet", "sigil", "tome", "blade" */
    public static Item seal_printed;
    public static Item glyph_tablet;
    public static Item pure_shard;

    public static Item augment_eye;
    public static Item augment_discharger;

    public static Item debug;

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(debug = new ItemDebug("debug"));

        iForgeRegistry.register(augment_eye = new ItemAidedEye("augment_eye"));
        iForgeRegistry.register(augment_discharger = new ItemKTBase("augment_discharger"));

        iForgeRegistry.register(seal_printed = new ItemKTBase("seal_printed", "tablet", "sigil", "tome", "blade"));
        iForgeRegistry.register(glyph_tablet = new ItemGlyphTablet("glyph_tablet"));
        iForgeRegistry.register(pure_shard = new ItemKTBase("pure_shard"));

    }

}
