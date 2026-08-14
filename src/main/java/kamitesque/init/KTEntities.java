package kamitesque.init;

import kamitesque.common.entities.EntityItemPersistent;
import kamitesque.common.entities.EntityPortalRift;
import kamitesque.root.Main;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;

public class KTEntities {

    public static List<Entity> ENTITY = new ArrayList<Entity>();

    public static void preInitEntities() {

        int id = 0;

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, EntityItemPersistent.id),
                EntityItemPersistent.class,
                EntityItemPersistent.id,
                id++,
                Main.instance,
                64,
                20,
                true
        );

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, EntityPortalRift.id),
                EntityPortalRift.class,
                EntityPortalRift.id,
                id++,
                Main.instance,
                64,
                20,
                true
        );
    }

}
