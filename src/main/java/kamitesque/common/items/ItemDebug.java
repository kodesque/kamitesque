package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemDebug extends ItemKTBase {

    public ItemDebug(String name, String... variants) {
        super(name, variants);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != CreativeTabs.SEARCH) return;

        //        items.add();
    }

}
