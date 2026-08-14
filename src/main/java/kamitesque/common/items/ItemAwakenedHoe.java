package kamitesque.common.items;

import kamitesque.util.HoeCache;
import mod.emt.kami.registry.ModSoundsKAMI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import java.util.List;

public class ItemAwakenedHoe extends ItemIchoriumHoe {

    public ItemAwakenedHoe(String name, String... variants) {
        super(name, variants);

        this.addPropertyOverride(new ResourceLocation("conversion_mode"), (stack, worldIn, entityIn) -> (float) ItemAwakenedHoe.EnumConversionMode.getMode(stack).ordinal());
    }

    public @NotNull EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack itemstack = player.getHeldItem(hand);

        EnumConversionMode current = EnumConversionMode.getMode(itemstack);
        Block block = worldIn.getBlockState(pos).getBlock();

        if (worldIn.isRemote) {
            return EnumActionResult.FAIL;
        }

        if (current.equals(EnumConversionMode.MUTATE)) {
            if (block instanceof BlockSapling || block.getRegistryName().getPath().contains("sapling")) {

                worldIn.setBlockState(pos, HoeCache.getRandomSapling(worldIn.rand));

                worldIn.playSound(
                        null,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        SoundsTC.wand,
                        SoundCategory.BLOCKS,
                        1.0F,
                        1.0F
                );

                return EnumActionResult.SUCCESS;
            }
        }

        if (current.equals(EnumConversionMode.NORMAL)) {
            super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    public boolean itemInteractionForEntity(@NotNull ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        EnumConversionMode current = EnumConversionMode.getMode(stack);

        if (playerIn.world.isRemote) return false;

        if (current.equals(EnumConversionMode.ELIMINATE)) {
            if (target instanceof EntityLivingBase && !target.getHeldItemMainhand().isEmpty()) {

                float chance = target instanceof EntityPlayer ? 0.05F : 0.3F;

                if (target.world.rand.nextFloat() < chance) {
                    ItemStack held = target.getHeldItemMainhand();

                    target.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    target.entityDropItem(held, 0);
                    if (target instanceof EntityLiving) {
                        ((EntityLiving)target).setCanPickUpLoot(false);
                    }

                    playerIn.world.playSound(
                            null,
                            target.posX,
                            target.posY,
                            target.posZ,
                            SoundsTC.hhoff,
                            SoundCategory.BLOCKS,
                            1.0F,
                            1.0F
                    );

                    return true;
                }

                playerIn.getCooldownTracker().setCooldown(this, 600);
                return true;
            }
        }

        return false;
    }


    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, @NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            ItemAwakenedHoe.EnumConversionMode mode = ItemAwakenedHoe.EnumConversionMode.getMode(heldStack).nextMode();
            ItemAwakenedHoe.EnumConversionMode.setMode(heldStack, mode);
            world.playSound((EntityPlayer) null, player.getPosition(), ModSoundsKAMI.ITEM_ICHOR_TOGGLE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F);
            player.sendStatusMessage((new TextComponentTranslation("tooltip.kamitesque.tool.conversion_mode." + mode, new Object[0])).setStyle((new Style()).setColor(mode.getTextColor())), true);
        } else if (hand == EnumHand.MAIN_HAND) {
            player.setActiveHand(hand);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        ItemAwakenedHoe.EnumConversionMode mode = ItemAwakenedHoe.EnumConversionMode.getMode(stack);
        tooltip.add(mode.getTextColor() + I18n.format("tooltip.kamitesque.tool.conversion_mode." + mode, new Object[0]));
    }

    public enum EnumConversionMode {
        NORMAL(TextFormatting.GRAY),
        MUTATE(TextFormatting.DARK_RED),
        GALVANIZE(TextFormatting.DARK_GREEN),
        ELIMINATE(TextFormatting.BLUE);

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
