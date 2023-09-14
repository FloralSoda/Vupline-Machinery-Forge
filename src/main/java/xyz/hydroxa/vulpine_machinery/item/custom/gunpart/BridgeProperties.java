package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

public class BridgeProperties {
    public float BulletSpeedMultiplier;
    public float ReloadSpeedMultiplier;

    public BridgeProperties() {
        BulletSpeedMultiplier = 1;
        ReloadSpeedMultiplier = 1;
    }

    public BridgeProperties bulletSpeedMultiplier(float multiplier) {
        BulletSpeedMultiplier = multiplier;
        return this;
    }
    public BridgeProperties reloadSpeedMultiplier(float multiplier) {
        ReloadSpeedMultiplier = multiplier;
        return this;
    }
}
