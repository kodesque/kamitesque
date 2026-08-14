package kamitesque.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.common.lib.utils.EntityUtils;

public class PacketThousandyardUse implements IMessage, IMessageHandler<PacketThousandyardUse, IMessage>  {

    private double minRange;
    private double range;
    private float padding;
    private boolean nonCollide;

    public PacketThousandyardUse() {}

    public PacketThousandyardUse(double minRange, double range, float padding, boolean nonCollide) {

        this.minRange = minRange;
        this.range = range;
        this.padding = padding;
        this.nonCollide = nonCollide;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(minRange);
        buf.writeDouble(range);
        buf.writeFloat(padding);
        buf.writeBoolean(nonCollide);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        minRange = buf.readDouble();
        range = buf.readDouble();
        padding = buf.readFloat();
        nonCollide = buf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketThousandyardUse message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;

        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;

            Entity target = EntityUtils.getPointedEntity(world, player,
                    message.minRange,
                    message.range,
                    message.padding,
                    message.nonCollide);

            if (target instanceof EntityLivingBase) {

                player.attackTargetEntityWithCurrentItem(target);
            }
        });

        return null;
    }

}
