package xyz.hydroxa.vulpine_machinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RedstoneStatusEffect extends MobEffect {
    protected RedstoneStatusEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);

        BlockState onBlock = pLivingEntity.getBlockStateOn();
        onBlock.setValue(BlockStateProperties.POWERED, true);
        pLivingEntity.level.setBlockAndUpdate(pLivingEntity.blockPosition(), onBlock);
    }
}
