package kamitesque.util;

import kamitesque.root.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.common.lib.potions.PotionSunScorned;

public class PersistentUtils {

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
}
