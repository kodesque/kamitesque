package kamitesque.common.blocks;

import kamitesque.common.templates.BlockKTTile;
import kamitesque.common.tiles.TileRootCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.IBlockFacing;
import thaumcraft.common.lib.SoundsTC;

@SuppressWarnings("deprecation")
public class BlockRootCrystal extends BlockKTTile implements IBlockFacing {

    public static String id = "root_crystal";

    public static PropertyBool IS_GROWING = PropertyBool.create("growing");

    public static final AxisAlignedBB BOX_UP = new AxisAlignedBB(
            0.25, 0.0, 0.25,
            0.75, 1.0, 0.75
    );

    public static final AxisAlignedBB BOX_DOWN = new AxisAlignedBB(
            0.25, 0.0, 0.25,
            0.75, 1.0, 0.75
    );

    public static final AxisAlignedBB BOX_NORTH = new AxisAlignedBB(
            0.25, 0.25, 0.0,
            0.75, 0.75, 1.0
    );

    public static final AxisAlignedBB BOX_SOUTH = new AxisAlignedBB(
            0.25, 0.25, 0.0,
            0.75, 0.75, 1.0
    );

    public static final AxisAlignedBB BOX_WEST = new AxisAlignedBB(
            0.0, 0.25, 0.25,
            1.0, 0.75, 0.75
    );

    public static final AxisAlignedBB BOX_EAST = new AxisAlignedBB(
            0.0, 0.25, 0.25,
            1.0, 0.75, 0.75
    );

    public BlockRootCrystal() {
        super(Material.GLASS, TileRootCrystal.class, id);
        this.setHardness(0.25F);
        this.setSoundType(SoundsTC.CRYSTAL);

        this.setDefaultState(this.getDefaultState().withProperty(IS_GROWING, false));
    }

    public SoundType getSoundType() {
        return SoundsTC.CRYSTAL;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_GROWING, IBlockFacing.FACING);
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
        EnumFacing facing = EnumFacing.byIndex(meta & 7);
        boolean active = (meta & 8) != 0;

        return getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(IS_GROWING, active);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getIndex();

        if (state.getValue(IS_GROWING)) {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
                                        IBlockAccess source,
                                        BlockPos pos) {

        switch (state.getValue(FACING)) {

            case DOWN:
                return BOX_DOWN;

            case NORTH:
                return BOX_NORTH;

            case SOUTH:
                return BOX_SOUTH;

            case WEST:
                return BOX_WEST;

            case EAST:
                return BOX_EAST;

            default:
                return BOX_UP;
        }
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        boolean growing = worldIn.getBlockState(
                pos.offset(facing.getOpposite())
        ).getBlock() == Blocks.BEDROCK;

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(IS_GROWING, growing);
    }

    public void neighborChanged(IBlockState state,
                                World world,
                                BlockPos pos,
                                Block blockIn,
                                BlockPos fromPos) {

        EnumFacing facing = state.getValue(FACING);

        BlockPos supportPos = pos.offset(facing.getOpposite());

        if (!world.getBlockState(supportPos).isSideSolid(
                world,
                supportPos,
                facing)) {

            world.destroyBlock(pos, true);
        }
    }

}
