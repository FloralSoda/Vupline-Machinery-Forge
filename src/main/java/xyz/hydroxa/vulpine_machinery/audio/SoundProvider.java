package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface SoundProvider {
    SoundVolume GetReloadAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundVolume GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundVolume GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundVolume GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon);
}
