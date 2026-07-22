package kamitesque.common.items;

import kamitesque.root.Main;
import mod.emt.kami.Kami;
import mod.emt.kami.registry.ModItemsKAMI;
import mod.emt.kami.utils.helpers.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class ItemIchoriumHoe extends ItemHoe implements IWarpingGear {

    protected ItemIchoriumHoe(String name, String... variants) {
        super(ModItemsKAMI.MATERIAL_ICHORIUM);

        this.setRegistryName(Main.MODID, name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Kami.tabKAMI);
    }

    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            ItemHelper.setUnbreakable(stack);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.REFINING, 4);
            items.add(stack);
        }

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

    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        return enchantment != Enchantments.MENDING && enchantment != Enchantments.UNBREAKING ? super.canApplyAtEnchantingTable(stack, enchantment) : false;
    }
}
