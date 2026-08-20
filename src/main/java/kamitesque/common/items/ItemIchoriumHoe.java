package kamitesque.common.items;

import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.IThaumcraftItems;

public class ItemIchoriumHoe extends ItemHoe implements IWarpingGear, IThaumcraftItems {

    protected String BASE_NAME;
    protected String[] VARIANTS;
    protected int[] VARIANTS_META;

    public ItemIchoriumHoe(String name, String... variants) {
        super(Main.isRebornLoaded() ? ToolMaterial.valueOf("ICHORIUM") : ToolMaterial.valueOf("ICHOR"));

        this.setRegistryName(Main.MODID, name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setHasSubtypes(variants.length > 1);

        this.setMaxDamage(0);

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
        return Main.isRebornLoaded() ? 1 : 0;
    }

    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        if (enchantment.equals(Enchantments.FORTUNE)) {return true;}
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
}
