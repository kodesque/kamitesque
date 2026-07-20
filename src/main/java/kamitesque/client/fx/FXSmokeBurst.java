package kamitesque.client.fx;

import net.minecraft.world.World;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSmokeSpiral;

import java.awt.*;

public class FXSmokeBurst {

    public static void drawSmokeBurst(double x, double y, double z, int color, int delay, World world) {
        FXSmokeSpiral fx = new FXSmokeSpiral(
                world,
                x,
                y,
                z,
                1.0F,
                world.rand.nextInt(360),
                (int)Math.floor(y) - 1
        );

        Color c = new Color(color);
        fx.setRBGColorF(
                c.getRed() / 255.0F,
                c.getGreen() / 255.0F,
                c.getBlue() / 255.0F
        );

        ParticleEngine.addEffectWithDelay(world, fx, delay);
    }
}
