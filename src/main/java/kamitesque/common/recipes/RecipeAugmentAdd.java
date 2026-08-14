package kamitesque.common.recipes;

import kamitesque.init.KTItems;
import kamitesque.util.NBTManager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.tools.ItemThaumometer;

public class RecipeAugmentAdd extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static String id = "augment_add";

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        boolean hasThaumometer = false;
        boolean hasAugment = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {

                if (stack.getItem() instanceof ItemThaumometer) {
                    if (hasThaumometer || NBTManager.has(stack, NBTManager.EnumGroups.AUGMENT))
                        return false;
                    hasThaumometer = true;

                } else if (stack.getItem().equals(KTItems.augment_eye)) {
                    if (hasAugment)
                        return false;
                    hasAugment = true;

                } else
                    return false;
            }
        }

        return hasThaumometer && hasAugment;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        ItemStack thaumometer = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemThaumometer) {
                    thaumometer = stack;
                    break;
                }
            }
        }

        return NBTManager.mutatePairs(thaumometer, NBTManager.EnumFunc.APPLY, new NBTManager.ValuePair<>(NBTManager.EnumGroups.AUGMENT, NBTManager.EnumGroups.Augment.MAIN, true));
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if ((stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, NBTManager.EnumGroups.AUGMENT)) || stack.getItem().equals(KTItems.augment_eye)) {
                result.set(i, ItemStack.EMPTY);
            }
        }

        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemsTC.thaumometer);
    }
}
