package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HandCannonSoundProvider implements SoundProvider {
    @Override
    public SoundVolume GetReloadAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.RELOAD.get(), 10f);
    }

    @Override
    public SoundVolume GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.HAND_CANNON_NEAR.get(), 48f);
    }

    @Override
    public SoundVolume GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.HAND_CANNON_FAR.get(), 108f);
    }

    @Override
    public SoundVolume GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.EMPTY_FIRE.get(), 10f);
    }
}
