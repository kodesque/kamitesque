package kamitesque.init;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class KTEnchants {

    public static EnumInfusionEnchantment IETHOUSANDYARD = ThaumicAPI.registerInfusionEnchantment("KT_THOUSANDYARD", 1, "KT_THOUSANDYARD_ENCHANT", new String[]{"weapon", "axe"});
    public static EnumInfusionEnchantment IESHARPEYE = ThaumicAPI.registerInfusionEnchantment("KT_SHARPEYE", 1, "KT_SHARPEYE_ENCHANT", new String[]{"weapon", "axe"});
    public static EnumInfusionEnchantment IEALLFRONTS = ThaumicAPI.registerInfusionEnchantment("KT_ALLFRONTS", 1, "KT_ALLFRONTS_ENCHANT", new String[]{"weapon", "axe"});

    public KTEnchants() {}
}
