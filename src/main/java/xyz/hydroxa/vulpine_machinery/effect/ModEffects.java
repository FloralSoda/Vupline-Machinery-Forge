package xyz.hydroxa.vulpine_machinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VulpineMachineryMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
