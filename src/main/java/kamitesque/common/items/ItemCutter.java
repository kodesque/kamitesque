package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTBlocks;
import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.aura.AuraHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemCutter extends ItemKTBase {
    public ItemCutter(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);

        this.addPropertyOverride(
                new ResourceLocation(Main.MODID, "charging"),
                new IItemPropertyGetter() {

                    @SideOnly(Side.CLIENT)
                    @Override
                    public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {

                        return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
                    }
                }
        );
    }

    @Override
    public int getMaxItemUseDuration(@NotNull ItemStack stack) {
        return 3600;
    }

    @Override
    public @NotNull EnumAction getItemUseAction(@NotNull ItemStack stack) {
        return EnumAction.BOW;
    }

    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    public @NotNull EnumActionResult onItemUse(@NotNull EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        Block block = worldIn.getBlockState(pos).getBlock();

        if (block.equals(Blocks.PORTAL) || block.equals(Blocks.END_PORTAL)) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if (player.inventory.getStackInSlot(i).getItem().equals(KTItems.reinforced_phial)) {
                        player.setActiveHand(hand);

                        return EnumActionResult.SUCCESS;
                    }
                }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void onPlayerStoppedUsing(
            @NotNull ItemStack stack,
            World world,
            @NotNull EntityLivingBase entity,
            int timeLeft) {

        if (world.isRemote) return;

        if (entity instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)entity;

            int usedTime = getMaxItemUseDuration(stack) - timeLeft;

            BlockPos pos = new BlockPos(0,0,0);
            Block block;

            boolean pass1 = false;
            boolean pass2 = false;

            int portalType = 0;
            int phialSlot = 0;

            if (usedTime >= 60) {

                double reach = Minecraft.getMinecraft().playerController.getBlockReachDistance();

                Vec3d eyes = player.getPositionEyes(1.0F);
                Vec3d look = player.getLookVec();
                Vec3d end = eyes.add(look.scale(reach));

                RayTraceResult result = world.rayTraceBlocks(eyes, end, false, false, true);

                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {

                    pos = result.getBlockPos();
                    block = world.getBlockState(pos).getBlock();

                    if (block.equals(Blocks.PORTAL)) {
                        portalType = 1;
                        pass1 = true;
                    } else if (block.equals(Blocks.END_PORTAL)) {
                        portalType = 2;
                        pass1 = true;
                    }
                }

                if (!player.isCreative()) {

                    for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                        if (player.inventory.getStackInSlot(i).getItem().equals(KTItems.reinforced_phial)
                                && player.inventory.getStackInSlot(i).getMetadata() == 0) {
                            phialSlot = i;
                            pass2 = true;
                            break;
                        }
                    }

                } else {
                    pass2 = true;
                }

            }

            if (!pass1 || !pass2) {return;}

            world.createExplosion(
                    null,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    2.0F,
                    false
            );

            float breakChance = 0.3F;
            float convertChance = 0.5F;
            float polluteChance = 0.8F;

            checkNether(world, pos, breakChance, convertChance, polluteChance);
            checkEnd(world, pos, breakChance, convertChance, polluteChance);

            int phialCount = player.inventory.getStackInSlot(phialSlot).getCount();
            int count = Math.max(world.rand.nextInt(4), 1);
            int resultCount = 0;

            if (!player.isCreative()) {
                resultCount = Math.min(phialCount, count);
                player.inventory.getStackInSlot(phialSlot).shrink(resultCount);
            } else {
                resultCount = count;
            }

            ItemStack toGive = new ItemStack(KTItems.reinforced_phial, resultCount, portalType);

            if (!player.addItemStackToInventory(toGive)) {
                player.dropItem(toGive, true);
            }

        }
    }

    private static void checkNether(World world, BlockPos pos, float breakChance, float convertChance, float polluteChange) {

        world.destroyBlock(pos, false);

        List<BlockPos> framePieces = SizeInternal.getNetherFrame(world, pos);
        if (framePieces.isEmpty()) return;

        for (BlockPos framePos : framePieces) {

            if (world.rand.nextFloat() < breakChance) {
                world.destroyBlock(framePos, false);
            } else if (world.rand.nextFloat() < convertChance) {
                world.destroyBlock(framePos, false);
                world.setBlockState(framePos, KTBlocks.obisian_soft.getDefaultState());
            }

            if (world.rand.nextFloat() < polluteChange) {
                AuraHelper.polluteAura(world, framePos, 20.0F, true);
            }
        }

    }

    private static void checkEnd(World world, BlockPos pos, float breakChance, float convertChance, float polluteChange) {

        BlockPattern.PatternHelper patternHelper = BlockEndPortalFrame.getOrCreatePortalShape().match(world, pos);
        if (patternHelper != null) {
            BlockPos blockPos2 = patternHelper.getFrontTopLeft().add(-3, 0, -3);

            for(int n = 0; n < 3; ++n) {
                for(int o = 0; o < 3; ++o) {
                    world.destroyBlock(blockPos2.add(n, 0, o), false);
                }
            }

            List<BlockPos> framePieces = getEndFrame(world, pos, patternHelper);
            if (framePieces.isEmpty()) return;

            for (BlockPos framePos : framePieces) {

                if (world.rand.nextFloat() < breakChance) {
                    world.playEvent(2003, new BlockPos(framePos), 0);
                    world.setBlockState(framePos, world.getBlockState(framePos).withProperty(BlockEndPortalFrame.EYE, false));
                } else if (world.rand.nextFloat() < convertChance) {

                    world.playEvent(2003, new BlockPos(framePos), 0);
                    world.setBlockState(framePos, world.getBlockState(framePos).withProperty(BlockEndPortalFrame.EYE, false));

                    EntityEndermite bug = new EntityEndermite(world);
                    bug.setPosition(framePos.getX(), framePos.getY() + 0.5, framePos.getZ());

                    for (int i = 0; i < 3; i++) {
                        world.spawnEntity(bug);
                    }
                }

                if (world.rand.nextFloat() < polluteChange) {
                    AuraHelper.polluteAura(world, framePos, 20.0F, true);
                }

            }
        }

    }

    private static List<BlockPos> getEndFrame(World world, BlockPos pos, BlockPattern.PatternHelper patternHelper) {

        ArrayList<BlockPos> framePieces = new ArrayList<>();
        BlockPos start = patternHelper.getFrontTopLeft().add(-4, 0, -4);

        for (int n = 0; n < 5; n++) {
            for (int o = 0; o < 5; o++) {
                BlockPos current = start.add(n, 0, o);

                if (world.getBlockState(current).getBlock() == Blocks.END_PORTAL_FRAME) {
                    framePieces.add(current);
                }
            }
        }

        return framePieces;
    }

    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }

    static class SizeInternal {
        public final World world;
        public final EnumFacing.Axis axis;
        public final EnumFacing rightDir;
        public final EnumFacing leftDir;
        public int portalBlockCount;
        public BlockPos bottomLeft;
        public int height;
        public int width;

        public SizeInternal(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
            this.world = worldIn;
            this.axis = p_i45694_3_;
            if (p_i45694_3_ == EnumFacing.Axis.X) {
                this.leftDir = EnumFacing.EAST;
                this.rightDir = EnumFacing.WEST;
            } else {
                this.leftDir = EnumFacing.NORTH;
                this.rightDir = EnumFacing.SOUTH;
            }

            for(BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down()) {
            }

            int i = this.getDistanceUntilEdge(p_i45694_2_, this.leftDir) - 1;
            if (i >= 0) {
                this.bottomLeft = p_i45694_2_.offset(this.leftDir, i);
                this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
                if (this.width < 2 || this.width > 21) {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if (this.bottomLeft != null) {
                this.height = this.calculatePortalHeight();
            }

        }

        protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
            int i;
            for(i = 0; i < 22; ++i) {
                BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);
                if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.OBSIDIAN) {
                    break;
                }
            }

            Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
            return block == Blocks.OBSIDIAN ? i : 0;
        }

        protected int calculatePortalHeight() {
            label56:
            for(this.height = 0; this.height < 21; ++this.height) {
                for(int i = 0; i < this.width; ++i) {
                    BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                    Block block = this.world.getBlockState(blockpos).getBlock();
                    if (!this.isEmptyBlock(block)) {
                        break label56;
                    }

                    if (block == Blocks.PORTAL) {
                        ++this.portalBlockCount;
                    }

                    if (i == 0) {
                        block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    } else if (i == this.width - 1) {
                        block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    }
                }
            }

            for(int j = 0; j < this.width; ++j) {
                if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            } else {
                this.bottomLeft = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean isEmptyBlock(Block blockIn) {
            return blockIn.equals(Blocks.AIR) || blockIn.equals(Blocks.PORTAL) || blockIn.equals(Blocks.FIRE);
        }

        public boolean isValid() {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public static List<BlockPos> getNetherFrame(World world, BlockPos pos) {
            ItemCutter.SizeInternal size = new ItemCutter.SizeInternal(world, pos, EnumFacing.Axis.X);

            if (!size.isValid()) {
                size = new ItemCutter.SizeInternal(world, pos, EnumFacing.Axis.Z);

                if (!size.isValid()) {
                    return Collections.emptyList();
                }
            }

            return buildFrame(size);
        }

        private static List<BlockPos> buildFrame(ItemCutter.SizeInternal size) {
            List<BlockPos> frame = new ArrayList<>();

            BlockPos bottomLeft = size.bottomLeft;
            EnumFacing right = size.rightDir;

            for (int x = 0; x < size.width; x++) {
                frame.add(bottomLeft.offset(right, x).down());
            }

            for (int x = 0; x < size.width; x++) {
                frame.add(bottomLeft.offset(right, x).up(size.height));
            }

            for (int y = 0; y < size.height; y++) {
                frame.add(bottomLeft.up(y).offset(right.getOpposite()));
                frame.add(bottomLeft.up(y).offset(right, size.width));
            }

            return frame;
        }
    }

}