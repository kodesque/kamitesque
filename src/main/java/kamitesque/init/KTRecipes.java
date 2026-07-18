package kamitesque.init;

import kamitesque.common.recipes.RecipeAugmentAdd;
import kamitesque.common.recipes.RecipeAugmentRemove;
import kamitesque.common.recipes.RecipeSealPrint;
import kamitesque.root.Main;
import mod.emt.kami.registry.ModItemsKAMI;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thecodex6824.thaumicaugmentation.api.TABlocks;

public class KTRecipes {

    public static void initWorkbench(IForgeRegistry<IRecipe> iForgeRegistry) {

        ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("kamitesque:augment_eye"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "KT_ANCIENTS",
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
                        new ItemStack(KTItems.pure_shard),
                        'E',
                        new ItemStack(Items.ENDER_EYE)
                )
        );

    }

    public static void initInfusion(IForgeRegistry<IRecipe> iForgeRegistry) {

        ItemStack[] sealStacks = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(KTItems.seal_printed);
            stack.setItemDamage(i);
            sealStacks[i] = stack;
        }

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("kamitesque:glyph_tablet"),
                new InfusionRecipe("KT_BASE",
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

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("kamitesque:ichorium_needle"),
                new InfusionRecipe("KT_ICHORIUM_NEEDLE",
                        new ItemStack (KTItems.ichorium_needle),
                        5,
                        new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.TRAP, 16),
                        new ItemStack(Items.BONE),
                        "ichor",
                        "ingotGold",
                        new ItemStack(ItemsTC.nuggets, 1, 10),
                        new ItemStack(Items.ENDER_EYE)
                )
        );

    }

    public static void initCrucible(IForgeRegistry<IRecipe> iForgeRegistry) {

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:ichorflame_nitor"),
                new CrucibleRecipe("KT_ICHORFLAME",
                        new ItemStack(KTBlocks.ichorflame_nitor, 4),
                        new ItemStack(ModItemsKAMI.ICHOR),
                        new AspectList().merge(Aspect.ALCHEMY, 30).merge(Aspect.AVERSION, 30).merge(Aspect.SENSES, 30))
        );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:crystal_cluster"),
                new CrucibleRecipe("KT_CRYSTALCLUSTER",
                        new ItemStack(KTItems.crystal_cluster),
                        new ItemStack(TABlocks.STRANGE_CRYSTAL),
                        new AspectList().merge(Aspect.ORDER, 30).merge(Aspect.CRYSTAL, 30))
        );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:pure_shard"),
                new CrucibleRecipe("KT_ANCIENTS",
                        new ItemStack(KTItems.pure_shard, 2),
                        new ItemStack(TABlocks.STRANGE_CRYSTAL),
                        new AspectList().merge(Aspect.ENTROPY, 30))
        );

    }

    public static void initFurnace() {
        ThaumcraftApi.addSmeltingBonus(KTItems.crystal_cluster, new ItemStack(KTItems.pure_shard, 3));
    }

    public static void initRest(IForgeRegistry<IRecipe> iForgeRegistry) {
        iForgeRegistry.register(new RecipeAugmentAdd().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentAdd.id)));
        iForgeRegistry.register(new RecipeAugmentRemove().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentRemove.id)));
        iForgeRegistry.register(new RecipeSealPrint().setRegistryName(new ResourceLocation(Main.MODID, RecipeSealPrint.id)));

        GameRegistry.addSmelting(
                KTItems.crystal_cluster,
                new ItemStack(KTItems.pure_shard),
                2
        );
    }
}
