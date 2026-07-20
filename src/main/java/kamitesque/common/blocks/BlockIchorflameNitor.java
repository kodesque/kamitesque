package kamitesque.common.blocks;

import kamitesque.common.templates.BlockKTTile;
import kamitesque.common.tiles.TileIchorflameNitor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

@SuppressWarnings("deprecation")
public class BlockIchorflameNitor extends BlockKTTile {

    public static final String id = "ichorflame_nitor";

    public BlockIchorflameNitor() {
        super(Material.CIRCUITS, TileIchorflameNitor.class, id);
        this.setHardness(0.1F);
        this.setSoundType(SoundType.CLOTH);
        this.setLightLevel(1.0F);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB((double)0.33F, (double)0.33F, (double)0.33F, (double)0.66F, (double)0.66F, (double)0.66F);
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
