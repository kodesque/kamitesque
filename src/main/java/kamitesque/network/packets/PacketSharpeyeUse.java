package kamitesque.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class PacketSharpeyeUse implements IMessage, IMessageHandler<PacketSharpeyeUse, IMessage> {

    public PacketSharpeyeUse() {}

    @Override
    public void fromBytes(ByteBuf byteBuf) {}

    @Override
    public void toBytes(ByteBuf byteBuf) {}

    @Override
    public IMessage onMessage(PacketSharpeyeUse message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;

        player.getServerWorld().addScheduledTask(() -> {

            AxisAlignedBB box = player.getEntityBoundingBox().grow(5);

            List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);

            for (Entity entity : entities) {
                NBTTagCompound nbt = entity.getEntityData();

                if (nbt != null && nbt.hasKey("kamitesque.mark")) {

                    player.attackTargetEntityWithCurrentItem(entity);
                }
            }
        });

        return null;
    }
}
