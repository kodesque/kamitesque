package kamitesque.common.items;

import kamitesque.common.entities.EntityPortalRift;
import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTBlocks;
import kamitesque.root.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import thaumcraft.common.entities.EntityFluxRift;
import thecodex6824.thaumicaugmentation.api.TABlocks;

public class ItemReinforcedPhial extends ItemKTBase {

    public ItemReinforcedPhial(String name, String... variants) {
        super(name, variants);
    }

    public @NotNull EnumActionResult onItemUse(EntityPlayer player, @NotNull World world, @NotNull BlockPos pos, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getMetadata() == 0 || world.isRemote) return EnumActionResult.FAIL;

        int meta = stack.getMetadata();
        int dimension = 0;
        Block block;

        switch (meta) {
            case 1: {
                dimension = -1;
                block = KTBlocks.portal_nether_cut;
                break;
            }
            case 2: {
                dimension = 1;
                block = KTBlocks.portal_end_cut;
                break;
            }
            default: {
                return EnumActionResult.FAIL;
            }
        }

        if (world.getBlockState(pos).getBlock().equals(TABlocks.FORTIFIED_GLASS)) {
            world.destroyBlock(pos, false);
            world.setBlockState(pos, block.getDefaultState());
        } else {
            EntityPortalRift rift = new EntityPortalRift(world, dimension);
            BlockPos offset = pos.offset(facing);
            Vec3d position = new Vec3d((double)offset.getX() + (double)0.5F, (double)offset.getY() + (double)0.5F, (double)offset.getZ() + (double)0.5F);
            rift.setRiftSeed(world.rand.nextInt());
            rift.setLocationAndAngles(position.x, position.y, position.z, (float)world.rand.nextInt(360), 0.0F);
            rift.setRiftStability(0.0F);
            rift.setRiftSize(10);
            world.spawnEntity(rift);
        }

        if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
        }

        return EnumActionResult.SUCCESS;
    }

    public @NotNull String getItemStackDisplayName(ItemStack stack) {

        int meta = stack.getMetadata();

        if (meta == 0) {
            return super.getItemStackDisplayName(stack);
        }

        String baseBit = super.getItemStackDisplayName(stack);
        String portalBit = null;

        switch (stack.getMetadata()) {
            case 1: {
                portalBit = new TextComponentTranslation("tooltip." + Main.MODID + ".portal.nether").getFormattedText();
                break;
            }
            case 2: {
                portalBit = new TextComponentTranslation("tooltip." + Main.MODID + ".portal.end").getFormattedText();
                break;
            }
        }

        return baseBit + ": " + portalBit;
    }

}
