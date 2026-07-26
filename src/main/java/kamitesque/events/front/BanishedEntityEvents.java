package kamitesque.events.front;

import kamitesque.init.KTSounds;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.packets.PacketParticleDust;
import kamitesque.util.BanishUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.common.lib.SoundsTC;

@Mod.EventBusSubscriber
public class BanishedEntityEvents {

    @SubscribeEvent
    public static void updateBanish(LivingEvent.LivingUpdateEvent event) {

        Entity entity = event.getEntity();
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager renderManager = mc.getRenderManager();
        Render render = renderManager.getEntityRenderObject(entity);

        if (BanishUtils.isBanished(entity)) {

            if (entity.isEntityInsideOpaqueBlock()) {

                entity.world.playSound (
                        null,
                        entity.posX,
                        entity.posY,
                        entity.posZ,
                        KTSounds.banish,
                        SoundCategory.HOSTILE,
                        1.0F,
                        1.0F
                );

                entity.setPosition(0, -1000, 0);
                entity.setDead();
            }

            float timer = BanishUtils.getTimer(entity);

            if (timer < entity.getEntityBoundingBox().maxY) {

                timer += 0.001F;

                entity.motionX = 0;
                entity.motionY = 0;
                entity.motionZ = 0;

                entity.setPosition(entity.posX, entity.posY - timer, entity.posZ);

                BanishUtils.updateTimer(entity, timer);

            }

            if (entity.world.getWorldTime() % 20 == 0) {

                entity.world.playSound (
                        null,
                        entity.posX,
                        entity.posY,
                        entity.posZ,
                        SoundsTC.tentacle,
                        SoundCategory.HOSTILE,
                        1.0F,
                        1.0F
                );

                KTNetwork.INSTANCE.sendToAllAround(new PacketParticleDust(
                                entity.posX,
                                BanishUtils.getParticlePos(entity),
                                entity.posZ,
                                60,
                                Blocks.DIRT.getDefaultState()),
                        new NetworkRegistry.TargetPoint(
                                entity.dimension,
                                entity.posX,
                                entity.posY,
                                entity.posZ,
                                64
                        ));

            }
        }
    }

}
