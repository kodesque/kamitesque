package kamitesque.common.templates;

import kamitesque.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEventKTBase extends SoundEvent {


    public SoundEventKTBase(String name) {
        super(new ResourceLocation(Main.MODID, name));
        this.setRegistryName(Main.MODID, name);
    }

}
