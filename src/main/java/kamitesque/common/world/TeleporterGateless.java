package kamitesque.common.world;

import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleporterGateless extends Teleporter {
    public TeleporterGateless(WorldServer worldIn) {
        super(worldIn);
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        if (entity instanceof EntityPlayerMP)
            placeInPortal(entity, yaw);
        else
            placeInExistingPortal(entity, yaw);
    }

    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        if (this.world.provider.getDimensionType().getId() != 1) {
            if (!this.placeInExistingPortal(entityIn, rotationYaw))
            {
                this.makePortal(entityIn);
                this.placeInExistingPortal(entityIn, rotationYaw);
            }
        } else {
            int x = MathHelper.floor(entityIn.posX);
            int y = MathHelper.floor(entityIn.posY) - 1;
            int z = MathHelper.floor(entityIn.posZ);

            entityIn.setLocationAndAngles((double)x, (double)y, (double)z, entityIn.rotationYaw, 0.0F);
            entityIn.motionX = 0.0D;
            entityIn.motionY = 0.0D;
            entityIn.motionZ = 0.0D;
        }
    }

    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posZ);
        boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);

        if (this.destinationCoordinateCache.containsKey(l))
        {
            d0 = 0.0D;
            flag = false;
        } else {
            flag = true;
        }

        if (d0 >= 0.0D)
        {
            if (flag)
            {
                this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition(blockpos, this.world.getTotalWorldTime()));
            }

            double d5 = (double)blockpos.getX() + 0.5D;
            double d6 = blockpos.getY();
            double d7 = (double)blockpos.getZ() + 0.5D;

            double d3 = entityIn.motionX;
            double d4 = entityIn.motionZ;
            entityIn.motionX = 0;
            entityIn.motionZ = 0;
            entityIn.rotationYaw = rotationYaw - (float)(entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90);

            if (entityIn instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)entityIn).connection.setPlayerLocation(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
            }
            else
            {
                entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean makePortal(Entity entityIn)
    {
        return true;
    }
}
