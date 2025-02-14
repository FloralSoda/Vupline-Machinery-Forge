package xyz.hydroxa.vulpine_machinery.entity.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;

@AutoRegisterCapability
public class EntityCombo {
    /// This is additive. You start at 0.5x, then go to 0.75x, 1.0x etc.
    public static final float COMBO_MULTIPLIER = 0.25f;
    public static final float BASE_COMBO_MULTIPLIER = 0.5f;
    private final String COMBO_NBT_TAG = "vulpine_combo";
    private final String HIT_NBT_TAG = "vulpine_combo_entity_track";

    private int combo;
    private boolean has_hit_entity;
    private final int MAX_COMBO = 10;

    public int getCombo() {
        return combo;
    }
    public boolean hasHitEntity() { return has_hit_entity; }
    public void addCombo(int add) {
        this.combo = Math.min(combo + add, MAX_COMBO);
        VulpineMachineryMod.LOGGER.debug("Kill! Combo: {}", combo);
    }
    public void resetCombo() {
        this.combo = 0;
        VulpineMachineryMod.LOGGER.debug("Miss! Combo: {}", combo);
    }
    public float getComboMult() {
        VulpineMachineryMod.LOGGER.debug("Hit! Combo: {}", combo);
        return BASE_COMBO_MULTIPLIER + (combo * COMBO_MULTIPLIER);
    }
    public void setHasHitEntity(boolean has_hit_entity) { this.has_hit_entity = has_hit_entity; }

    public void copyFrom(EntityCombo source) {
        this.combo = source.combo;
        this.has_hit_entity = false;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt(COMBO_NBT_TAG, combo);
        nbt.putBoolean(HIT_NBT_TAG, has_hit_entity);
    }
    public void loadNBTData(CompoundTag nbt) {
        this.combo = nbt.getInt(COMBO_NBT_TAG);
        this.has_hit_entity = nbt.getBoolean(HIT_NBT_TAG);
    }
}