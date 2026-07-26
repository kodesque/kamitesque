package kamitesque.events.front;

import kamitesque.init.KTEnchants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.utils.EntityUtils;

import java.util.List;

@Mod.EventBusSubscriber
public class InfusionEnchantmentEvents {

    @SubscribeEvent
    public static void onThousandyardUse(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();

        for (int i = 0; i < EnumInfusionEnchantment.getInfusionEnchantments(stack).size(); i++) {
            if (EnumInfusionEnchantment.getInfusionEnchantments(stack).get(i).equals(KTEnchants.IETHOUSANDYARD)) {
                Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 100.0, 0.0f, false);

                if (target != null && target instanceof EntityLiving) {
                    player.attackTargetEntityWithCurrentItem(target);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSharpeyeUse(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();

        for (int i = 0; i < EnumInfusionEnchantment.getInfusionEnchantments(stack).size(); i++) {
            if (EnumInfusionEnchantment.getInfusionEnchantments(stack).get(i).equals(KTEnchants.IESHARPEYE)) {
                AxisAlignedBB box = player.getEntityBoundingBox().grow(5);

                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

                for (int y = 0; y < entities.size(); y++) {
                    NBTTagCompound nbt = entities.get(y).getEntityData();

                    if (nbt != null && nbt.hasKey("kamitesque.marked")) {
                        player.attackTargetEntityWithCurrentItem(entities.get(y));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSharpeyeApply(LivingDamageEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {return;}

        EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();

        for (int i = 0; i < EnumInfusionEnchantment.getInfusionEnchantments(stack).size(); i++) {
            if (EnumInfusionEnchantment.getInfusionEnchantments(stack).get(i).equals(KTEnchants.IESHARPEYE)) {
                NBTTagCompound nbt = event.getEntityLiving().getEntityData();

                if (!nbt.hasKey("kamitesque.mark")) {
                    nbt.setBoolean("kamitesque.mark", true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSharpeyeUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound nbt = entity.getEntityData();

        if (entity.world.getWorldTime() % 1200 == 0) {
            if (nbt.hasKey("kamitesque.mark")) {
                nbt.removeTag("kamitesque.mark");
            }
        }
    }

    @SubscribeEvent
    public static void onAllfrontsUse(LivingDamageEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {return;}

        EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();

        for (int i = 0; i < EnumInfusionEnchantment.getInfusionEnchantments(stack).size(); i++) {
            if (EnumInfusionEnchantment.getInfusionEnchantments(stack).get(i).equals(KTEnchants.IEALLFRONTS)) {
                AxisAlignedBB box = player.getEntityBoundingBox().grow(2, 0, 2);

                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

                for (int y = 0; y < entities.size(); y++) {

                    player.attackTargetEntityWithCurrentItem(entities.get(y));
                }
            }
        }
    }

    @SubscribeEvent
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

            for (int x = 0; x < EnumInfusionEnchantment.getInfusionEnchantments(stack).size(); x++) {
                if (EnumInfusionEnchantment.getInfusionEnchantments(stack).get(x).equals(KTEnchants.IEALLFRONTS) && !player.getCooldownTracker().hasCooldown(stack.getItem())) {
                    AxisAlignedBB box = player.getEntityBoundingBox().grow(2, 0, 2);

                    List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

                    for (int y = 0; y < entities.size(); y++) {

                        float damage = (float) stack
                                .getAttributeModifiers(EntityEquipmentSlot.MAINHAND)
                                .get(SharedMonsterAttributes.ATTACK_DAMAGE.getName())
                                .stream()
                                .mapToDouble(AttributeModifier::getAmount)
                                .sum();

                        if (entities.get(y) instanceof IMob) {
                            entities.get(y).attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                            player.getCooldownTracker().setCooldown(stack.getItem(), 1200);
                            return;
                        }
                    }
                }
            }
        }
    }

}
