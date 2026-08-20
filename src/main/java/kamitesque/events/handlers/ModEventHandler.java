package kamitesque.events.handlers;

import kamitesque.events.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEventHandler {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        PersistenceEvents.replacePersistent(event);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        PersistenceEvents.onPersistentHoldMob(event);

        BanishedEntityEvents.onBanishUpdate(event);

        InfusionEnchantmentEvents.onSharpeyeUpdate(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PersistenceEvents.onPersistentHoldPlayer(event);

        InfusionEnchantmentEvents.onAllfrontsSelfUse(event);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        InfusionEnchantmentEvents.onAllfrontsCacheTick(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event) {
        TooltipEvents.onPersistentTooltipRender(event);
        TooltipEvents.onAugmentTooltipRender(event);
        TooltipEvents.onMemoryTooltipRender(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderTooltip(RenderTooltipEvent.PostText event) {
        TooltipEvents.onPersistentOverlayRender(event);
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        InfusionEnchantmentEvents.onThousandyardUse(event);
        InfusionEnchantmentEvents.onSharpeyeUse(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ScannerAugmentEvents.onAidedEyeAbilityUse(event);
        ScannerAugmentEvents.onAidedEyeMemoryAdd(event);
    }

    @SubscribeEvent
    public static void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        AwakenedHoeEvents.onAwakenedHoeHarvest(event);
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        InfusionEnchantmentEvents.onSharpeyeApply(event);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        InfusionEnchantmentEvents.onAllfrontsUse(event);
    }

}
