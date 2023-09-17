package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MuffledSoundProvider implements SoundProvider {
    @Override
    public SoundEvent GetReloadAudio(LivingEntity user, Level level, ItemStack weapon) {
        return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
    }

    @Override
    public SoundEvent GetGunshotNearAudio(LivingEntity user, Level level, ItemStack weapon) {
        return ModSoundEvents.MUFFLED_GUNSHOT.get();
    }

    @Override
    public SoundEvent GetGunshotFarAudio(LivingEntity user, Level level, ItemStack weapon) {
        return null;
    }

    @Override
    public SoundEvent GetEmptyFireAudio(LivingEntity user, Level level, ItemStack weapon) {
        return SoundEvents.TRIPWIRE_CLICK_OFF;
    }
}
