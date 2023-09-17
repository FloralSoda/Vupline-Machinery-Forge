package xyz.hydroxa.vulpine_machinery.item.custom;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import java.util.UUID;

public class WeaponProperties {
    public boolean CanHipFire;
    public int AimCarrySpeedMultiplier;
    public int HipCarrySpeedMultiplier;
    public float BulletSpeed;
    public Item DefaultBarrel;
    public Item DefaultCore;
    public Item DefaultBridge;
    public Item DefaultHandle;
    public float RecoilInDegrees;

    private final UUID hip_carry_uid = UUID.randomUUID();
    private final UUID aim_carry_uid = UUID.randomUUID();

    public AttributeModifier HipCarryModifier = new AttributeModifier(hip_carry_uid.toString(), 1f, AttributeModifier.Operation.ADDITION);
    public AttributeModifier AimCarryModifier = new AttributeModifier(aim_carry_uid.toString(), 1f, AttributeModifier.Operation.ADDITION);

    public WeaponProperties(Item defaultBarrel, Item defaultCore, Item defaultBridge, Item defaultHandle) {
        CanHipFire = true;
        AimCarrySpeedMultiplier = 0;
        HipCarrySpeedMultiplier = 0;
        BulletSpeed = 6f;
        RecoilInDegrees = 2f;

        DefaultBarrel = defaultBarrel;
        DefaultBridge = defaultBridge;
        DefaultCore = defaultCore;
        DefaultHandle = defaultHandle;
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
    public WeaponProperties carrySpeedMultiplier(float hipCarry, float aimCarry) {
        AimCarryModifier = new AttributeModifier(aim_carry_uid.toString(), aimCarry, AttributeModifier.Operation.MULTIPLY_TOTAL);
        HipCarryModifier = new AttributeModifier(hip_carry_uid.toString(), hipCarry, AttributeModifier.Operation.MULTIPLY_TOTAL);
        return this;
    }
}
