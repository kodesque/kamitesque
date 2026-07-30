package kamitesque.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketParticleDust implements IMessage, IMessageHandler<PacketParticleDust, IMessage> {

    private double xCoord;
    private double yCoord;
    private double zCoord;
    private int numberOfParticles;
    private IBlockState state;

    public PacketParticleDust() {}

    public PacketParticleDust(double xCoord, double yCoord, double zCoord,
                              int numberOfParticles,
                              IBlockState state) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.numberOfParticles = numberOfParticles;
        this.state = state;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(xCoord);
        buf.writeDouble(yCoord);
        buf.writeDouble(zCoord);
        buf.writeInt(numberOfParticles);
        buf.writeInt(Block.getStateId(state));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        xCoord = buf.readDouble();
        yCoord = buf.readDouble();
        zCoord = buf.readDouble();
        numberOfParticles = buf.readInt();
        state = Block.getStateById(buf.readInt());
    }

    @Override
    public IMessage onMessage(PacketParticleDust message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            World world = Minecraft.getMinecraft().world;

            if (world == null) {
                return;
            }

            for (int i = 0; i < message.numberOfParticles; i++) {
                world.spawnParticle(
                        EnumParticleTypes.BLOCK_DUST,
                        message.xCoord,
                        message.yCoord,
                        message.zCoord,
                        (world.rand.nextDouble() - 0.5D) * 0.2D,
                        0.2D + world.rand.nextDouble() * 0.3D,
                        (world.rand.nextDouble() - 0.5D) * 0.2D,
                        Block.getStateId(message.state)
                );
            }
        });

        return null;
    }

}
