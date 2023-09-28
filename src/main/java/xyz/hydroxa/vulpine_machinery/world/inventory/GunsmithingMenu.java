package xyz.hydroxa.vulpine_machinery.world.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.block.ModBlocks;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.item.custom.BlueprintItem;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;
import xyz.hydroxa.vulpine_machinery.recipe.GunsmithingRecipe;

import javax.annotation.Nullable;

public class GunsmithingMenu extends AbstractContainerMenu {
    protected final int outputSlot = 7;
    protected final int inputSlot = 0;

    protected final ContainerLevelAccess access;
    protected final Player player;

    protected GunsmithingRecipe currentRecipe;
    protected boolean modMode = false;

    protected final ResultContainer resultSlots = new ResultContainer() {
        @Override
        public void setChanged() {
            super.setChanged();
            if (!player.getLevel().isClientSide)
                GunsmithingMenu.this.takeRecipe(this);
        }
    };
    protected ItemStack previousBlueprint = ItemStack.EMPTY;
    protected final SimpleContainer blueprintSlot = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            if (!player.getLevel().isClientSide && getItem(0) != previousBlueprint) {
                previousBlueprint = getItem(0);
                GunsmithingMenu.this.recipeChanged(this);
            }
        }
    };
    protected final SimpleContainer inputSlots = new SimpleContainer(6) {
        public void setChanged() {
            super.setChanged();
            if (!player.getLevel().isClientSide)
                GunsmithingMenu.this.slotsChanged(this);
        }
    };

    public GunsmithingMenu(final int pContainerId, Inventory pPlayerInventory, @Nullable FriendlyByteBuf _b) {
        this(ModMenus.GUNSMITHING.get() ,pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }
    public GunsmithingMenu(final int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        this(ModMenus.GUNSMITHING.get() ,pContainerId, pPlayerInventory, pAccess);
    }
    public GunsmithingMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;

        addSlot(
                new Slot(blueprintSlot, inputSlot, 10, 30) {
                    public boolean mayPlace(@NotNull ItemStack stack) { return stack.is(ModItems.BLUEPRINT.get()) || stack.getItem() instanceof WeaponItem; }
                    public int getMaxStackSize() { return 1; }
                }
        );
        addSlot(
                new Slot(resultSlots, outputSlot, 145, 30) {
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return false;
                    }
                }
        );
        int slotStartX = 40;
        int slotStartY = 15;
        //Add blueprint slots
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 2; ++y) {
                int slot = (2*x) + y;
                addSlot(new Slot(inputSlots, slot, slotStartX + (x * 31), slotStartY + (y * 27)) {
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        if (GunsmithingMenu.this.currentRecipe != null) {
                            return GunsmithingMenu.this.currentRecipe.canGoInSlot(stack, slot);
                        } else {
                            return false;
                        }
                    }
                });
            }
        }

        //Add inventory
        int invStartX = 8;
        int invStartY = 84;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, invStartX + j * 18, invStartY + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, invStartX + k * 18, invStartY + (18 * 3) + 4));
        }
    }

    protected boolean isValidBlock(BlockState pState) {
        return pState.is(ModBlocks.GUNSMITHING_TABLE.get());
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        int startPlayerInv = outputSlot + 1;
        int endPlayerInv = startPlayerInv + 36;

        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();
            if (pIndex == outputSlot) {
                if (!moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex > outputSlot) {
                if (!moveItemStackTo(slotStack, 0, outputSlot, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == slotStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotStack);
        }

        return slotStackCopy;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(@NotNull ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return this.access.evaluate((level, position) ->
                this.isValidBlock(level.getBlockState(position))
                        && pPlayer.distanceToSqr(
                        (double) position.getX() + 0.5D,
                        (double) position.getY() + 0.5D,
                        (double) position.getZ() + 0.5D) <= 64.0D,
                true);
    }

    public void recipeChanged(SimpleContainer pContainer) {
        ItemStack stack = pContainer.getItem(0);

        VulpineMachineryMod.LOGGER.info("Recipe Change");

        resultSlots.clearContent();
        if (stack.getItem() instanceof BlueprintItem) {
            modMode = false;
            var recipe = player.getLevel().getRecipeManager()
                    .byKey(new ResourceLocation(stack.getOrCreateTag().getString(BlueprintItem.TAG_PRINT_ID)));
            recipe.ifPresent(value -> currentRecipe = (GunsmithingRecipe) value);
        } else if (stack.getItem() instanceof WeaponItem wi) {
            modMode = true;

            if (!inputSlots.isEmpty()) {
                access.execute((level, blockPos) -> this.clearContainer(player, inputSlots));
            }

            var recipe = player.getLevel().getRecipeManager()
                    .byKey(wi.Properties.Recipe);
            recipe.ifPresent(value -> currentRecipe = (GunsmithingRecipe) value);

            CompoundTag parts = stack.getOrCreateTag().getCompound(WeaponItem.TAG_PARTS);

            inputSlots.setItem(GunsmithingRecipe.barrelSlot,ItemStack.of(parts.getCompound(WeaponItem.TAG_PARTS_BARREL)));
            inputSlots.setItem(GunsmithingRecipe.coreSlot,ItemStack.of(parts.getCompound(WeaponItem.TAG_PARTS_CORE)));
            inputSlots.setItem(GunsmithingRecipe.bridgeSlot,ItemStack.of(parts.getCompound(WeaponItem.TAG_PARTS_BRIDGE)));
            inputSlots.setItem(GunsmithingRecipe.handleSlot,ItemStack.of(parts.getCompound(WeaponItem.TAG_PARTS_HANDLE)));

            resultSlots.setItem(0, stack.copy());
        } else {
            currentRecipe = null;
            if (modMode) {
                inputSlots.clearContent();
                modMode = false;
            } else if (!inputSlots.isEmpty()) {
                for (int i = 1; i < inputSlots.getContainerSize() + 1; ++i) {
                    quickMoveStack(player, i);
                }
            }
        }
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        super.slotsChanged(pContainer);

        if (currentRecipe != null && currentRecipe.matches((SimpleContainer)pContainer, player.getLevel())) {
            resultSlots.setItem(0, currentRecipe.assemble((SimpleContainer) pContainer));
        } else {
            resultSlots.clearContent();
        }
    }

    public void takeRecipe(ResultContainer pContainer) {
        if (pContainer.isEmpty()) {
            if (modMode) {
                blueprintSlot.clearContent();
                modMode = false;
            }
            inputSlots.clearContent();
        }
    }

    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, blockPos) -> {
            this.clearContainer(pPlayer, inputSlots);
            this.clearContainer(pPlayer, blueprintSlot);
        });
    }
}