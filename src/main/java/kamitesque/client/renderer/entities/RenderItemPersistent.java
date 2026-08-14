package kamitesque.client.renderer.entities;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class RenderItemPersistent extends RenderEntityItem {
    public RenderItemPersistent(RenderManager p_i46167_1_, RenderItem p_i46167_2_) {
        super(p_i46167_1_, p_i46167_2_);
    }

    @Override
    public void doRender(EntityItem e, double x, double y, double z, float entityYaw, float partialTicks) {
        Random random = new Random(187L);

        float var11 = MathHelper.sin(
                ((float) e.getAge() + partialTicks) / 10.0F + e.hoverStart
        ) * 0.1F + 0.1F;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + var11 + 0.35F, z);

        int q = !FMLClientHandler.instance()
                .getClient()
                .gameSettings
                .fancyGraphics ? 10 : 30;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderHelper.disableStandardItemLighting();

        float f1 = (float) e.getAge() / 500.0F;
        float f2 = 0.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);

        GlStateManager.pushMatrix();

        for (int i = 0; i < q; ++i) {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(
                    random.nextFloat() * 360.0F + f1 * 360.0F,
                    0.0F, 0.0F, 1.0F
            );

            buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

            float fa = random.nextFloat() * 20.0F + 5.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F;

            fa /= 30.0F / ((float) Math.min(e.getAge(), 10) / 10.0F);
            f4 /= 30.0F / ((float) Math.min(e.getAge(), 10) / 10.0F);

            int r = Color.ORANGE.getRed();
            int g = Color.ORANGE.getGreen();
            int b = Color.ORANGE.getBlue();

            buffer.pos(0.0D, 0.0D, 0.0D)
                    .color(r, g, b, 255)
                    .endVertex();

            buffer.pos(-0.866D * f4, fa, -0.5D * f4)
                    .color(r, g, b, 0)
                    .endVertex();

            buffer.pos(0.866D * f4, fa, -0.5D * f4)
                    .color(r, g, b, 0)
                    .endVertex();

            buffer.pos(0.0D, fa, 1.0D * f4)
                    .color(r, g, b, 0)
                    .endVertex();

            buffer.pos(-0.866D * f4, fa, -0.5D * f4)
                    .color(r, g, b, 0)
                    .endVertex();

            tessellator.draw();
        }

        GlStateManager.popMatrix();

        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();

        RenderHelper.enableStandardItemLighting();

        GlStateManager.popMatrix();

        super.doRender(e, x, y, z, entityYaw, partialTicks);
    }
}
