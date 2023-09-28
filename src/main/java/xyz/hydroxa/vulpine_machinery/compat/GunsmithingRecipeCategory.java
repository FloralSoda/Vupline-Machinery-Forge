package xyz.hydroxa.vulpine_machinery.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.block.ModBlocks;
import xyz.hydroxa.vulpine_machinery.item.ModItems;
import xyz.hydroxa.vulpine_machinery.item.custom.BlueprintItem;
import xyz.hydroxa.vulpine_machinery.recipe.GunsmithingRecipe;

public class GunsmithingRecipeCategory implements IRecipeCategory<GunsmithingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(VulpineMachineryMod.MOD_ID, "gunsmithing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(VulpineMachineryMod.MOD_ID, "textures/gui/jei/gunsmithing.png");

    private final IDrawable background;
    private final IDrawable icon;

    public GunsmithingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 72);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.GUNSMITHING_TABLE.get()));
    }

    @Override
    public @NotNull RecipeType<GunsmithingRecipe> getRecipeType() {
        return JEIVulpineMachineryPlugin.GUNSMITHING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.vulpine_machinery.gunsmithing");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GunsmithingRecipe recipe, @NotNull IFocusGroup focuses) {
        ItemStack blueprint = new ItemStack(ModItems.BLUEPRINT.get());
        blueprint.getOrCreateTag().putString(BlueprintItem.TAG_PRINT_ID, recipe.getId().toString());
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 7).addItemStack(blueprint);

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 18).addIngredients(recipe.getBridge());
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 18).addIngredients(recipe.getCore());
        builder.addSlot(RecipeIngredientRole.INPUT, 94, 18).addIngredients(recipe.getBarrel());
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 38).addIngredients(recipe.getHandle());
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 38);
        builder.addSlot(RecipeIngredientRole.INPUT, 94, 38);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 138, 28).addItemStack(recipe.getResultItem());
    }
}
