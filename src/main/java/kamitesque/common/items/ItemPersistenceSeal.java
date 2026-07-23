package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

import javax.annotation.Nullable;

public class ItemPersistenceSeal extends ItemKTBase {

    public ItemPersistenceSeal(String name, String... variants) {
        super(name, variants);
        this.setMaxStackSize(1);

        this.addPropertyOverride(
                new ResourceLocation(Main.MODID, "charging"),
                new IItemPropertyGetter() {

                    @SideOnly(Side.CLIENT)
                    @Override
                    public float apply(ItemStack stack,
                                       @Nullable World world,
                                       @Nullable EntityLivingBase entity) {

                        return entity != null
                                && entity.isHandActive()
                                && entity.getActiveItemStack() == stack
                                ? 1.0F
                                : 0.0F;
                    }
                }
        );
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3600;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(
            World world,
            EntityPlayer player,
            EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        if (hand.equals(EnumHand.OFF_HAND)) {
            return new ActionResult<>(
                    EnumActionResult.FAIL,
                    stack
            );
        }

        if (player.getHeldItemOffhand().isEmpty() || player.getHeldItemOffhand().getItem().equals(KTItems.persistence_seal)) {
            return new ActionResult<>(
                    EnumActionResult.FAIL,
                    stack
            );
        }

        if (player.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            return new ActionResult<>(
                    EnumActionResult.FAIL,
                    stack
            );
        }

        player.setActiveHand(hand);

        return new ActionResult<>(
                EnumActionResult.SUCCESS,
                stack
        );
    }

    @Override
    public void onPlayerStoppedUsing(
            ItemStack stack,
            World world,
            EntityLivingBase entity,
            int timeLeft) {

        if (entity instanceof EntityPlayer) {

            int usedTime = getMaxItemUseDuration(stack) - timeLeft;

            if (!world.isRemote && usedTime >= 60) {

                NBTTagCompound nbt = entity.getHeldItemOffhand().getTagCompound() != null ? entity.getHeldItemOffhand().getTagCompound() : new NBTTagCompound();

                nbt.setBoolean("kamitesque.persistent", true);
                nbt.setUniqueId("kamitesque.persistent.owner", entity.getUniqueID());

                entity.getHeldItemOffhand().setTagCompound(nbt);

                ((EntityPlayer)entity).addExperienceLevel(-15);

                if (entity.hasCapability(ThaumcraftCapabilities.WARP, null)) {
                    IPlayerWarp cap = entity.getCapability(ThaumcraftCapabilities.WARP, null);
                    cap.add(IPlayerWarp.EnumWarpType.NORMAL, 10);
                }

                ((EntityPlayer)entity).getCooldownTracker().setCooldown(this, 600);

            }
        }
    }


}
