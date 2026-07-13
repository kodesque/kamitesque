package kamitesque.init;

import kamitesque.common.recipes.RecipeAugmentAdd;
import kamitesque.common.recipes.RecipeAugmentRemove;
import kamitesque.common.recipes.RecipeSealPrint;
import kamitesque.root.Main;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TAItems;

public class KTRecipes {

    public static void initWorkbench(IForgeRegistry<IRecipe> iForgeRegistry) {

        ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("kamitesque:augment_eye"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "KT_MEGALOMANIA",
                        25,
                        new AspectList().add(Aspect.AIR, 2),
                        new ItemStack(KTItems.augment_eye),
                        "TRT",
                        "QIQ",
                        "TET",
                        'T',
                        "plateThaumium",
                        'R',
                        new ItemStack(ItemsTC.morphicResonator),
                        'Q',
                        new ItemStack(ItemsTC.quicksilver),
                        'I',
                        new ItemStack(TAItems.MATERIAL, 1, 5),
                        'E',
                        new ItemStack(Items.ENDER_EYE)
                )
        );

        ItemStack[] sealStacks = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(KTItems.seal_printed);
            stack.setItemDamage(i);
            sealStacks[i] = stack;
        }

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("kamitesque:glyph_tablet"),
                new InfusionRecipe("KT_MEGALOMANIA",
                        new ItemStack (KTItems.glyph_tablet),
                        8,
                        new AspectList().add(Aspect.MIND, 50).add(Aspect.FIRE, 50).add(Aspect.ELDRITCH, 100),
                        new ItemStack(Item.getItemFromBlock(BlocksTC.jarBrain)),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools))
        );

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
