package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTBlocks;
import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

public class ItemDebug extends ItemKTBase {

    public ItemDebug(String name, String... variants) {
        super(name, variants);
    }

    public void getSubItems(CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {

        String tabName = null;
        CreativeTabs tabActual = null;

        if (Main.isRebornLoaded()) {
            tabName = "KamiTab";
        } else if (Main.isUnofficialLoaded()) {
            tabName = "thaumictinkerer";
        }

        if (tabName == null) return;

        for (CreativeTabs t : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (t.getTabLabel().equals(tabName)) {
                tabActual = t;
                break;
            }
        }

        if (tabActual == null) return;

        if (tab != tabActual && tab != CreativeTabs.SEARCH) return;

        items.add(new ItemStack (KTItems.augment_eye));
        items.add(new ItemStack (KTItems.persistence_seal));

        items.add(new ItemStack (KTItems.seal_printed));
        items.add(new ItemStack (KTItems.glyph_tablet));

        items.add(new ItemStack (KTItems.pure_shard));
        items.add(new ItemStack (KTItems.crystal_cluster));

        items.add(new ItemStack (KTBlocks.ichorflame_nitor));

        items.add(new ItemStack (KTItems.ichorium_claw));

        items.add(new ItemStack (KTItems.root_seed));
        items.add(new ItemStack (KTBlocks.root_crystal));

        ItemStack ichorium_hoe = new ItemStack(KTItems.ichorium_hoe);
        ichorium_hoe.setTagInfo("Unbreakable", new NBTTagByte((byte)1));
        ItemStack awakened_ichorium_hoe = new ItemStack(KTItems.awakened_ichorium_hoe);
        awakened_ichorium_hoe.setTagInfo("Unbreakable", new NBTTagByte((byte)1));

        items.add(ichorium_hoe);
        items.add(awakened_ichorium_hoe);

        items.add(new ItemStack(KTItems.dimensional_cutter));
        items.add(new ItemStack(KTItems.reinforced_phial));
        items.add(new ItemStack(KTItems.reinforced_phial, 1, 1));
        items.add(new ItemStack(KTItems.reinforced_phial, 1, 2));

    }
}
