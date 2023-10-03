package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeavySoundProvider implements SoundProvider {
    @Override
    public SoundVolume GetReloadAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.RELOAD.get(), 10f);
    }

    @Override
    public SoundVolume GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.HEAVY_GUNSHOT_NEAR.get(), 64f);
    }

    @Override
    public SoundVolume GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.HEAVY_GUNSHOT_FAR.get(), 128f);
    }

    @Override
    public SoundVolume GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon) {
        return new SoundVolume(ModSoundEvents.EMPTY_FIRE.get(), 10f);
    }
}
