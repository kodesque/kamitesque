package kamitesque.client.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXEntityRunes extends Particle {

        double ofx = (double) 0.0F;
        double ofy = (double) 0.0F;
        float rotation = 0.0F;
        int runeIndex = 0;

        public FXEntityRunes(World world, double d, double d1, double d2, float f1, float f2, float f3, int m) {
            super(world, d, d1, d2, (double) 0.0F, (double) 0.0F, (double) 0.0F);
            if (f1 == 0.0F) {
                f1 = 1.0F;
            }

            this.rotation = (float) (this.rand.nextInt(4) * 90);
            this.particleRed = f1;
            this.particleGreen = f2;
            this.particleBlue = f3;
            this.particleGravity = 0.0F;
            this.motionX = this.motionY = this.motionZ = (double) 0.0F;
            this.particleMaxAge = 3 * m;
            this.setSize(0.01F, 0.01F);
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.runeIndex = (int) (Math.random() * (double) 16.0F + (double) 224.0F);
            this.ofx = (double) this.rand.nextFloat() * 0.2;
            this.ofy = -0.3 + (double) this.rand.nextFloat() * 0.6;
            this.particleScale = (float) ((double) 1.0F + this.rand.nextGaussian() * (double) 0.1F);
            this.particleAlpha = 0.0F;
        }

        public void setScale(float s) {
            this.particleScale = s;
        }

        public void setOffsetX(double f) {
            this.ofx = f;
        }

        public void renderParticle(BufferBuilder wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5) {
            Tessellator.getInstance().draw();
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);
            float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
            float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
            float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
            GL11.glTranslated((double) var13, (double) var14, (double) var15);
            GL11.glRotatef(this.rotation, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslated(this.ofx, this.ofy, 0.0);
            float var8 = (float) (this.runeIndex % 16) / 64.0F;
            float var9 = var8 + 0.015625F;
            float var10 = 0.09375F;
            float var11 = var10 + 0.015625F;
            float var12 = 0.3F * this.particleScale;
            float var16 = 1.0F;
            wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            int i = 240;
            int j = i >> 16 & '\uffff';
            int k = i & '\uffff';
            wr.pos((double) -0.5F * (double) var12, (double) 0.5F * (double) var12, (double) 0.0F).tex((double) var9, (double) var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
            wr.pos((double) 0.5F * (double) var12, (double) 0.5F * (double) var12, (double) 0.0F).tex((double) var9, (double) var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
            wr.pos((double) 0.5F * (double) var12, (double) -0.5F * (double) var12, (double) 0.0F).tex((double) var8, (double) var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
            wr.pos((double) -0.5F * (double) var12, (double) -0.5F * (double) var12, (double) 0.0F).tex((double) var8, (double) var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
            wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        }

        public void onUpdate() {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            float threshold = (float) this.particleMaxAge / 5.0F;
            if ((float) this.particleAge <= threshold) {
                this.particleAlpha = (float) this.particleAge / threshold;
            } else {
                this.particleAlpha = (float) (this.particleMaxAge - this.particleAge) / (float) this.particleMaxAge;
            }

            if (this.particleAge++ >= this.particleMaxAge) {
                this.setExpired();
            }

            this.motionY -= 0.04 * (double) this.particleGravity;
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
        }

        public void setGravity(float value) {
            this.particleGravity = value;
        }
    }

