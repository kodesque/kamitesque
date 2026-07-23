package kamitesque.events.front;

import kamitesque.common.entities.EntityItemPersistent;
import kamitesque.init.KTItems;
import kamitesque.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.potions.PotionSunScorned;

@Mod.EventBusSubscriber
public class PersistentItemEvents {

        @SubscribeEvent
        public static void replacePersistent(EntityJoinWorldEvent event) {

            if (!(event.getEntity() instanceof EntityItem)) return;
            if (event.getEntity() instanceof EntityItemPersistent) return;

            EntityItem old = (EntityItem) event.getEntity();

            ItemStack stack = old.getItem();

            if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("kamitesque.persistent")) {
                return;
            }

            EntityItemPersistent replacement =
                    new EntityItemPersistent(
                            old.world,
                            old.posX,
                            old.posY,
                            old.posZ,
                            old.getItem()
                            );

            replacement.motionY = (double)0.1F;
            replacement.motionX = (double)0.0F;
            replacement.motionZ = (double)0.0F;

            replacement.setDefaultPickupDelay();

            event.setCanceled(true);

            old.world.spawnEntity(replacement);
        }


    @SubscribeEvent
    public static void updatePersistent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack = entity.getHeldItemMainhand();

        if (entity.world.isRemote) {
            return;
        }

        if (!(entity instanceof EntityPlayer)) {
        if (stack.getTagCompound() != null) {
            if (stack.getTagCompound().hasKey("kamitesque.persistent")) {

                entity.entityDropItem(stack, 0);
            }
        }
            }
    }

    @SubscribeEvent
    public static void holdPersistent(TickEvent.PlayerTickEvent event) {

        EntityPlayer player = event.player;

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (player.world.isRemote) {
            return;
        }

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("kamitesque.persistent")) {
                    if (!stack.getTagCompound().getUniqueId("kamitesque.persistent.owner").equals(player.getUniqueID())) {

                        player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        player.entityDropItem(stack, 0);

                        punish(player);
                    }
                }
            }
    }

    public static void punish(EntityPlayer player) {

        if (player.isPotionActive(PotionSunScorned.instance) && player.isPotionActive(MobEffects.BLINDNESS) && player.isPotionActive(MobEffects.NAUSEA)) {
            return;
        }

            player.sendStatusMessage(new TextComponentTranslation("message" + "." + Main.MODID + "." + "persistent")
                            .setStyle(new Style()
                                    .setItalic(true)
                                    .setColor(TextFormatting.DARK_PURPLE)),
                    false);

            player.setFire(5);
            player.addPotionEffect(new PotionEffect(PotionSunScorned.instance, 100));
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));

    }

    @SubscribeEvent
    public static void renderAugmentTooltip (ItemTooltipEvent event) {
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


    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderPersistenceSealTooltip(RenderTooltipEvent.PostText event) {

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
