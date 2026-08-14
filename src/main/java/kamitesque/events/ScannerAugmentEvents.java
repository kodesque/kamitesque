package kamitesque.events;

import kamitesque.common.items.ItemAidedEye;
import kamitesque.root.Main;
import kamitesque.util.NBTManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;
import thecodex6824.thaumicaugmentation.api.impetus.ImpetusAPI;
import thecodex6824.thaumicaugmentation.common.item.ItemEldritchLockKey;

public class ScannerAugmentEvents {

    public static final int gazeCost = 20;
    public static final int memoryCost = 10;

    public static void onAidedEyeAbilityUse(PlayerInteractEvent.RightClickItem event) {

        if (event.getEntityPlayer().isSneaking()) {
            if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
                if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), NBTManager.EnumGroups.AUGMENT)) {

                    Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 15.0, 0.0f, true);

                    if (target != null && target instanceof EntityLiving) {

                        if (ItemAidedEye.canFindExtract(event.getEntityPlayer(), gazeCost)) {

                            EntityLiving entity = (EntityLiving)target;
                            entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1));

                            event.getEntityPlayer().playSound(
                                    SoundsTC.shock,
                                    2.0F,
                                    1.0F
                            );

                            event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message" + "." + Main.MODID + "." + "gaze")
                                            .setStyle(new Style()
                                                    .setItalic(true)
                                                    .setColor(TextFormatting.DARK_PURPLE)),
                                    true);

                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }


    public static void onAidedEyeMemoryAdd(PlayerInteractEvent.RightClickItem event) {

        if (event.getWorld().isRemote) return;

        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
            if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), NBTManager.EnumGroups.AUGMENT)) {

                Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 9.0, 0.0f, true);

                if (target != null) {
                    if (target instanceof EntityItem) {

                        EntityItem entity = (EntityItem)target;
                        ItemStack stack = entity.getItem();

                        if (stack.getItem() instanceof ItemEldritchLockKey) {

                            if (ItemAidedEye.canFindExtract(event.getEntityPlayer(), memoryCost)) {

                                Integer main = event.getWorld().rand.nextInt(7);
                                Integer sub = event.getWorld().rand.nextInt(3);

                                NBTManager.applySoft(stack,
                                        new NBTManager.ValuePair<>(NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.MAIN, main),
                                        new NBTManager.ValuePair<>(NBTManager.EnumGroups.MEMORY, NBTManager.EnumGroups.Memory.SUB, sub));

                                stack.setTranslatableName(new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "key" + "." + stack.getMetadata()).getFormattedText());

                                entity.setItem(stack);

                                event.getWorld().playSound(
                                        null,
                                        event.getPos(),
                                        SoundsTC.wand,
                                        SoundCategory.BLOCKS,
                                        2.0F,
                                        1.0F
                                );

                                event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message" + "." + Main.MODID + "." + "reveal")
                                                .setStyle(new Style()
                                                        .setItalic(true)
                                                        .setColor(TextFormatting.DARK_PURPLE)),
                                        true);

                                for (int i = 0; i < 4; ++i) {
                                    ImpetusAPI.createImpetusParticles(event.getWorld(), event.getEntityPlayer().getPositionVector().add(0, event.getEntityPlayer().height / 2, 0), new Vec3d(entity.getPosition()));
                                }

                                event.setCanceled(true);
                            }
                        }

                    }


                }
            }
        }
    }
}

