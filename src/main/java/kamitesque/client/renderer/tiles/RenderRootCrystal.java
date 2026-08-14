package kamitesque.client.renderer.tiles;

import kamitesque.common.tiles.TileRootCrystal;
import kamitesque.init.KTBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.common.blocks.IBlockFacing;

public class RenderRootCrystal extends TileEntitySpecialRenderer<TileRootCrystal> {

    @Override
    public void render(TileRootCrystal te, double x, double y, double z,
                       float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        float m = Math.min(1.0F, te.progress / 2400.0F);

        GlStateManager.translate(x, y , z);


        switch (te.getWorld().getBlockState(te.getPos()).getValue(IBlockFacing.FACING)) {

            case UP:
                GlStateManager.translate(
                        0.5F,
                        0.5F * m,
                        0.5F
                );
                break;

            case DOWN:
                GlStateManager.translate(
                        0.5F,
                        1.0F - 0.5F * m,
                        0.5F
                );
                break;

            case NORTH:
                GlStateManager.translate(
                        0.5F,
                        0.5F,
                        1.0F - 0.5F * m
                );
                break;

            case SOUTH:
                GlStateManager.translate(
                        0.5F,
                        0.5F,
                        0.5F * m
                );
                break;

            case WEST:
                GlStateManager.translate(
                        1.0F - 0.5F * m,
                        0.5F,
                        0.5F
                );
                break;

            case EAST:
                GlStateManager.translate(
                        0.5F * m,
                        0.5F,
                        0.5F
                );
                break;
        }


        GlStateManager.scale(m, m, m);

        IBlockState state = KTBlocks.bedrock_raw.getDefaultState();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        Minecraft.getMinecraft()
                .getTextureManager()
                .bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderItem.renderItem(new ItemStack(Items.DIAMOND), model);

        GlStateManager.popMatrix();
    }

}
