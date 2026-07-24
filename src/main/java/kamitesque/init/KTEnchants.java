package kamitesque.init;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class KTEnchants {

    public static EnumInfusionEnchantment THOUSANDYARD = ThaumicAPI.registerInfusionEnchantment("KT_THOUSANDYARD", 1, "KT_THOUSANDYARD_ENCHANT", new String[]{"weapon", "axe"});
    public static EnumInfusionEnchantment SHARPEYE = ThaumicAPI.registerInfusionEnchantment("KT_SHARPEYE", 1, "KT_SHARPEYE_ENCHANT", new String[]{"weapon", "axe"});

    public KTEnchants() {}
}
