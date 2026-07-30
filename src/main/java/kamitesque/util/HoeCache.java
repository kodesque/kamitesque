package kamitesque.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Random;

public class HoeCache {

    public static ArrayList<Integer> seeds = new ArrayList<Integer>();
    public static ArrayList<Integer> saplings = new ArrayList<Integer>();

    public static ArrayList<DataBundle> galvanize_drops = new ArrayList<DataBundle>();

    public static void fillHoeCache() {

        galvanize_drops.add(new DataBundle(Blocks.CARROTS, Items.GOLDEN_CARROT, 0.5F, 1, 2));
        galvanize_drops.add(new DataBundle(Blocks.MELON_BLOCK, Items.SPECKLED_MELON, 0.8F, 1, 5));
        galvanize_drops.add(new DataBundle(Blocks.LEAVES, Items.GOLDEN_APPLE, 0.3F, 1, 1));

        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            if (ForgeRegistries.ITEMS.getValue(key) instanceof ItemSeeds
                    || key.getPath().contains("seed")
                    || key.getPath().contains("seeds")
                    || ForgeRegistries.ITEMS.getValue(key).getItemStackDisplayName(new ItemStack(ForgeRegistries.ITEMS.getValue(key))).contains("seed")
                    || ForgeRegistries.ITEMS.getValue(key).getItemStackDisplayName(new ItemStack(ForgeRegistries.ITEMS.getValue(key))).contains("seeds")) {

                if (!seeds.contains(Item.getIdFromItem(ForgeRegistries.ITEMS.getValue(key)))) {
                    seeds.add(Item.getIdFromItem(ForgeRegistries.ITEMS.getValue(key)));
                }
            }
        }

        for (ResourceLocation key : ForgeRegistries.BLOCKS.getKeys()) {
            if (ForgeRegistries.BLOCKS.getValue(key) instanceof BlockSapling
                    || key.getPath().contains("sapling")
                    || ForgeRegistries.ITEMS.getValue(key).getItemStackDisplayName(new ItemStack(ForgeRegistries.ITEMS.getValue(key))).contains("sapling")) {

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
