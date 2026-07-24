package kamitesque.init;

import kamitesque.common.templates.SoundEventKTBase;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class KTSounds {

    public static SoundEvent banish;
    public static SoundEvent blazing;

    public static SoundEvent stamping;
    public static SoundEvent persistence;

    public static void initSounds(IForgeRegistry<SoundEvent> iForgeRegistry) {

        iForgeRegistry.register(banish = new SoundEventKTBase("banish"));
        iForgeRegistry.register(blazing = new SoundEventKTBase("blazing"));

        iForgeRegistry.register(stamping = new SoundEventKTBase("stamping"));
        iForgeRegistry.register(persistence = new SoundEventKTBase("persistence"));

    }
}
