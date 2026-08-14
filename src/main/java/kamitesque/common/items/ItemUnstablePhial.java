package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemUnstablePhial extends ItemKTBase {
    public ItemUnstablePhial(String name, String... variants) {
        super(name, variants);
    }

    public @NotNull EnumActionResult onItemUse(EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getMetadata() == 0 || world.isRemote) return EnumActionResult.FAIL;

        int meta = stack.getMetadata();
        int dimension = 0;

        switch (meta) {
            case 1: {
                dimension = -1;
                break;
            }
            case 2: {
                dimension = 1;
                break;
            }
            default: {
                return EnumActionResult.FAIL;
            }
        }

        player.changeDimension(dimension);

        player.playSound(
                SoundEvents.BLOCK_GLASS_BREAK,
                1.0F,
                1.0F);

        player.playSound(
                SoundEvents.BLOCK_PORTAL_TRAVEL,
                1.0F,
                1.0F);

        if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
        }

        return EnumActionResult.SUCCESS;
    }

    public @NotNull String getItemStackDisplayName(ItemStack stack) {

        int meta = stack.getMetadata();
        int type = 0;

        if (meta == 0) {
            return super.getItemStackDisplayName(new ItemStack(this));
        }

        String baseBit = KTItems.reinforced_phial.getItemStackDisplayName(new ItemStack(KTItems.reinforced_phial));
        String portalBit = new TextComponentTranslation( "tooltip." + Main.MODID + ".portal").getFormattedText();
        String unstableBit = new TextComponentTranslation( "tooltip." + Main.MODID + ".unstable").getFormattedText();


        switch (stack.getMetadata()) {
            case 1: {
                type = -1;
                break;
            }
            case 2: {
                type = 1;
                break;
            }
        }

        String dimBit = DimensionType.getById(type).getName();

        return baseBit + ": " + unstableBit + " " + dimBit + " " + portalBit;
    }
}
