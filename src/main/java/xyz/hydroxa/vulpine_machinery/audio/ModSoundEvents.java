package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VulpineMachineryMod.MOD_ID);

    public static final RegistryObject<SoundEvent> GUNSHOT_FAR = registerSoundEvent("gunshot_far");
    public static final RegistryObject<SoundEvent> GUNSHOT_NEAR = registerSoundEvent("gunshot_near");
    public static final RegistryObject<SoundEvent> HEAVY_GUNSHOT_FAR = registerSoundEvent("heavy_gunshot_far");
    public static final RegistryObject<SoundEvent> HEAVY_GUNSHOT_NEAR = registerSoundEvent("heavy_gunshot_near");
    public static final RegistryObject<SoundEvent> HAND_CANNON_FAR = registerSoundEvent("hand_cannon_far");
    public static final RegistryObject<SoundEvent> HAND_CANNON_NEAR = registerSoundEvent("hand_cannon_near");
    public static final RegistryObject<SoundEvent> MUFFLED_GUNSHOT = registerSoundEvent("muffled_gunshot");
    public static final RegistryObject<SoundEvent> EMPTY_FIRE = registerSoundEvent("empty_fire");
    public static final RegistryObject<SoundEvent> RELOAD = registerSoundEvent("reload");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
