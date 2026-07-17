package kamitesque.init;

import kamitesque.common.blocks.BlockIchorflameNitor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.Thaumcraft;

import java.util.ArrayList;
import java.util.List;

public class KTBlocks {

    public static Block ichorflame_nitor;

    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static void initBlocks() {
        //        TCABlocks.arcane_brazier = registerBlock(new BlockArcaneBrazier());

        KTBlocks.ichorflame_nitor = registerBlock(new BlockIchorflameNitor());
    }

    private static Block registerBlock(Block block, ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        Thaumcraft.proxy.registerModel(itemBlock);
        KTItems.ITEMS.add(itemBlock);
        return block;
    }

    private static Block registerBlock(Block block) {
        ItemBlock itemBlock = new ItemBlock(block);
        KTItems.ITEMS.add(itemBlock);
        return registerBlock(block, itemBlock);
    }

    private static Block registerBlockSpecial(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }

}
