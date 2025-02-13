package xyz.hydroxa.vulpine_machinery.enums;

public enum ItemOrder {
    Weapon(0),
    Blueprints(1),
    Barrels(2),
    Bridges(3),
    Handles(4),
    Cores(5),
    Bullets(6),
    Item(99);
    int priority;

    ItemOrder(int priority) {
        this.priority = priority;
    }

    public int compare(ItemOrder b) {
        return priority - b.priority;
    }
}
