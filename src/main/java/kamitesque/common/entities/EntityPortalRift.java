package kamitesque.common.entities;

import kamitesque.common.world.TeleporterGateless;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.entities.EntityFluxRift;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockBamf;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.world.aura.AuraHandler;

import java.util.List;

public class EntityPortalRift extends EntityFluxRift {

    public static final String id = "portal_rift";

    private int dimensionId;
    public final String dimensionIdKey = "dimensionId";

    public EntityPortalRift(World par1World) {
        super(par1World);
    }

    public EntityPortalRift(World par1World, int dimension_id) {
        super(par1World);
        this.dimensionId = dimension_id;
    }

    protected void entityInit() {
        super.func_70088_a();
    }

    public int getDimension() {
        return this.dimensionId;
    }

    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            if (this.getRiftSeed() == 0) {
                this.setRiftSeed(this.rand.nextInt());
            }

            if (!this.points.isEmpty()) {
                int pi = this.rand.nextInt(this.points.size() - 1);
                Vec3d v1 = ((Vec3d)this.points.get(pi)).add(this.posX, this.posY, this.posZ);
                Vec3d v2 = ((Vec3d)this.points.get(pi + 1)).add(this.posX, this.posY, this.posZ);
                RayTraceResult rt = this.world.rayTraceBlocks(v1, v2, false);
                if (rt != null && rt.getBlockPos() != null) {
                    BlockPos p = new BlockPos(rt.getBlockPos());
                    IBlockState bs = this.world.getBlockState(p);
                    if (!this.world.isAirBlock(p) && bs.getBlockHardness(this.world, p) >= 0.0F && bs.getBlock().canCollideCheck(bs, false)) {
                        this.world.playEvent((EntityPlayer)null, 2001, p, Block.getStateId(this.world.getBlockState(p)));
                        this.world.setBlockToAir(p);
                    }
                }
            }

            if (this.points.size() < 3 && !this.getCollapse()) {
                this.setCollapse(true);
            }

            if (this.getCollapse()) {
                this.setRiftSize(this.getRiftSize() - 1);

                if (this.rand.nextInt(10) == 0) {
                    this.world.createExplosion(this, this.posX + this.rand.nextGaussian() * (double)2.0F, this.posY + this.rand.nextGaussian() * (double)2.0F, this.posZ + this.rand.nextGaussian() * (double)2.0F, this.rand.nextFloat() / 2.0F, false);
                }

                if (this.getRiftSize() <= 1) {
                    this.completeCollapsePortal();
                    return;
                }
            }

            if (this.ticksExisted % 120 == 0) {
                this.setRiftStability(this.getRiftStability() - 0.2F);
            }

            if (this.ticksExisted % 600 == this.getEntityId() % 600) {
                float taint = AuraHandler.getFlux(this.world, this.getPosition());
                double size = Math.sqrt((double)(this.getRiftSize() * 2));
                if ((double)taint >= size && this.getRiftSize() < 100 && this.getStability() != EntityFluxRift.EnumStability.VERY_STABLE) {
                    AuraHandler.drainFlux(this.getEntityWorld(), this.getPosition(), (float)size, false);
                    this.setRiftSize(this.getRiftSize() + 1);
                }
            }

            if (!this.isDead && this.ticksExisted % 300 == 0) {
                this.playSound(SoundsTC.evilportal, (float)((double)0.15F + this.rand.nextGaussian() * 0.066), (float)((double)0.75F + this.rand.nextGaussian() * 0.1));
            }
        } else {
            if (!this.points.isEmpty() && this.points.size() > 2 && !this.getCollapse() && this.getRiftStability() < 0.0F && (float)this.rand.nextInt(150) < Math.abs(this.getRiftStability())) {
                int pi = 1 + this.rand.nextInt(this.points.size() - 2);
                Vec3d v1 = ((Vec3d)this.points.get(pi)).add(this.posX, this.posY, this.posZ);
                FXDispatcher.INSTANCE.drawCurlyWisp(v1.x, v1.y, v1.z, (double)0.0F, (double)0.0F, (double)0.0F, 0.1F + (Float)this.pointsWidth.get(pi) * 3.0F, 1.0F, 1.0F, 1.0F, 0.25F, (EnumFacing)null, 1, 0, 0);
            }

            if (!this.points.isEmpty() && this.points.size() > 2 && this.getCollapse()) {
                int pi = 1 + this.rand.nextInt(this.points.size() - 2);
                Vec3d v1 = ((Vec3d)this.points.get(pi)).add(this.posX, this.posY, this.posZ);
                FXDispatcher.INSTANCE.drawCurlyWisp(v1.x, v1.y, v1.z, (double)0.0F, (double)0.0F, (double)0.0F, 0.1F + (Float)this.pointsWidth.get(pi) * 3.0F, 1.0F, 0.3F + this.rand.nextFloat() * 0.1F, 0.3F + this.rand.nextFloat() * 0.1F, 0.4F, (EnumFacing)null, 1, 0, 0);
            }
        }

    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (entityIn.world.isRemote) {return;}
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = entityIn;

            if (this.dimensionId != 0) {
                if (this.dimensionId == -1) {
                    player.changeDimension(this.dimensionId, new TeleporterGateless((WorldServer) entityIn.world));
                } else {
                    player.changeDimension(this.dimensionId);
                }
            }
        }
    }

    private void completeCollapsePortal() {

        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockBamf(this.posX, this.posY, this.posZ, 0, true, true, (EnumFacing)null), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.posX, this.posY, this.posZ, (double)64.0F));
        List<EntityLivingBase> list = EntityUtils.getEntitiesInRange(this.world, this.posX, this.posY, this.posZ, this, EntityLivingBase.class, (double)32.0F);

        this.setDead();
    }

    public void func_70014_b(NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.setInteger(this.dimensionIdKey, this.dimensionId);
    }

    public void func_70037_a(NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        this.dimensionId = nbttagcompound.getInteger(this.dimensionIdKey);
    }
}
