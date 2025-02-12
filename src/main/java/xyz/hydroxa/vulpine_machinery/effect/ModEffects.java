package xyz.hydroxa.vulpine_machinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VulpineMachineryMod.MOD_ID);

    public static final RegistryObject<MobEffect> FREEZE
            = MOB_EFFECTS.register("freeze", () ->
            new FreezeStatusEffect(MobEffectCategory.HARMFUL, 0x88FFFF).addAttributeModifier(Attributes.MOVEMENT_SPEED, "add358d1-ef78-4332-9adc-50f4630c6c4b", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> SLIMY
            = MOB_EFFECTS.register("slimy", () ->
            new BlankStatusEffect(MobEffectCategory.NEUTRAL, 0x66FF11));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
