package kamitesque.init;

import kamitesque.client.renderer.entities.RenderItemPersistent;
import kamitesque.common.entities.EntityItemPersistent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class KTRender {

    public static void initEntityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityItemPersistent.class, new RenderItemPersistent(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
    }

}
