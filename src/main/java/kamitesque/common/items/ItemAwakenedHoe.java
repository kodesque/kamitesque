package kamitesque.common.items;

import mod.emt.kami.registry.ModSoundsKAMI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemAwakenedHoe extends ItemIchoriumHoe {

    public ItemAwakenedHoe(String name, String... variants) {
        super(name, variants);

        this.addPropertyOverride(new ResourceLocation("conversion_mode"), (stack, worldIn, entityIn) -> (float) ItemAwakenedHoe.EnumConversionMode.getMode(stack).ordinal());

//        this.addPropertyOverride(
//                new ResourceLocation(Main.MODID, "extracting"),
//                new IItemPropertyGetter() {
//
//                    @SideOnly(Side.CLIENT)
//                    @Override
//                    public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
//
//                        return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
//                    }
//                }
//        );
    }

//    public int getMaxItemUseDuration(ItemStack stack) {
//        return 3600;
//    }
//
//    public EnumAction getItemUseAction(ItemStack stack) {
//        return EnumAction.BOW;
//    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        ItemStack itemstack = player.getHeldItem(hand);

        EnumConversionMode current = EnumConversionMode.getMode(itemstack);

        if (current.equals(EnumConversionMode.EXTRACT)) {

        } else if (current.equals(EnumConversionMode.MUTATE)) {

        }

        return null;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        EnumConversionMode current = EnumConversionMode.getMode(itemstack);

        if (current.equals(EnumConversionMode.ELIMINATE)) {
            if (target instanceof EntityLivingBase && !target.getHeldItemMainhand().isEmpty()) {

                float chance = target instanceof EntityPlayer ? 0.1F : 0.5F;

                if (target.world.rand.nextFloat() == chance) {
                    target.replaceItemInInventory(EntityEquipmentSlot.MAINHAND.getSlotIndex(), ItemStack.EMPTY);
                    target.entityDropItem(stack, 0);

                    playerIn.getCooldownTracker().setCooldown(this, 600);
                }
            }
        }

        return false;
    }



    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, @NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            ItemAwakenedHoe.EnumConversionMode mode = ItemAwakenedHoe.EnumConversionMode.getMode(heldStack).nextMode();
            ItemAwakenedHoe.EnumConversionMode.setMode(heldStack, mode);
            world.playSound((EntityPlayer)null, player.getPosition(), ModSoundsKAMI.ITEM_ICHOR_TOGGLE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F);
            player.sendStatusMessage((new TextComponentTranslation("tooltip.kamitesque.tool.conversion_mode." + mode, new Object[0])).setStyle((new Style()).setColor(mode.getTextColor())), true);
        } else if (hand == EnumHand.MAIN_HAND) {
            player.setActiveHand(hand);
        }

        return new ActionResult(EnumActionResult.SUCCESS, heldStack);
    }

    public enum EnumConversionMode {
        EXTRACT(TextFormatting.GRAY),
        MUTATE(TextFormatting.BLUE),
        GALVANIZE(TextFormatting.DARK_GREEN),
        ELIMINATE(TextFormatting.DARK_RED);

        private final TextFormatting textColor;

        EnumConversionMode(TextFormatting textColor) {
            this.textColor = textColor;
        }

        public String toString() {
            return super.toString().toLowerCase();
        }

        public TextFormatting getTextColor() {
            return this.textColor;
        }

        public ItemAwakenedHoe.EnumConversionMode nextMode() {
            ItemAwakenedHoe.EnumConversionMode[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

        public static ItemAwakenedHoe.EnumConversionMode getMode(ItemStack stack) {
            ItemAwakenedHoe.EnumConversionMode[] values = values();
            int ordinal = stack.getTagCompound() != null ? stack.getTagCompound().getInteger("mode") : 0;
            ordinal = MathHelper.clamp(ordinal, 0, values.length - 1);
            return values[ordinal];
        }

        public static void setMode(ItemStack stack, ItemAwakenedHoe.EnumConversionMode mode) {
            stack.setTagInfo("mode", new NBTTagInt(mode.ordinal()));
        }
    }

}
