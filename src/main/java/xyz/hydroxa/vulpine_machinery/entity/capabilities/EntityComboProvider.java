package xyz.hydroxa.vulpine_machinery.entity.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityComboProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<EntityCombo> ENTITY_COMBO = CapabilityManager.get(new CapabilityToken<>() {
    });

    private EntityCombo combo = null;
    private final LazyOptional<EntityCombo> optional = LazyOptional.of(this::createEntityCombo);

    private @NotNull EntityCombo createEntityCombo() {
        if (this.combo == null) {
            this.combo = new EntityCombo();
        }
        return this.combo;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ENTITY_COMBO) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createEntityCombo().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createEntityCombo().loadNBTData(nbt);
    }
}