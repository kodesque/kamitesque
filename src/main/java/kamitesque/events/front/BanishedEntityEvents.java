package kamitesque.events.front;

import kamitesque.init.KTSounds;
import kamitesque.network.packets.KTNetwork;
import kamitesque.network.packets.PacketParticleDust;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
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

        if (isBanished(entity)) {

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

            float timer = getTimer(entity);

            if (timer < entity.getEntityBoundingBox().maxY) {

                timer += 0.001F;

                entity.motionX = 0;
                entity.motionY = 0;
                entity.motionZ = 0;

                entity.setPosition(entity.posX, entity.posY - timer, entity.posZ);

                updateTimer(entity, timer);

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
                                getParticlePos(entity),
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

    public static boolean isBanished(Entity entity) {
        NBTTagCompound nbt = entity.getEntityData();

        return nbt != null && nbt.hasKey("kamitesque.banished");
    }

    public static float getTimer(Entity entity) {
        NBTTagCompound nbt = entity.getEntityData();

        return (nbt != null && nbt.hasKey("kamitesque.banished.timer")) ? nbt.getFloat("kamitesque.banished.timer") : 0;
    }

    public static double getParticlePos(Entity entity) {
        NBTTagCompound nbt = entity.getEntityData();

        return (nbt != null && nbt.hasKey("kamitesque.banished.particlepos")) ? nbt.getFloat("kamitesque.banished.particlepos") : 0;
    }

    public static void updateTimer(Entity entity, float timer) {
        NBTTagCompound nbt = entity.getEntityData();

        if (nbt != null) {
            nbt.setFloat("kamitesque.banished.timer", timer);
        }
    }

}
