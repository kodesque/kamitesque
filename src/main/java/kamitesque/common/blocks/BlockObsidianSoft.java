package kamitesque.common.blocks;

import kamitesque.common.templates.BlockKTBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.items.ItemsTC;

import java.util.Random;

public class BlockObsidianSoft extends BlockKTBase {

    public static final String id = "obsidian_soft";

    public BlockObsidianSoft() {
        super(Material.ROCK, id);
        this.setHardness(4.0F);
        this.setSoundType(SoundType.STONE);
    }

    public int quantityDropped(Random random) {
        return random.nextInt(4);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemsTC.voidSeed;
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!player.isCreative()) {
            AuraHelper.polluteAura(worldIn, pos, 5.0F, true);
        }
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {}
}
