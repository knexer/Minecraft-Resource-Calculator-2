package untouchedwagons.minecraft.mcrc2.api.recipes.filters;

import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

public abstract class RecipeFilter {
    private ILocalizationRegistry registry;

    public abstract Class[] getRecipeWrapperClasses();

    public abstract boolean shouldActivateFilter();

    public abstract boolean shouldFilterRecipe(RecipeWrapper recipe);

    public ILocalizationRegistry getLocalizationRegistry() {
        return registry;
    }

    public void setLocalizationRegistry(ILocalizationRegistry registry)
    {
        this.registry = registry;
    }
}
