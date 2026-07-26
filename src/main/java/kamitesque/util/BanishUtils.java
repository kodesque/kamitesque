package kamitesque.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class BanishUtils {

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
