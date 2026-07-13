package kamitesque.init;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.internal.CommonInternals;

public class KTResearch {

    public static void initResearch() {

        registerResearchLocation(new ResourceLocation("kamitesque:research/zenithappend"));
        registerResearchLocation(new ResourceLocation("kamitesque:research/augmentationappend"));

    }

    public static void registerResearchLocation(ResourceLocation loc) {
        if (!CommonInternals.jsonLocs.containsKey(loc.toString())) {
            CommonInternals.jsonLocs.put(loc.toString(), loc);
        }
    }


}
