package kamitesque.events.handlers;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DebugHandler {

    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {

        System.out.println(event.getEntityPlayer().getHeldItemMainhand().getTagCompound());

    }

}
