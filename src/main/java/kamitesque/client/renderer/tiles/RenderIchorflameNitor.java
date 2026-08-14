package kamitesque.client.renderer.tiles;

import kamitesque.common.tiles.TileIchorflameNitor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import thaumcraft.api.items.ItemsTC;

public class RenderIchorflameNitor extends TileEntitySpecialRenderer<TileIchorflameNitor>  {

    @Override
    public void render(TileIchorflameNitor te, double x, double y, double z,
                       float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);

        this.renderPearl(partialTicks);

        GlStateManager.popMatrix();
    }

    private void renderPearl(float partialTicks) {

        GlStateManager.pushMatrix();

        float time = Minecraft.getMinecraft().world.getTotalWorldTime() + partialTicks;

        float bob = MathHelper.sin(time / 10.0F) * 0.1F + 0.1F;

        GlStateManager.translate(0.5F, 0.5F + bob, 0.5F);

        GlStateManager.rotate(time * 4.0F, 0.0F, 1.0F, 0.0F);

        Minecraft.getMinecraft()
                .getRenderItem()
                .renderItem(new ItemStack(ItemsTC.primordialPearl),
                        ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.popMatrix();
    }
}
