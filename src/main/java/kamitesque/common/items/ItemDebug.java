package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTBlocks;
import kamitesque.init.KTItems;
import mod.emt.kami.Kami;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemDebug extends ItemKTBase {

    public ItemDebug(String name) {
        super(name);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != Kami.tabKAMI ) return;

        items.clear();

        items.add(new ItemStack (KTItems.augment_eye));
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

//        for (int i = 0; i < ModItemsKAMI.MOD_ITEMS.size(); i++) {
//            ModItemsKAMI.MOD_ITEMS.get(i).getSubItems(tab, items);
//        }
    }
}
