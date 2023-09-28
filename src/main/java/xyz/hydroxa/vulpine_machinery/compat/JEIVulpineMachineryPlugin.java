package xyz.hydroxa.vulpine_machinery.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.VulpineMachineryMod;
import xyz.hydroxa.vulpine_machinery.recipe.GunsmithingRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIVulpineMachineryPlugin implements IModPlugin {
    public static RecipeType<GunsmithingRecipe> GUNSMITHING_TYPE =
            new RecipeType<>(GunsmithingRecipeCategory.UID, GunsmithingRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(VulpineMachineryMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                GunsmithingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<GunsmithingRecipe> recipesGunsmithing = rm.getAllRecipesFor(GunsmithingRecipe.Type.INSTANCE);
        registration.addRecipes(GUNSMITHING_TYPE, recipesGunsmithing);
    }
}
