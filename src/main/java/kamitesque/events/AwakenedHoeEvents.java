package kamitesque.events;

import kamitesque.common.items.ItemAwakenedHoe;
import kamitesque.init.KTItems;
import kamitesque.util.HoeCache;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class AwakenedHoeEvents {

    public static void onAwakenedHoeHarvest(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if (player == null) return;

        if (player.world.isRemote) return;

        ItemStack stack = player.getHeldItemMainhand();
        Block block = event.getState().getBlock();

        List<ItemStack> drops = event.getDrops();
        int fortune = event.getFortuneLevel();

        if (stack.getItem().equals(KTItems.awakened_ichorium_hoe)) {
            ItemAwakenedHoe.EnumConversionMode current = ItemAwakenedHoe.EnumConversionMode.getMode(stack);

            if (current.equals(ItemAwakenedHoe.EnumConversionMode.GALVANIZE)) {
                    for (int i = 0; i < HoeCache.galvanize_drops.size(); i++) {
                            if (block.equals(HoeCache.galvanize_drops.get(i).block)) {

                                HoeCache.DataBundle bundle = HoeCache.galvanize_drops.get(i);

                                if (player.world.rand.nextFloat() < bundle.chance) {

                                    int amount = player.world.rand.nextInt(bundle.minAmount, bundle.maxAmount);

                                    drops.clear();
                                    drops.add(new ItemStack(bundle.item, amount));
                                    drops.add(HoeCache.getSpecialDrop(block, fortune, player.world.rand));

                                    return;
                                }
                            }
                        }
                }
        }
    }

}
