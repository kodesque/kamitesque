package kamitesque.init;

import kamitesque.common.items.*;
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
    public static Item root_seed;

    public static Item augment_eye;
    public static Item augment_discharger;

    /* "empty", "nether", "end" */
    public static Item reinforced_phial;
    public static Item unstable_phial;

    public static Item ichorium_needle;
    public static Item persistence_seal;
    public static Item ichorium_hoe;
    public static Item awakened_ichorium_hoe;

    public static Item dimensional_cutter;

    /* "boots", "ring" */
    public static Item boots_mutable;

    public static Item crystal_cluster;

    /* "vortex", "wings" */
    public static Item debug;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(augment_eye = new ItemAidedEye("augment_eye"));
        iForgeRegistry.register(pure_shard = new ItemKTBase("pure_shard"));
        iForgeRegistry.register(root_seed = new ItemKTBase("root_seed"));

        iForgeRegistry.register(seal_printed = new ItemKTBase("seal_printed", "tablet", "sigil", "tome", "blade"));
        iForgeRegistry.register(glyph_tablet = new ItemGlyphTablet("glyph_tablet"));

        iForgeRegistry.register(ichorium_needle = new ItemIchoriumNeedle("ichorium_needle"));
        iForgeRegistry.register(persistence_seal = new ItemPersistenceSeal("persistence_seal"));
        iForgeRegistry.register(crystal_cluster = new ItemKTBase("crystal_cluster"));

        iForgeRegistry.register(ichorium_hoe = new ItemIchoriumHoe("ichorium_hoe"));
        iForgeRegistry.register(awakened_ichorium_hoe = new ItemAwakenedHoe("awakened_ichorium_hoe"));

        iForgeRegistry.register(dimensional_cutter = new ItemCutter("dimensional_cutter"));
        iForgeRegistry.register(reinforced_phial = new ItemReinforcedPhial("reinforced_phial", "empty", "nether", "end"));
        iForgeRegistry.register(unstable_phial = new ItemUnstablePhial("unstable_phial", "empty", "nether", "end"));

        iForgeRegistry.register(boots_mutable = new ItemBootsMutable("boots_mutable", "boots", "ring"));

        iForgeRegistry.register(debug = new ItemDebug("debug", "vortex", "wings"));

    }

}
