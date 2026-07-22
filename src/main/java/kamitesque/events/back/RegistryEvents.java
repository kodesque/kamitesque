package kamitesque.events.back;

import kamitesque.init.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryEvents {

    public static void registerEntities() {
        KTEntities.preInitEntities();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        KTBlocks.initBlocks();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        KTItems.initItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

        KTRecipes.initRest(event.getRegistry());

        KTRecipes.initWorkbench();
        KTRecipes.initInfusion();
        KTRecipes.initCrucible();
        KTRecipes.initFurnace();

    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {

        KTSounds.initSounds(event.getRegistry());
    }
}
