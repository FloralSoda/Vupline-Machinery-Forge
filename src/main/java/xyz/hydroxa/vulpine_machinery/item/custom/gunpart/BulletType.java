package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

public enum BulletType {
    Unset(false),
    Pistol(false),
    Heavy(false),
    HandCannon(true),
    Muffled(true);

    public final boolean CanBreakShields;
    BulletType(boolean canBreakShields) {
        this.CanBreakShields = canBreakShields;
    }
}
