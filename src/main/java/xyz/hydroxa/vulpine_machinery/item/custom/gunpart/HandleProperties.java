package xyz.hydroxa.vulpine_machinery.item.custom.gunpart;

public class HandleProperties {
    public float RecoilMultiplier;
    public float AccuracyMultiplier;

    public HandleProperties() {
        RecoilMultiplier = 1;
        AccuracyMultiplier = 1;
    }

    public HandleProperties recoilMultiplier(float multiplier) {
        RecoilMultiplier = multiplier;
        return this;
    }
    public HandleProperties accuracyMultiplier(float multiplier) {
        AccuracyMultiplier = multiplier;
        return this;
    }
}
