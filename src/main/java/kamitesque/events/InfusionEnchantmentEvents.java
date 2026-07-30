package kamitesque.events;

import kamitesque.init.KTEnchants;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.packets.PacketSharpeyeUse;
import kamitesque.network.packets.PacketThousandyardUse;
import kamitesque.util.IECache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

import java.util.List;

public class InfusionEnchantmentEvents {

    public static void onAllfrontsUse(LivingDamageEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {return;}

        EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();

        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, KTEnchants.IEALLFRONTS) > 0) {

            if (!IECache.remoteDamageList.contains(player.getUniqueID())) {
                IECache.remoteDamageList.add(player.getUniqueID());

                AxisAlignedBB box = player.getEntityBoundingBox().grow(2, 0, 2);

                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

                for (Entity entity : entities) {
                    if (entity instanceof EntityLivingBase) {
                        player.attackTargetEntityWithCurrentItem(entity);
                    }
                }
            }
        }
    }

    public static void onAllfrontsCacheTick(TickEvent.WorldTickEvent event) {

//        if (event.world.isRemote) {return;}

        if (event.world.getWorldTime() % 20 == 0 && !IECache.remoteDamageList.isEmpty()) {
            IECache.remoteDamageList.clear();
        }

    }

    public static void onAllfrontsSelfUse(TickEvent.PlayerTickEvent event) {

        EntityPlayer player = event.player;

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (player.world.isRemote) {
            return;
        }

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

            ItemStack stack = player.inventory.getStackInSlot(i);

            if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, KTEnchants.IEALLFRONTS) > 0 && !player.getCooldownTracker().hasCooldown(stack.getItem())) {
                AxisAlignedBB box = player.getEntityBoundingBox().grow(2, 0, 2);

                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

                for (Entity entity : entities) {

                    float damage = (float) stack
                            .getAttributeModifiers(EntityEquipmentSlot.MAINHAND)
                            .get(SharedMonsterAttributes.ATTACK_DAMAGE.getName())
                            .stream()
                            .mapToDouble(AttributeModifier::getAmount)
                            .sum();

                    if (entity instanceof IMob) {
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                        player.getCooldownTracker().setCooldown(stack.getItem(), 1200);
                        return;
                    }
                }
            }
        }
    }

    public static void onSharpeyeUse(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();

        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, KTEnchants.IESHARPEYE) > 0) {

            KTNetwork.INSTANCE.sendToServer(new PacketSharpeyeUse());
        }
    }

    public static void onSharpeyeApply(AttackEntityEvent event) {

        if (event.getEntityPlayer().world.isRemote) {return;}

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();

        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, KTEnchants.IESHARPEYE) > 0) {
            NBTTagCompound nbt = event.getTarget().getEntityData();

            if (!nbt.hasKey("kamitesque.mark")) {
                nbt.setBoolean("kamitesque.mark", true);
            }
        }
    }

    public static void onSharpeyeUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound nbt = entity.getEntityData();

        if (entity.world.getWorldTime() % 1200 == 0) {
            entity.getEntityData().removeTag("kamitesque.mark");
        }
    }

    public static void onThousandyardUse(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();

        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(stack, KTEnchants.IETHOUSANDYARD) > 0) {

            KTNetwork.INSTANCE.sendToServer(new PacketThousandyardUse(1.0, 100.0, 0.0f, false));

        }
    }

}
