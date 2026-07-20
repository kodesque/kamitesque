package kamitesque.network.packets;

import io.netty.buffer.ByteBuf;
import kamitesque.client.fx.FXSmokeBurst;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFXSmokeBurst implements IMessage, IMessageHandler<PacketFXSmokeBurst, IMessage> {

    private double x;
    private double y;
    private double z;
    private int color;

    public PacketFXSmokeBurst() {
    }

    public PacketFXSmokeBurst(double x, double y, double z, int color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(color);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        color = buf.readInt();
    }

    @Override
    public IMessage onMessage(PacketFXSmokeBurst message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            World world = Minecraft.getMinecraft().world;

            if (world == null) {
                return;
            }

            for (int i = 0; i < 33; i++) {
                FXSmokeBurst.drawSmokeBurst(
                        message.x,
                        message.y,
                        message.z,
                        message.color,
                        0,
                        world
                );
            }
        });

        return null;
    }
}