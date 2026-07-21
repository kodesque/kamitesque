package kamitesque.common.tiles;

import kamitesque.client.fx.FXIchorflame;
import kamitesque.init.KTSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.tiles.misc.TileNitor;

import java.awt.*;

public class TileIchorflameNitor extends TileNitor {

    public static final String id = "ichorflame_nitor";

    int count = 0;

    public TileIchorflameNitor() {};

    public void update() {

        this.count++;

        if (this.world.isRemote) {

            float hue = (count % 360) / 360.0F;
            Color c = Color.getHSBColor(hue, 1.0F, 1.0F);

            IBlockState state = this.world.getBlockState(this.getPos());
            for (int i = 0; i < 2; i++){
                FXIchorflame.drawNitorFlames(
                        this.pos.getX() + 0.5 + this.world.rand.nextGaussian() * 0.125,
                        this.pos.getY() + 0.45 + this.world.rand.nextGaussian() * 0.125,
                        this.pos.getZ() + 0.5 + this.world.rand.nextGaussian() * 0.125,

                        this.world.rand.nextGaussian() * 0.0125,
                        this.world.rand.nextFloat() * 0.30,
                        this.world.rand.nextGaussian() * 0.0125,

                        c,
                        0,
                        this.world);
            }

            if (this.count % 8 == 0) {
                for (int i = 0; i < this.world.rand.nextInt(5); i ++) {
                    double x = this.pos.getX() + 0.5;
                    double y = this.pos.getY() + 0.5;
                    double z = this.pos.getZ() + 0.5;

                    double radius = 0.7;

                    double dx = this.world.rand.nextGaussian();
                    double dy = this.world.rand.nextGaussian();
                    double dz = this.world.rand.nextGaussian();

                    double len = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    dx /= len;
                    dy /= len;
                    dz /= len;

                    FXDispatcher.INSTANCE.arcLightning(
                            x,
                            y,
                            z,
                            x + dx * radius * 2,
                            y + dy * radius,
                            z + dz * radius * 2,
                            1.0F,
                            0.4F,
                            1.0F,
                            0.15F
                    );
                }
            }
        }

        if (this.count % 60 == 0) {
            this.world.playSound(
                    null,
                    this.getPos().getX(),
                    this.getPos().getY(),
                    this.getPos().getZ(),
                    KTSounds.blazing,
                    SoundCategory.BLOCKS,
                    1.0F,
                    1.0F
            );
        }

        if (this.count % 80 == 0) {
            this.world.playSound(
                    null,
                    this.getPos().getX(),
                    this.getPos().getY(),
                    this.getPos().getZ(),
                    SoundsTC.zap,
                    SoundCategory.BLOCKS,
                    0.2F,
                    1.0F
            );
        }
    }
}
