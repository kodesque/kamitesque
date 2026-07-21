package kamitesque.common.blocks;

import kamitesque.common.templates.BlockKTTile;
import kamitesque.common.tiles.TileRootCrystal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.SoundsTC;

@SuppressWarnings("deprecation")
public class BlockRootCrystal extends BlockKTTile {

    public static String id = "root_crystal";

    public static PropertyBool GROWN = PropertyBool.create("grown");

    AxisAlignedBB box =  new AxisAlignedBB(
    0.25, 0.0, 0.25,
            0.75, 1.0, 0.75
    );

    public BlockRootCrystal() {
        super(Material.GLASS, TileRootCrystal.class, id);
        this.setHardness(0.25F);
        this.setSoundType(SoundsTC.CRYSTAL);
    }

    public SoundType getSoundType() {
        return SoundsTC.CRYSTAL;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK);
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWN);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(GROWN, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWN) ? 1 : 0;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.box;
    }

//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
//
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, this.box);
//    }
//
//    @Nullable
//    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
//        blockState = this.getActualState(blockState, worldIn, pos);
//        return CLIP_AABB_BY_INDEX[getAABBIndex(blockState)];
//    }

}
