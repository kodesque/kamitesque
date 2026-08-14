package kamitesque.common.items;

import kamitesque.init.KTItems;
import kamitesque.root.Main;
import mod.emt.kami.registry.ModSoundsKAMI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.IThaumcraftItems;
import thaumcraft.common.items.casters.foci.FocusEffectRift;
import thaumcraft.common.lib.utils.BlockUtils;
import thecodex6824.thaumicaugmentation.common.item.ItemVoidBoots;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBootsMutable extends ItemVoidBoots implements IThaumcraftItems {
    protected static final String TEXTURE_PATH = (new ResourceLocation("kamitesque", "textures/models/armor/boots_mutable.png")).toString();
    protected static final String TEXTURE_PATH_OVERLAY = (new ResourceLocation("kamitesque", "textures/models/armor/boots_mutable.png")).toString();

    protected String BASE_NAME;
    protected String[] VARIANTS;
    protected int[] VARIANTS_META;

    public ItemBootsMutable(String name, String... variants) {
        super();

        this.setRegistryName(Main.MODID, name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setHasSubtypes(variants.length > 1);

        this.BASE_NAME = name;
        if (variants.length == 0) {
            this.VARIANTS = new String[] { name };
        }
        else {
            this.VARIANTS = variants;
        }
        this.VARIANTS_META = new int[this.VARIANTS.length];
        for (int m = 0; m < this.VARIANTS.length; ++m) {
            this.VARIANTS_META[m] = m;
        }
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);

        KTItems.ITEMS.add(this);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(
            World world,
            EntityPlayer player,
            EnumHand hand) {

        ItemStack stack = player.getHeldItemMainhand();
        int meta = stack.getMetadata();

        stack.setItemDamage(stack.getMetadata() == 1 ? 0 : 1);
        world.playSound((EntityPlayer)null, player.getPosition(), ModSoundsKAMI.ITEM_ICHOR_TOGGLE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public void onArmorTick(@NotNull World world, @NotNull EntityPlayer player, @NotNull ItemStack itemStack) {
        if (player.isEntityAlive()) {
            if (this.armorType.equals(EntityEquipmentSlot.FEET)) {
                NBTTagCompound nbt = itemStack.getTagCompound();

                if (nbt != null && nbt.hasKey("kamitesque.toggle")) {
                    if (nbt.getBoolean("kamitesque.toggle")) {

                        if (world.getWorldTime() % 5 == 0) {

                            BlockPos center = player.getPosition();

                            for (int x = -1; x <= 1; x++) {
                                for (int y = -1; y <= 1; y++) {
                                    for (int z = -1; z <= 1; z++) {
                                        BlockPos pos = center.add(x, y, z);
                                        IBlockState state = world.getBlockState(pos);
                                        Block block = state.getBlock();

                                        if (!block.equals(Blocks.AIR) && !block.equals(BlocksTC.hole) && !BlockUtils.isPortableHoleBlackListed(state)) {
                                            FocusEffectRift.createHole(world, center, player.getHorizontalFacing(), (byte) 2, 2);
                                        }

                                    }
                                }
                            }

                        }

                    }
                }
            }
        }

    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return type == null ? TEXTURE_PATH : TEXTURE_PATH_OVERLAY;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        if (this.hasSubtypes && itemStack.getMetadata() < this.VARIANTS.length && this.VARIANTS[itemStack.getMetadata()] != this.BASE_NAME)
            return String.format(super.getUnlocalizedNameInefficiently(itemStack) + ".%s", this.VARIANTS[itemStack.getMetadata()]);
        return super.getUnlocalizedNameInefficiently(itemStack);
    }

    @Nullable
    public CreativeTabs getCreativeTab() {
        return null;
    }

    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }

    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 1;
    }

    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return 7;
    }

    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        return enchantment != Enchantments.MENDING && enchantment != Enchantments.UNBREAKING ? super.canApplyAtEnchantingTable(stack, enchantment) : false;
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public String[] getVariantNames() {
        return this.VARIANTS;
    }

    @Override
    public int[] getVariantMeta() {
        return this.VARIANTS_META;
    }

    @Override
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }

    @Override
    public ModelResourceLocation getCustomModelResourceLocation(String variant) {
        if (variant.equals(this.BASE_NAME))
            return new ModelResourceLocation("kamitesque:" + this.BASE_NAME);
        return new ModelResourceLocation("kamitesque:" + this.BASE_NAME, variant);
    }

    public void getSubItems(@NotNull CreativeTabs tab, NonNullList<ItemStack> items) {}

    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {}

    public int getMaxCharge(ItemStack stack, EntityLivingBase entity) {
        return 1000;
    }

    public int getDyedColor(ItemStack stack) {return 0;};

    public void setDyedColor(ItemStack stack, int color) {}

    public boolean hasColor(@NotNull ItemStack stack) {
        return true;
    }

    public int getColor(@NotNull ItemStack stack) {
        return this.getDyedColor(stack);
    }

    public void removeColor(@NotNull ItemStack stack) {}

    public void setColor(@NotNull ItemStack stack, int color) {}

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {return EnumActionResult.PASS;}

    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @javax.annotation.Nullable World world, List<String> tooltip, ITooltipFlag flag) {}

}
