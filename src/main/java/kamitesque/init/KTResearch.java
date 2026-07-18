package kamitesque.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thecodex6824.thaumicaugmentation.api.TABlocks;
import thecodex6824.thaumicaugmentation.api.TAItems;
import thecodex6824.thaumicaugmentation.api.block.property.IAltarBlock;
import thecodex6824.thaumicaugmentation.api.block.property.IObeliskType;

public class KTResearch {

    public static void initResearch() {

        registerResearchLocation(new ResourceLocation("kamitesque:research/zenith"));
        registerResearchLocation(new ResourceLocation("kamitesque:research/thaumicaugmentation"));
        registerResearchLocation(new ResourceLocation("kamitesque:research/alchemy"));

    }

    public static void initScans() {

        ScanningManager.addScannableThing(new ScanBlockState("!EYES", TABlocks.CAPSTONE.getDefaultState().withProperty(IObeliskType.OBELISK_TYPE, IObeliskType.ObeliskType.ELDRITCH).withProperty(
                IAltarBlock.ALTAR, true), true));
        ScanningManager.addScannableThing(new ScanBlockState("!PORTAL", Blocks.END_PORTAL.getDefaultState(), true));

        ScanningManager.addScannableThing(new ScanItem("!KEY", new ItemStack(TAItems.ELDRITCH_LOCK_KEY)));

    }

    public static void registerResearchLocation(ResourceLocation loc) {
        if (!CommonInternals.jsonLocs.containsKey(loc.toString())) {
            CommonInternals.jsonLocs.put(loc.toString(), loc);
        }
    }


}
