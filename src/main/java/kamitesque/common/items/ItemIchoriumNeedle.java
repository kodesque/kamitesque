package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.IRarity;
import org.jetbrains.annotations.NotNull;
import thaumcraft.common.lib.SoundsTC;

public class ItemIchoriumNeedle extends ItemKTBase {

    public ItemIchoriumNeedle(String name, String... variants) {
        super(name);
        setMaxDamage(1);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target instanceof EntityPlayer) {
            return false;
        }

        if (target.world.isRemote) {
            for(int i = 0; i < 2; ++i) {
                target.world.spawnParticle(EnumParticleTypes.PORTAL, target.posX + (target.world.rand.nextDouble() - (double)0.5F) * (double)target.width, target.posY + target.world.rand.nextDouble() * (double)target.height - (double)0.25F, target.posZ + (target.world.rand.nextDouble() - (double)0.5F) * (double)target.width, (target.world.rand.nextDouble() - (double)0.5F) * (double)2.0F, -target.world.rand.nextDouble(), (target.world.rand.nextDouble() - (double)0.5F) * (double)2.0F, new int[0]);
            }
        }

        target.setPosition(0, -1000, 0);
        stack.damageItem(2, attacker);

        if (attacker instanceof EntityPlayer) {

            attacker.world.playSound(
                    null,
                    attacker.posX,
                    attacker.posY,
                    attacker.posZ,
                    KTSounds.banish,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            if (!((EntityPlayer) attacker).isCreative()) {
                attacker.world.playSound(
                        null,
                        attacker.posX,
                        attacker.posY,
                        attacker.posZ,
                        SoundsTC.urnbreak,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }

        return true;
    }

    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
