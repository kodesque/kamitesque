package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;
import thecodex6824.thaumicaugmentation.api.TAMaterials;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGlyphTablet extends ItemKTBase implements IWarpingGear {

    TextComponentTranslation desc = new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "glyph_tablet");

    public ItemGlyphTablet(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return TAMaterials.RARITY_ELDRITCH;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(this.desc.getFormattedText());
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 5;
    }
}
