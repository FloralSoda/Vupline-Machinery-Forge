package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.logging.Level;

public interface SoundProvider {
    SoundEvent GetReloadAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundEvent GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundEvent GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon);
    SoundEvent GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon);
}
