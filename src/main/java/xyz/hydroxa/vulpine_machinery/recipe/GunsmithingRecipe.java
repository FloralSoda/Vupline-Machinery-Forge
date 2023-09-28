package xyz.hydroxa.vulpine_machinery.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.item.custom.WeaponItem;

import java.util.HashMap;
import java.util.Map;

public class GunsmithingRecipe implements Recipe<SimpleContainer> {
    private static final String ID = "gunsmithing";
    private final ResourceLocation ResourceID;
    private final ItemStack weaponOutput;
    private final Map<String, Ingredient> recipe;

    private static final String barrelKey = "barrel";
    private static final String coreKey = "core";
    private static final String handleKey = "handle";
    private static final String bridgeKey = "bridge";
    public static final int barrelSlot = 4;
    public static final int coreSlot = 2;
    public static final int bridgeSlot = 0;
    public static final int handleSlot = 1;
    public static final int emptySlot1 = 3;
    public static final int emptySlot2 = 5;

    public GunsmithingRecipe(ResourceLocation id, ItemStack output, Map<String, Ingredient> recipe) {
        this.ResourceID = id;
        this.weaponOutput = output;
        this.recipe = recipe;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide)
            return false;

        return recipe.get(barrelKey).test(pContainer.getItem(barrelSlot)) &&
                recipe.get(coreKey).test(pContainer.getItem(coreSlot)) &&
                recipe.get(bridgeKey).test(pContainer.getItem(bridgeSlot)) &&
                recipe.get(handleKey).test(pContainer.getItem(handleSlot)) &&
                pContainer.getItem(emptySlot1).getCount() == 0 && //Keep until remaining 2 slots have uses
                pContainer.getItem(emptySlot2).getCount() == 0;
                //&& recipe.get(barrelKey).test(pContainer.getItem(1)) //Uncomment for additional pieces
                //&& recipe.get(barrelKey).test(pContainer.getItem(1));
    }

    public boolean canGoInSlot(ItemStack stack, int slot) {
        String key = switch (slot) {
            case barrelSlot -> barrelKey;
            case coreSlot -> coreKey;
            case bridgeSlot -> bridgeKey;
            case handleSlot -> handleKey;
            default -> "no";
        };
        return !key.equals("no") && recipe.get(key).test(stack);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        if (weaponOutput.getItem() instanceof WeaponItem wi ) {
            ItemStack newWeapon = weaponOutput.copy();
            return wi.outfitWith(newWeapon, pContainer.getItem(barrelSlot), pContainer.getItem(coreSlot), pContainer.getItem(bridgeSlot), pContainer.getItem(handleSlot));
        } else {
            return weaponOutput;
        }
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth < 7 && pHeight < 3;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return weaponOutput.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ResourceID;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<GunsmithingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = GunsmithingRecipe.ID;
    }

    public static class Serializer implements RecipeSerializer<GunsmithingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(VulpineMachineryMod.MOD_ID, GunsmithingRecipe.ID);

        @Override
        public @NotNull GunsmithingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonObject ingredients = GsonHelper.getAsJsonObject(pSerializedRecipe, "slots");
            Map<String, Ingredient> recipe = new HashMap<>(6);
            recipe.put(barrelKey, Ingredient.fromJson(ingredients.get(barrelKey)));
            recipe.put(coreKey, Ingredient.fromJson(ingredients.get(coreKey)));
            recipe.put(bridgeKey, Ingredient.fromJson(ingredients.get(bridgeKey)));
            recipe.put(handleKey, Ingredient.fromJson(ingredients.get(handleKey)));
            //recipe.put(barrelKey, Ingredient.fromJson(ingredients.get(barrelKey))); //Additional slots if desired
            //recipe.put(barrelKey, Ingredient.fromJson(ingredients.get(barrelKey)));

            return new GunsmithingRecipe(pRecipeId, output, recipe);
        }

        @Override
        public @Nullable GunsmithingRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            Map<String, Ingredient> recipe = new HashMap<>(6);
            recipe.put(barrelKey, Ingredient.fromNetwork(pBuffer));
            recipe.put(coreKey, Ingredient.fromNetwork(pBuffer));
            recipe.put(bridgeKey, Ingredient.fromNetwork(pBuffer));
            recipe.put(handleKey, Ingredient.fromNetwork(pBuffer));
            //recipe.put(barrelKey, Ingredient.fromNetwork(pBuffer));
            //recipe.put(barrelKey, Ingredient.fromNetwork(pBuffer));

            ItemStack output = pBuffer.readItem();
            return new GunsmithingRecipe(pRecipeId, output, recipe);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, GunsmithingRecipe pRecipe) {
            pRecipe.recipe.get(barrelKey).toNetwork(pBuffer);
            pRecipe.recipe.get(coreKey).toNetwork(pBuffer);
            pRecipe.recipe.get(bridgeKey).toNetwork(pBuffer);
            pRecipe.recipe.get(handleKey).toNetwork(pBuffer);
            //pRecipe.recipe.get(barrelKey).toNetwork(pBuffer);
            //pRecipe.recipe.get(barrelKey).toNetwork(pBuffer);

            pBuffer.writeItem(pRecipe.weaponOutput);
        }
    }
}
