package kamitesque.common.tiles;

import kamitesque.common.blocks.BlockRootCrystal;
import kamitesque.init.KTBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileRootCrystal extends TileThaumcraft implements ITickable {

    public int progress;
    public static final String progressKey = "progress";
    public static final int maxProgress = 2400;

    public TileRootCrystal() {
        this.progress = 0;
    }

    public void update() {

        if (this.isGrowing()) {
            if (this.progress < maxProgress) {
                this.progress++;
            } else {
                this.world.setBlockState(this.pos, KTBlocks.bedrock_raw.getDefaultState());
                world.scheduleUpdate(this.pos, KTBlocks.bedrock_raw, 40);
            }
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getInteger(progressKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger(progressKey, this.progress);
        return nbttagcompound;
    }

    public boolean isGrowing() {
        return this.world.getBlockState(this.pos).getValue(BlockRootCrystal.IS_GROWING);
    }

}
