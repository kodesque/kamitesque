package kamitesque.common.tiles;

import kamitesque.common.blocks.BlockRootCrystal;
import kamitesque.init.KTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileRootCrystal extends TileThaumcraft implements ITickable {

    public static final String id = "root_crystal";

    public static int progress;
    public static String progressKey = "progress";
    public static int maxProgress = 2400;

    public static int delay;
    public static String delayKey = "delay";
    public static int maxDelay = 40;

    public TileRootCrystal() {
        progress = 0;
        delay = maxDelay;
    }

    public void update() {

        if (!world.isRemote) {

            if (progress < maxProgress) {
                progress++;
            } else if (progress == maxProgress && !isGrown()) {
                this.world.setBlockState(this.pos, KTBlocks.root_crystal.getDefaultState().withProperty(BlockRootCrystal.GROWN, true));
            }

            if (isGrown() && delay > 0) {
                delay--;
            } else if (delay == 0) {
                this.world.setBlockState(this.pos, Blocks.BEDROCK.getDefaultState());

                this.world.playSound(
                        null,
                        this.getPos().getX(),
                        this.getPos().getY(),
                        this.getPos().getZ(),
                        SoundsTC.poof,
                        SoundCategory.BLOCKS,
                        1.0F,
                        1.0F
                );

            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getInteger(progressKey);
        this.delay = nbttagcompound.getInteger(delayKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger(progressKey, progress);
        nbttagcompound.setInteger(delayKey, delay);
        return nbttagcompound;
    }

    public boolean isGrown() {
        return this.world.getBlockState(this.pos).getValue(BlockRootCrystal.GROWN);
    }

}
