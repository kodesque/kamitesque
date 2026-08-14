package kamitesque.common.entities;

import kamitesque.events.PersistenceEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityItemPersistent extends EntityItem {

    public static String id = "item_persistent";

    public EntityItemPersistent(World worldIn, double x, double p_i1710_3_, double y, ItemStack p_i1710_5_) {
        super(worldIn, x, p_i1710_3_, y, p_i1710_5_);

        this.setEntityInvulnerable(true);
        this.setNoDespawn();
        this.setPickupDelay(20);

        this.rotationYaw = (float)(Math.random() * (double)360.0F);
        this.motionX = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
        this.motionY = (double)0.2F;
        this.motionZ = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
    }

    public EntityItemPersistent(World worldIn) {
        super(worldIn);
    }

    protected void dealFireDamage(int amount) {}

    public void onCollideWithPlayer(EntityPlayer entityIn) {

        if (this.getItem().getTagCompound() != null && this.getItem().getTagCompound().hasKey("kamitesque.persistent")) {
            if (!this.getItem().getTagCompound().getUniqueId("kamitesque.persistent.owner").equals(entityIn.getUniqueID())) {

                PersistenceEvents.punish(entityIn);
                return;
            }
        }

        super.onCollideWithPlayer(entityIn);
    }

    public void onUpdate() {

            if (this.ticksExisted > 1) {
                if (this.motionY > (double)0.0F) {
                    this.motionY *= (double)0.9F;
                }

                this.motionY += (double)0.04F;
            }

            if (this.ticksExisted > 10) {
                this.motionX *= 0.5F;
                this.motionZ *= 0.5F;
            }

            AxisAlignedBB box = this.getEntityBoundingBox().grow(2);

            List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, box);

            if (!entities.isEmpty()) {
                for (Entity entity : entities) {
                    if (entity instanceof EntityLiving) {
                        ((EntityLiving)entity).setCanPickUpLoot(false);

                        if (entity instanceof IMob) {
                            entity.setFire(5);
                        }

                    }
                }
            }
            super.onUpdate();
    }

}


