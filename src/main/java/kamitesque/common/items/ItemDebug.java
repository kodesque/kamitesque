package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTBlocks;
import kamitesque.init.KTItems;
import mod.emt.kami.Kami;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemDebug extends ItemKTBase {

    public ItemDebug(String name, String... variants) {
        super(name, variants);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != Kami.tabKAMI && tab != CreativeTabs.SEARCH) return;

        items.add(new ItemStack (KTItems.augment_eye));
        items.add(new ItemStack (KTItems.persistence_seal));
        items.add(new ItemStack (KTItems.pure_shard));
        items.add(new ItemStack (KTItems.crystal_cluster));

        items.add(new ItemStack (KTItems.seal_printed));
        items.add(new ItemStack (KTItems.glyph_tablet));

        items.add(new ItemStack (KTItems.augment_discharger));
        items.add(new ItemStack (KTItems.cerebral_pearls));

        items.add(new ItemStack (KTBlocks.ichorflame_nitor));

        items.add(new ItemStack (KTItems.ichorium_needle));

        items.add(new ItemStack (KTItems.root_seed));
        items.add(new ItemStack (KTBlocks.root_crystal));

    }
}
