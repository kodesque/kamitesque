package kamitesque.common.recipes;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class RecipeInfusionThousandyard extends InfusionEnchantmentRecipe {
    public EnumInfusionEnchantment enchantmentThousandyard;

    public RecipeInfusionThousandyard(EnumInfusionEnchantment ench, AspectList as, Object... components) {
        super(ench, as, components);
    }
}
