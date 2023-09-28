package xyz.hydroxa.vulpine_machinery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.world.inventory.GunsmithingMenu;

public class GunsmithingTableBlock extends CraftingTableBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.vulpine_machinery.upgrade");

    public GunsmithingTableBlock(Properties pProperties) {
        super(pProperties);
    }

    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return new SimpleMenuProvider((id, inventory, _d) ->
                new GunsmithingMenu(id, inventory, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }

    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            NetworkHooks.openScreen((ServerPlayer) pPlayer, pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }
}
