package kamitesque.common.items;

import kamitesque.common.templates.ItemKTBase;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.packets.PacketFXSmokeBurst;
import kamitesque.util.BanishUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.common.lib.SoundsTC;

public class ItemIchoriumNeedle extends ItemKTBase implements IWarpingGear {

    public ItemIchoriumNeedle(String name, String... variants) {
        super(name);
        this.setMaxStackSize(1);
        setMaxDamage(1);
    }

    public boolean hitEntity(@NotNull ItemStack stack, @NotNull EntityLivingBase target, EntityLivingBase attacker) {

        if (!attacker.world.isRemote) {

            if (target instanceof EntityPlayer || BanishUtils.isBanished(target)) {
                return false;
            }

            for (int i = 0; i < 10; i++) {
                KTNetwork.INSTANCE.sendToAllAround(
                        new PacketFXSmokeBurst(
                                target.posX,
                                target.getEntityBoundingBox().minY + target.height * 0.5,
                                target.posZ,
                                0x1c1c1c
                        ),
                        new NetworkRegistry.TargetPoint(
                                attacker.dimension,
                                target.posX,
                                target.posY,
                                target.posZ,
                                64
                        ));
            }

            banish(target);
            stack.damageItem(2, attacker);

            if (attacker instanceof EntityPlayer) {

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
        }

        return true;
    }

    public static void banish(Entity entity) {
        ((EntityLiving)entity).setNoAI(true);
        entity.setEntityInvulnerable(true);
        entity.getEntityData().setBoolean("kamitesque.banished", true);
        entity.getEntityData().setDouble("kamitesque.banished.particlepos", entity.getEntityBoundingBox().minY);

        if (!entity.onGround) {
            World world = entity.getEntityWorld();

            BlockPos pos = entity.getPosition();

            BlockPos refPos = new BlockPos(pos.getX(), 0, pos.getY());

            BlockPos keyPos = world.getTopSolidOrLiquidBlock(refPos);

            entity.setPosition(keyPos.getX(), keyPos.getY(), keyPos.getZ());
        }
    }

    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }

    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 1;
    }
}
