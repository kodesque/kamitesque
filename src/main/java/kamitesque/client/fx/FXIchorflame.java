package kamitesque.client.fx;

import net.minecraft.world.World;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;

import java.awt.*;

public class FXIchorflame {

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

}
