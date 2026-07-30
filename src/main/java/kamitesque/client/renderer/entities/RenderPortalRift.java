package kamitesque.client.renderer.entities;

import com.sasmaster.glelwjgl.java.CoreGLE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.ender.ShaderCallback;
import thaumcraft.client.lib.ender.ShaderHelper;
import thaumcraft.client.renderers.entity.RenderFluxRift;
import thaumcraft.common.entities.EntityFluxRift;
import thaumcraft.common.lib.utils.EntityUtils;

public class RenderPortalRift extends RenderFluxRift {
    private final ShaderCallback shaderCallback;
    private static final ResourceLocation portalTexture = new ResourceLocation("textures/blocks/portal.png");
    CoreGLE gle = new CoreGLE();

    public RenderPortalRift(RenderManager rm, ShaderCallback shaderCallback) {
        super(rm);
        this.shadowSize = 0.0F;
        this.shaderCallback = new ShaderCallback() {
            public void call(int shader) {
                Minecraft mc = Minecraft.getMinecraft();
                int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
                ARBShaderObjects.glUniform1fARB(x, (float)((double)(mc.player.rotationYaw * 2.0F) * Math.PI / (double)360.0F));
                int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
                ARBShaderObjects.glUniform1fARB(z, -((float)((double)(mc.player.rotationPitch * 2.0F) * Math.PI / (double)360.0F)));
            }
        };
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float pt) {
        EntityFluxRift rift = (EntityFluxRift)entity;
        boolean goggles = EntityUtils.hasGoggles(Minecraft.getMinecraft().player);
        GL11.glPushMatrix();
        this.bindTexture(portalTexture);
        ShaderHelper.useShader(ShaderHelper.endShader, this.shaderCallback);
        float amp = 1.0F;
        float stab = MathHelper.clamp(1.0F - rift.getRiftStability() / 50.0F, 0.0F, 1.5F);
        GL11.glEnable(3042);

        for(int q = 0; q <= 3; ++q) {
            if (q < 3) {
                GlStateManager.depthMask(false);
                if (q == 0 && goggles) {
                    GL11.glDisable(2929);
                }
            }

            GL11.glBlendFunc(770, q < 3 ? 1 : 771);
            if (rift.points.size() > 2) {
                GL11.glPushMatrix();
                double[][] pp = new double[rift.points.size()][3];
                float[][] colours = new float[rift.points.size()][4];
                double[] radii = new double[rift.points.size()];

                for(int a = 0; a < rift.points.size(); ++a) {
                    float var = (float)rift.ticksExisted + pt;
                    if (a > rift.points.size() / 2) {
                        var -= (float)(a * 10);
                    } else if (a < rift.points.size() / 2) {
                        var += (float)(a * 10);
                    }

                    pp[a][0] = ((Vec3d)rift.points.get(a)).x + x + Math.sin((double)(var / 50.0F * amp)) * (double)0.1F * (double)stab;
                    pp[a][1] = ((Vec3d)rift.points.get(a)).y + y + Math.sin((double)(var / 60.0F * amp)) * (double)0.1F * (double)stab;
                    pp[a][2] = ((Vec3d)rift.points.get(a)).z + z + Math.sin((double)(var / 70.0F * amp)) * (double)0.1F * (double)stab;
                    colours[a][0] = 1.0F;
                    colours[a][1] = 1.0F;
                    colours[a][2] = 1.0F;
                    colours[a][3] = 1.0F;
                    double w = (double)1.0F - Math.sin((double)(var / 8.0F * amp)) * (double)0.1F * (double)stab;
                    radii[a] = (double)(Float)rift.pointsWidth.get(a) * w * (double)(q < 3 ? 1.25F + 0.5F * (float)q : 1.0F);
                }

                this.gle.set_POLYCYL_TESS(6);
                this.gle.gleSetJoinStyle(1026);
                this.gle.glePolyCone(pp.length, pp, colours, radii, 1.0F, 0.0F);
                GL11.glPopMatrix();
            }

            if (q < 3) {
                GlStateManager.depthMask(true);
                if (q == 0 && goggles) {
                    GL11.glEnable(2929);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
