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

    public static final RegistryObject<SoundEvent> GUNSHOT_FAR = SOUND_EVENTS.register("gunshot_far",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "gunshot_far")));
    public static final RegistryObject<SoundEvent> GUNSHOT_NEAR = SOUND_EVENTS.register("gunshot_near",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "gunshot_near")));
    public static final RegistryObject<SoundEvent> HEAVY_GUNSHOT_FAR = SOUND_EVENTS.register("heavy_gunshot_far",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "heavy_gunshot_far")));
    public static final RegistryObject<SoundEvent> HEAVY_GUNSHOT_NEAR = SOUND_EVENTS.register("heavy_gunshot_near",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "heavy_gunshot_near")));
    public static final RegistryObject<SoundEvent> HAND_CANNON_FAR = SOUND_EVENTS.register("hand_cannon_far",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "hand_cannon_far")));
    public static final RegistryObject<SoundEvent> HAND_CANNON_NEAR = SOUND_EVENTS.register("hand_cannon_near",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "hand_cannon_near")));
    public static final RegistryObject<SoundEvent> MUFFLED_GUNSHOT = SOUND_EVENTS.register("muffled_gunshot",
            () -> new SoundEvent(new ResourceLocation(VulpineMachineryMod.MOD_ID, "muffled_gunshot")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
