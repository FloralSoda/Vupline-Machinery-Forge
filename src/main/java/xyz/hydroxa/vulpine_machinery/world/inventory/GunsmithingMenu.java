package xyz.hydroxa.vulpine_machinery.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.hydroxa.vulpine_machinery.block.ModBlocks;

import javax.annotation.Nullable;

//public class GunsmithingMenu extends AbstractContainerMenu {
//    protected final ContainerLevelAccess access;
//
//    protected final ResultContainer resultSlots = new ResultContainer();
//    protected final Container inputSlots = new SimpleContainer(2) {
//        /**
//         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
//         * it hasn't changed and skip it.
//         */
//        public void setChanged() {
//            super.setChanged();
//            GunsmithingMenu.this.slotsChanged(this);
//        }
//    };
//
//    public GunsmithingMenu(int pContainerId, Inventory pPlayerInventory) {
//        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
//    }
//    public GunsmithingMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
//        super(pType, pContainerId);
//
//    }
//
//    protected boolean isValidBlock(BlockState pState) {
//        return pState.is(ModBlocks.GUNSMITHING_TABLE.get());
//    }
//
//    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
//        return this.recipes.stream().anyMatch((p_40261_) -> {
//            return p_40261_.isAdditionIngredient(pStack);
//        });
//    }
//
//    @Override
//    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
//        return null;
//    }
//
//    /**
//     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
//     * null for the initial slot that was double-clicked.
//     */
//    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
//        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
//    }
//
//    @Override
//    public boolean stillValid(Player pPlayer) {
//        return this.access.evaluate((p_39785_, p_39786_) -> {
//            return !this.isValidBlock(p_39785_.getBlockState(p_39786_)) ? false : pPlayer.distanceToSqr((double)p_39786_.getX() + 0.5D, (double)p_39786_.getY() + 0.5D, (double)p_39786_.getZ() + 0.5D) <= 64.0D;
//        }, true);
//    }
//}