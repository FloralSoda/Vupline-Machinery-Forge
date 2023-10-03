package xyz.hydroxa.vulpine_machinery.audio;

import net.minecraft.sounds.SoundEvent;

public class SoundVolume {
    private final float volume;
    private final SoundEvent sound;
    public SoundVolume(SoundEvent sound, float radius) {
        this.sound = sound;
        this.volume = radius / 16f;
    }

    public SoundEvent getSound() { return sound; }
    public float getVolume() { return volume; }
    public float getRadius() { return volume * 16f; }
}
