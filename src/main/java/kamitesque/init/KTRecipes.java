package kamitesque.init;

import kamitesque.common.recipes.RecipeAugmentAdd;
import kamitesque.common.recipes.RecipeAugmentRemove;
import kamitesque.common.recipes.RecipeSealPrint;
import kamitesque.root.Main;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.*;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe;
import thecodex6824.thaumicaugmentation.api.TABlocks;
import thecodex6824.thaumicaugmentation.api.TAItems;
import thecodex6824.thaumicaugmentation.common.item.foci.FocusEffectWard;

import java.util.Collections;

public class KTRecipes {

    public static final ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

    public static void initWorkbench() {

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

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("kamitesque:root_seed"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "KT_ROOTCRYSTAL",
                        50,
                        null,
                        new ItemStack(KTItems.root_seed, 4),
                        " S ",
                        "AID",
                        " C ",
                        'S',
                        new ItemStack(ItemsTC.voidSeed),
                        'A',
                        "gemAmber",
                        'I',
                        ingredientIchor(),
                        'D',
                        "gemDiamond",
                        'C',
                        new ItemStack(KTItems.pure_shard)
                )
        );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("kamitesque:reinforced_phial"),
                new ShapelessArcaneRecipe(
                        baseGroup,
                        "KT_PORTALCUTTER",
                        50,
                        null,
                        new ItemStack(KTItems.reinforced_phial, 3),
                        new Object[] {
                                new ItemStack(ItemsTC.phial),
                                new ItemStack(ItemsTC.phial),
                                new ItemStack(ItemsTC.phial),
                                new ItemStack(ItemsTC.plate, 1, 3),
                                new ItemStack(TAItems.MATERIAL, 1, 1),
                                "nuggetIchorium"
                        }
                )
        );

        ItemStack ichorium_hoe = new ItemStack(KTItems.ichorium_hoe);
        ItemStack rod = null;

        if (Main.isRebornLoaded()) {
            rod = GameRegistry.makeItemStack("kami:blessed_silverwood_rod", 0, 1, null);

        } else if (Main.isUnofficialLoaded()) {
            rod = new ItemStack(BlocksTC.logSilverwood);
        }

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("kamitesque:ichorium_hoe"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "KT_ICHORIUMHOE",
                        250,
                        new AspectList().add(Aspect.ENTROPY, 8),
                        ichorium_hoe,
                        "II ",
                        " S ",
                        " S ",
                        'I',
                        "ingotIchorium",
                        'S',
                        rod
                )
        );

    }

    public static void initInfusion() {

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
                new ResourceLocation("kamitesque:ichorium_claw"),
                new InfusionRecipe("KT_ICHORIUMCLAW",
                        new ItemStack (KTItems.ichorium_claw),
                        5,
                        new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.TRAP, 16),
                        new ItemStack(Items.BONE),
                        ingredientIchor(),
                        "ingotGold",
                        new ItemStack(ItemsTC.nuggets, 1, 10),
                        new ItemStack(Items.ENDER_EYE)
                )
        );

        ItemStack bookProtect = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(Collections.singletonMap(Enchantments.PROTECTION, 4), bookProtect);
        ItemStack bookProtectFire = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(Collections.singletonMap(Enchantments.FIRE_PROTECTION, 4), bookProtectFire);
        ItemStack bookProtectBlast = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(Collections.singletonMap(Enchantments.BLAST_PROTECTION, 4), bookProtectBlast);
        ItemStack bookProtectProj = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(Collections.singletonMap(Enchantments.PROJECTILE_PROTECTION, 4), bookProtectProj);

        ItemStack focusWard = new ItemStack(TAItems.FOCUS_ANCIENT);
        FocusPackage fp_1 = new FocusPackage();
        FocusEffectWard ward = new FocusEffectWard();
        fp_1.addNode(ward);
        ItemFocus.setPackage(focusWard, fp_1);

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("kamitesque:persistence_seal"),
                new InfusionRecipe("KT_PERSISTENCE",
                        new ItemStack (KTItems.persistence_seal),
                        6,
                        new AspectList().add(Aspect.TOOL, 64).add(Aspect.MAGIC, 64),
                        new ItemStack(ItemsTC.pechWand),
                        ingredientIchor(),
                        bookProtect,
                        new ItemStack(ItemsTC.mechanismComplex),
                        bookProtectFire,
                        focusWard,
                        bookProtectBlast,
                        new ItemStack(ItemsTC.mechanismComplex),
                        bookProtectProj
                )
        );

        ItemStack awakened_ichorium_hoe = new ItemStack(KTItems.awakened_ichorium_hoe);

        if (Main.isRebornLoaded()) {
            awakened_ichorium_hoe.setTagInfo("Unbreakable", new NBTTagByte((byte)1));
        }

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("kamitesque:awakened_ichorium_hoe"),
                new InfusionRecipe("KT_ICHORIUMHOE_AWAKENED",
                        awakened_ichorium_hoe,
                        10,
                        new AspectList().add(Aspect.PLANT, 500).add(Aspect.LIFE, 500).add(Aspect.BEAST, 500).add(Aspect.EARTH, 500).add(Aspect.DESIRE, 500),
                        new ItemStack(KTItems.ichorium_hoe),
                        new ItemStack(ItemsTC.primordialPearl),
                        new ItemStack(Items.NETHER_STAR),
                        new ItemStack(ItemsTC.primordialPearl),
                        new ItemStack(Items.NETHER_STAR),
                        new ItemStack(ItemsTC.primordialPearl),
                        new ItemStack(Items.NETHER_STAR),
                        new ItemStack(ItemsTC.primordialPearl),
                        new ItemStack(Items.NETHER_STAR)
                )
        );

        ItemStack rod = null;

        if (Main.isRebornLoaded()) {
            Item rodSilver = Item.getByNameOrId("kami:blessed_silverwood_rod");

            if (rodSilver != null) {
                rod = new ItemStack(rodSilver);
            }

        } else if (Main.isUnofficialLoaded()) {
            rod = new ItemStack(BlocksTC.logSilverwood);
        }

        if (rod != null) {
            ThaumcraftApi.addInfusionCraftingRecipe(
                    new ResourceLocation("kamitesque:dimensional_cutter"),
                    new InfusionRecipe("KT_PORTALCUTTER",
                            new ItemStack(KTItems.dimensional_cutter),
                            8,
                            new AspectList().add(Aspect.FLUX, 250).add(Aspect.MAGIC, 250).add(Aspect.TRAP, 250).add(Aspect.ORDER, 250),
                            new ItemStack(Items.SHEARS),
                            new ItemStack(ItemsTC.causalityCollapser),
                            new ItemStack(ItemsTC.primordialPearl),
                            new ItemStack(ItemsTC.causalityCollapser),
                            "ingotIchorium",
                            new ItemStack(ItemsTC.salisMundus),
                            rod,
                            new ItemStack(ItemsTC.salisMundus),
                            "ingotIchorium"
                    )
            );
        }

        initInfusionEnchantments();

    }

    private static void initInfusionEnchantments() {
        InfusionEnchantmentRecipe IETHOUSANDYARD = new InfusionEnchantmentRecipe(KTEnchants.IETHOUSANDYARD, (new AspectList()).add(Aspect.AVERSION, 120).add(Aspect.MOTION, 120).add(Aspect.ELDRITCH, 120), ingredientIchor(), new ItemStack(BlocksTC.mirror), new ItemStack(Items.CHORUS_FRUIT_POPPED));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("kamitesque:IETHOUSANDYARD"), IETHOUSANDYARD);

        String sword = null;

        if (Main.isUnofficialLoaded()) {
            sword = "thaumictinkerer:ichorium_sword";
        } else if (Main.isRebornLoaded()) {
            sword = "kami:ichorium_sword";
        }

        ItemStack stackSword = GameRegistry.makeItemStack(sword, 0, 1,null);

        if (!stackSword.isEmpty()) {
            ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("kamitesque:IETHOUSANDYARD_FAKE"), new InfusionEnchantmentRecipe(IETHOUSANDYARD, stackSword));

            InfusionEnchantmentRecipe IESHARPEYE = new InfusionEnchantmentRecipe(KTEnchants.IESHARPEYE, (new AspectList()).add(Aspect.SENSES, 120).add(Aspect.ENERGY, 120).add(Aspect.ORDER, 120), ingredientIchor(), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(ItemsTC.goggles));

            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("kamitesque:IESHARPEYE"), IESHARPEYE);
            ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("kamitesque:IESHARPEYE_FAKE"), new InfusionEnchantmentRecipe(IESHARPEYE, stackSword));

            InfusionEnchantmentRecipe IEALLFRONTS = new InfusionEnchantmentRecipe(KTEnchants.IEALLFRONTS, (new AspectList()).add(Aspect.PROTECT, 120).add(Aspect.DEATH, 120).add(Aspect.EXCHANGE, 120), ingredientIchor(), new ItemStack(ItemsTC.modules, 1, 1), new ItemStack(ItemsTC.crimsonBlade));

            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("kamitesque:IEALLFRONTS"), IEALLFRONTS);
            ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("kamitesque:IEALLFRONTS_FAKE"), new InfusionEnchantmentRecipe(IEALLFRONTS, stackSword));
        }
    }

    public static void initCrucible() {

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:ichorflame_nitor"),
                new CrucibleRecipe("KT_ICHORFLAME",
                        new ItemStack(KTBlocks.ichorflame_nitor, 4),
                        ingredientIchor(),
                        new AspectList().merge(Aspect.ALCHEMY, 16).merge(Aspect.AVERSION, 16).merge(Aspect.SENSES, 16))
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
                        new ItemStack(KTItems.pure_shard),
                        new ItemStack(TABlocks.STRANGE_CRYSTAL),
                        new AspectList().merge(Aspect.ENTROPY, 15))
        );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:root_crystal"),
                new CrucibleRecipe("KT_ROOTCRYSTAL",
                        new ItemStack(KTBlocks.root_crystal),
                        new ItemStack(KTItems.root_seed),
                        new AspectList().merge(Aspect.LIFE, 16).merge(Aspect.CRAFT, 16).merge(Aspect.ELDRITCH, 16))
        );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:unstable_phial"),
                new CrucibleRecipe("KT_PORTALCUTTER",
                        new ItemStack(KTItems.unstable_phial, 1, 1),
                        new ItemStack(KTItems.reinforced_phial, 1, 1),
                        new AspectList().merge(Aspect.ENTROPY, 16).merge(Aspect.MOTION, 16).merge(Aspect.FLUX, 16))
        );
        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("kamitesque:unstable_phial-1"),
                new CrucibleRecipe("KT_PORTALCUTTER",
                        new ItemStack(KTItems.unstable_phial, 1, 2),
                        new ItemStack(KTItems.reinforced_phial, 1, 2),
                        new AspectList().merge(Aspect.ENTROPY, 16).merge(Aspect.MOTION, 16).merge(Aspect.FLUX, 16))
        );

    }

    public static void initFurnace() {
        ThaumcraftApi.addSmeltingBonus(new ItemStack (KTItems.crystal_cluster), new ItemStack(KTItems.pure_shard), 0.10F);
    }

    public static void initRest(IForgeRegistry<IRecipe> iForgeRegistry) {
        iForgeRegistry.register(new RecipeAugmentAdd().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentAdd.id)));
        iForgeRegistry.register(new RecipeAugmentRemove().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentRemove.id)));
        iForgeRegistry.register(new RecipeSealPrint().setRegistryName(new ResourceLocation(Main.MODID, RecipeSealPrint.id)));

        GameRegistry.addSmelting(
                KTItems.crystal_cluster,
                new ItemStack(KTItems.pure_shard, 2),
                2
        );
    }

    public static ItemStack ingredientIchor() {

        String ichor = null;
        int meta = 0;

        if (Main.isRebornLoaded()) {
            ichor = "kami:ichor";
        } else if (Main.isUnofficialLoaded()) {
            ichor = "thaumictinkerer:kamiresource";
            meta = 2;
        }
        return ichor != null ? GameRegistry.makeItemStack(ichor, meta, 1,null) : ItemStack.EMPTY;
    }
}
