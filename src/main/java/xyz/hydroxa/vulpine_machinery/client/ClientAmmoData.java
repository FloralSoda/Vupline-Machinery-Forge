package xyz.hydroxa.vulpine_machinery.client;

public class ClientAmmoData {
    private static boolean isVisible = false;
    private static int ammoLevel = 0;
    private static int capacity = 0;
    public static void set_ammo(int level) {ClientAmmoData.ammoLevel = level;}
    public static void set_capacity(int capacity) {ClientAmmoData.capacity = capacity;}
    public static void set_visible(boolean visibility) {ClientAmmoData.isVisible = visibility;}
    public static boolean isVisible() { return isVisible; }
    public static int getAmmoLevel() { return ammoLevel; }
    public static int getCapacity() { return capacity; }
}
