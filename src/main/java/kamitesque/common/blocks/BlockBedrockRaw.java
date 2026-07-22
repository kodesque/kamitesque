package kamitesque.common.blocks;

import kamitesque.common.templates.BlockKTBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import java.util.Random;

public class BlockBedrockRaw extends BlockKTBase {

    public static final String id = "bedrock_raw";

    public BlockBedrockRaw() {
        super(Material.BARRIER, id);
        this.setBlockUnbreakable();
        this.setTickRandomly(true);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
            worldIn.setBlockState(pos, Blocks.BEDROCK.getDefaultState());

            worldIn.playSound(
                    null,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    SoundsTC.poof,
                    SoundCategory.BLOCKS,
                    1.0F,
                    1.0F
            );

            FXDispatcher.INSTANCE.drawBamf(pos.getX() + 0.5F, pos.getY() + (double) 0.5F, pos.getZ() + 0.5F, 16719133, false, false, EnumFacing.UP);

    }
}
