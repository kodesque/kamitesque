package kamitesque.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Random;

public class HoeCache {

    public static ArrayList<Integer> saplings = new ArrayList<>();

    public static ArrayList<DataBundle> galvanize_drops = new ArrayList<DataBundle>();

    public static void fillHoeCache() {

        galvanize_drops.add(new DataBundle(Blocks.CARROTS, Items.GOLDEN_CARROT, 0.5F, 1, 3));
        galvanize_drops.add(new DataBundle(Blocks.MELON_BLOCK, Items.SPECKLED_MELON, 0.8F, 1, 5));
        galvanize_drops.add(new DataBundle(Blocks.LEAVES, Items.GOLDEN_APPLE, 0.3F, 1, 2));

        for (ResourceLocation key : ForgeRegistries.BLOCKS.getKeys()) {
            if (ForgeRegistries.BLOCKS.getValue(key) instanceof BlockSapling
                    || key.getPath().contains("sapling")
            ) {

                saplings.add(Block.getIdFromBlock(ForgeRegistries.BLOCKS.getValue(key)));
            }
        }

    }

    public static ItemStack getSpecialDrop(Block block, int fortune, Random rand) {
        if (block.equals(Blocks.LEAVES) && fortune > 0 && (rand.nextInt(10) * fortune) < 8) {
            return new ItemStack(Items.GOLDEN_APPLE, 1, 1);
        }

        return ItemStack.EMPTY;
    }

    public static IBlockState getRandomSapling(Random rand) {
        IBlockState state = Block.getBlockById(HoeCache.saplings.get(rand.nextInt(HoeCache.saplings.size()))).getDefaultState();

        Item item = Item.getItemFromBlock(state.getBlock());
        ItemStack stack;
        int meta = 0;

        if (item != null) {
            if (item.getHasSubtypes()) {

                NonNullList<ItemStack> list = NonNullList.create();
                item.getSubItems(CreativeTabs.SEARCH, list);

                for (int i = 0; i < list.size(); i++) {
                    meta++;
                }

                state = state.getBlock().getStateFromMeta(rand.nextInt(meta));
            }
        }

        return state;
    }

    public static class DataBundle {

        public Block block;
        public Item item;
        public float chance;
        public int minAmount;
        public int maxAmount;

        public DataBundle(Block block, Item item, float chance, int minAmount, int maxAmount) {
            this.block = block;
            this.item = item;
            this.chance = chance;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }
    }
}
