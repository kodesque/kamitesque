package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPersistenceSeal extends ItemKTBase {

    public ItemPersistenceSeal(String name, String... variants) {
        super(name, variants);
        this.setMaxStackSize(1);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (playerIn.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            return new ActionResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

//        for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
//            if (playerIn.inventory.getStackInSlot(i).getItem().equals(ItemsTC)) {
//
//            }
//        }

        NBTTagCompound nbt = playerIn.getHeldItemMainhand().getTagCompound();

        nbt.setBoolean("kamitesque.persistent", true);
        nbt.setUniqueId("kamitesque.persistent.owner", playerIn.getUniqueID());

        return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
