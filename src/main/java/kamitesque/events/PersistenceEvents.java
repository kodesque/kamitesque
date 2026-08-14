package kamitesque.events;

import kamitesque.common.entities.EntityItemPersistent;
import kamitesque.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.common.lib.potions.PotionSunScorned;

public class PersistenceEvents {

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

        replacement.motionY = old.motionY;
        replacement.motionX = old.motionX;
        replacement.motionZ = old.motionZ;

        event.setCanceled(true);

        old.world.spawnEntity(replacement);
    }

    public static void onPersistentHoldMob(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack = entity.getHeldItemMainhand();

        if (entity.world.isRemote) {
            return;
        }

        if (!(entity instanceof EntityPlayer)) {
            if (stack.getTagCompound() != null) {
                if (stack.getTagCompound().hasKey("kamitesque.persistent")) {

                    entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    entity.entityDropItem(stack, 0);
                }
            }
        }
    }

    public static void onPersistentHoldPlayer(TickEvent.PlayerTickEvent event) {

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

                    PersistenceEvents.punish(player);
                    break;
                }
            }
        }
    }
}
