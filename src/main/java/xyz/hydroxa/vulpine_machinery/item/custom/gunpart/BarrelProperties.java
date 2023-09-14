package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

public class BarrelProperties {
    public int Capacity;
    public int BulletsPerShot;
    public int TicksPerShot;
    public int TicksPerBulletReloaded;
    public BulletType BulletType;
    public float Variance;
    public String BarrelName;
    public float DamageMultiplier;
    public float BulletSpeed;

    public BarrelProperties(String barrelName) {
        Capacity = 1;
        BulletsPerShot = 1;
        TicksPerShot = 10;
        TicksPerBulletReloaded = 2;
        this.BulletType = xyz.hydroxa.vulpine_machinery.item.custom.gunpart.BulletType.Pistol;
        Variance = 0;
        BarrelName = barrelName;
        DamageMultiplier = 1;
        BulletSpeed = 3.15F;
    }

    public BarrelProperties capacity(int capacity) {
        this.Capacity = capacity;
        return this;
    }
    public BarrelProperties bulletsPerShot(int count) {
        this.BulletsPerShot = count;
        return this;
    }
    public BarrelProperties ticksPerShot(int ticks) {
        this.TicksPerShot = ticks;
        return this;
    }
    public BarrelProperties ticksPerBulletReloaded(int ticks) {
        this.TicksPerBulletReloaded = ticks;
        return this;
    }
    public BarrelProperties bulletType(BulletType type) {
        this.BulletType = type;
        return this;
    }
    public BarrelProperties variance(float variance) {
        this.Variance = variance;
        return this;
    }
    public BarrelProperties damageMultiplier(float multiplier) {
        this.DamageMultiplier = multiplier;
        return this;
    }
    public BarrelProperties bulletSpeed(float speed) {
        this.BulletSpeed = speed;
        return this;
    }
}
