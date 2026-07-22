package kamitesque.common.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemPersistent extends EntityItem {
    public EntityItemPersistent(World worldIn, double x, double p_i1710_3_, double y, ItemStack p_i1710_5_) {
        super(worldIn, x, p_i1710_3_, y, p_i1710_5_);

        this.setEntityInvulnerable(true);
        this.setNoPickupDelay();
        this.setNoDespawn();
    }


}
