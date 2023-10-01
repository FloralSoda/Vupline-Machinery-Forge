package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HandCannonSoundProvider implements SoundProvider {
    @Override
    public SoundEvent GetReloadAudio(LivingEntity user, Level level, ItemStack weapon) {
        return ModSoundEvents.RELOAD.get();
    }

    @Override
    public SoundEvent GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon) {
        return ModSoundEvents.HAND_CANNON_NEAR.get();
    }

    @Override
    public SoundEvent GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon) {
        return ModSoundEvents.HAND_CANNON_FAR.get();
    }

    @Override
    public SoundEvent GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon) {
        return ModSoundEvents.EMPTY_FIRE.get();
    }
}
