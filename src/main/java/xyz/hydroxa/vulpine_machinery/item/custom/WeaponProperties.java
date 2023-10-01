package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import java.util.UUID;

public class WeaponProperties {
    public boolean CanHipFire;
    public boolean Automatic;
    public float BulletSpeed;
    public Item DefaultBarrel;
    public Item DefaultCore;
    public Item DefaultBridge;
    public Item DefaultHandle;
    public float RecoilInDegrees;
    public ResourceLocation Recipe;

    private final UUID hip_carry_uid = UUID.randomUUID();
    private final UUID aim_carry_uid = UUID.randomUUID();

    public AttributeModifier HipCarryModifierAttribute = new AttributeModifier(hip_carry_uid.toString(), 0f, AttributeModifier.Operation.ADDITION);
    public AttributeModifier AimCarryModifierAttribute = new AttributeModifier(aim_carry_uid.toString(), 0f, AttributeModifier.Operation.ADDITION);

    public WeaponProperties(ResourceLocation recipe, Item defaultBarrel, Item defaultCore, Item defaultBridge, Item defaultHandle) {
        CanHipFire = true;
        BulletSpeed = 6f;
        RecoilInDegrees = 2f;
        Automatic = false;

        this.Recipe = recipe;

        DefaultBarrel = defaultBarrel;
        DefaultBridge = defaultBridge;
        DefaultCore = defaultCore;
        DefaultHandle = defaultHandle;
    }

    public WeaponProperties automatic(boolean enabled) {
        Automatic = enabled;
        return this;
    }
    public WeaponProperties canHipFire(boolean enabled) {
        CanHipFire = enabled;
        return this;
    }

    public WeaponProperties bulletSpeed(float speed) {
        BulletSpeed = speed;
        return this;
    }
    public WeaponProperties recoil(float degrees) {
        RecoilInDegrees = degrees;
        return this;
    }

    /**
     * Potency for potion effects when holding the gun. Positive values grant speed, negative values grant slowness. 0 grants nothing
     * @param hipCarry The potency for when the gun is simply being held
     * @param aimCarry The potency for when the gun is being aimed
     * @return Builder pattern.
     */
    public WeaponProperties carrySpeedModifier(float hipCarry, float aimCarry) {
        AimCarryModifierAttribute = new AttributeModifier(aim_carry_uid.toString(), aimCarry, AttributeModifier.Operation.MULTIPLY_TOTAL);
        HipCarryModifierAttribute = new AttributeModifier(hip_carry_uid.toString(), hipCarry, AttributeModifier.Operation.MULTIPLY_TOTAL);
        return this;
    }
}
