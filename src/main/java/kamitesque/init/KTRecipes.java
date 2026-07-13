package kamitesque.init;

import kamitesque.common.recipes.RecipeAugmentAdd;
import kamitesque.common.recipes.RecipeAugmentRemove;
import kamitesque.common.recipes.RecipeSealPrint;
import kamitesque.root.Main;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class KTRecipes {

    public static void initWorkbench(IForgeRegistry<IRecipe> iForgeRegistry) {

    }

    public static void initInfusion(IForgeRegistry<IRecipe> iForgeRegistry) {

    }

    public static void initCrucible(IForgeRegistry<IRecipe> iForgeRegistry) {

    }

    public static void initRest(IForgeRegistry<IRecipe> iForgeRegistry) {
        iForgeRegistry.register(new RecipeAugmentAdd().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentAdd.id)));
        iForgeRegistry.register(new RecipeAugmentRemove().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentRemove.id)));
        iForgeRegistry.register(new RecipeSealPrint().setRegistryName(new ResourceLocation(Main.MODID, RecipeSealPrint.id)));
    }
}
