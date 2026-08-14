package kamitesque.events;

import kamitesque.init.KTItems;
import kamitesque.root.Main;
import kamitesque.util.NBTManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thecodex6824.thaumicaugmentation.common.item.ItemEldritchLockKey;

import java.util.List;

public class TooltipEvents {

    public static void onPersistentTooltipRender(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null || !nbt.getBoolean("kamitesque.persistent")) {
            return;
        }

        event.getToolTip().add(1,
                new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "persistent")
                        .setStyle(new Style()
                                .setColor(TextFormatting.GOLD))
                        .getFormattedText());

        if (!event.getToolTip().get(2).isEmpty()) {
            event.getToolTip().add(2, "");
        }


    }

    public static void onAugmentTooltipRender(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, NBTManager.EnumGroups.AUGMENT)) {
            tips.add(1,
                    new TextComponentString((new ItemStack(KTItems.augment_eye)).getDisplayName())
                            .setStyle(new Style()
                                    .setColor(TextFormatting.DARK_PURPLE)
                                    .setItalic(true))
                            .getFormattedText());
        }
    }


    public static void onMemoryTooltipRender(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemEldritchLockKey) {

            if (NBTManager.has(stack, NBTManager.EnumGroups.MEMORY)) {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + NBTManager.get(stack, NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.MAIN) + "." + NBTManager.get(stack, NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.SUB)).getFormattedText());
                tips.remove(2);
            }
        } else if (stack.getItem().equals(KTItems.seal_printed)) {
            if (NBTManager.has(stack, NBTManager.EnumGroups.MEMORY)) {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + NBTManager.get(stack, NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.MAIN) + "." + NBTManager.get(stack, NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.SUB)).getFormattedText());
            } else {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + "null").getFormattedText());
            }
        }
    }

    public static void onPersistentOverlayRender(RenderTooltipEvent.PostText event) {

        ItemStack stack = event.getStack();

        NBTTagCompound tag = stack.getTagCompound();

        if (tag == null || !tag.getBoolean("kamitesque.persistent")) {
            return;
        }

        ItemStack icon = new ItemStack(KTItems.debug, 1, 1);

        Minecraft mc = Minecraft.getMinecraft();
        RenderItem renderItem = mc.getRenderItem();

        int x = event.getX();
        int y = event.getY();

        int itemX = x + 55;
        int itemY = y + 8;

        renderItem.renderItemAndEffectIntoGUI(
                icon,
                itemX,
                itemY
        );

        renderItem.renderItemOverlayIntoGUI(
                mc.fontRenderer,
                icon,
                itemX,
                itemY,
                null
        );
    }
}
