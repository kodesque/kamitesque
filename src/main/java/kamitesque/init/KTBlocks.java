package kamitesque.init;

import kamitesque.common.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.Thaumcraft;

import java.util.ArrayList;
import java.util.List;

public class KTBlocks {

    public static Block ichorflame_nitor;
    public static Block root_crystal;
    public static Block bedrock_raw;
    public static Block obisian_soft;

    public static Block portal_end_cut;
    public static Block portal_nether_cut;

    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static void initBlocks() {

        KTBlocks.ichorflame_nitor = registerBlock(new BlockIchorflameNitor());
        KTBlocks.root_crystal = registerBlock(new BlockRootCrystal());
        KTBlocks.bedrock_raw = registerBlockSpecial(new BlockBedrockRaw());
        KTBlocks.obisian_soft = registerBlockSpecial(new BlockObsidianSoft());

        KTBlocks.portal_nether_cut = registerBlockSpecial(new BlockPortalCut());
        KTBlocks.portal_end_cut = registerBlockSpecial(new BlockEndPortalCut());
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
