package kamitesque.client.fx;

import net.minecraft.world.World;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.client.fx.particles.FXSmokeSpiral;

import java.awt.*;

public class FXDispatcherInternal {

    public static void blockRunes(double x, double y, double z, float r, float g, float b, int dur, float grav, World world) {
        FXEntityRunes fb = new FXEntityRunes(world, x + (double)0.5F, y + (double)0.5F, z + (double)0.5F, r, g, b, dur);
        fb.setGravity(grav);
        ParticleEngine.addEffect(world, fb);
    }

    public static void drawNitorFlames(double x, double y, double z, double x2, double y2, double z2, Color c, int a, World world) {
        FXGeneric fb = new FXGeneric(world, x, y, z, x2, y2, z2);
        fb.setMaxAge(10 + world.rand.nextInt(5));
        fb.setRBGColorF((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F);
        fb.setAlphaF(0.66F);
        fb.setLoop(true);
        fb.setGridSize(64);
        fb.setParticles(264, 8, 1);
        fb.setScale(new float[]{3.0F * 5 + world.rand.nextFloat(), 0.05F * 5});
        fb.setRandomMovementScale(0.0025F, 0.0F, 0.0025F);
        ParticleEngine.addEffectWithDelay(world, fb, a);
    }

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
