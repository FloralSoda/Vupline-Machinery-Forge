package xyz.hydroxa.vulpine_machinery.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.hydroxa.vulpine_machinery.effect.ModEffects;

@Mixin(Block.class)
public class SlimeCoreApplicatorMixin {
    @Inject(method = "stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, @NotNull Entity pEntity, CallbackInfo ci) {
        if (pEntity instanceof LivingEntity le && le.hasEffect(ModEffects.SLIMY.get())) {
            double d0 = Math.abs(pEntity.getDeltaMovement().y);
            if (d0 < 0.1D && !pEntity.isSteppingCarefully()) {
                double d1 = 0.4D + d0 * 0.2D;
                pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(d1, 1.0D, d1));
            }
        }
    }

    @Inject(method = "updateEntityAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void updateEntityAfterFallOn(BlockGetter pLevel, @NotNull Entity pEntity, CallbackInfo ci) {
        if (pEntity instanceof LivingEntity le && le.hasEffect(ModEffects.SLIMY.get()) && !pEntity.getBlockStateOn().is(Blocks.SLIME_BLOCK) && !pEntity.isSuppressingBounce()) {
            Vec3 vec3 = pEntity.getDeltaMovement();
            if (vec3.y < 0.0D) {
                pEntity.setDeltaMovement(vec3.x, -vec3.y, vec3.z);

                ci.cancel();
            }
        }
    }
}
