package kamitesque.init;

import kamitesque.client.renderer.entities.RenderItemPersistent;
import kamitesque.client.renderer.entities.RenderPortalRift;
import kamitesque.common.entities.EntityItemPersistent;
import kamitesque.common.entities.EntityPortalRift;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class KTRender {

    public static void initEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityItemPersistent.class, new RenderItemPersistent(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPortalRift.class, new RenderPortalRift(Minecraft.getMinecraft().getRenderManager()));
    }

}
