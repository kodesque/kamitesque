package kamitesque.common.entities;

import kamitesque.events.front.PersistentItemEvents;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemPersistent extends EntityItem {

    public static String id = "item_persistent";

    public EntityItemPersistent(World worldIn, double x, double p_i1710_3_, double y, ItemStack p_i1710_5_) {
        super(worldIn, x, p_i1710_3_, y, p_i1710_5_);
        init();
    }

    public EntityItemPersistent(World worldIn) {
        super(worldIn);
        init();
    }

    private void init() {
        this.setEntityInvulnerable(true);
        this.setNoDespawn();

        this.rotationYaw = (float)(Math.random() * (double)360.0F);
        this.motionX = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
        this.motionY = (double)0.2F;
        this.motionZ = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
    }

    protected void dealFireDamage(int amount) {}

    public void onCollideWithPlayer(EntityPlayer entityIn) {

        if (entityIn.world.isRemote) {
            return;
        }

        if (this.getItem().getTagCompound() != null && this.getItem().getTagCompound().hasKey("kamitesque.persistent")) {
            if (!this.getItem().getTagCompound().getUniqueId("kamitesque.persistent.owner").equals(entityIn.getUniqueID())) {

                PersistentItemEvents.punish(entityIn);

                return;
            }
        }

        super.onCollideWithPlayer(entityIn);
    }

    }


