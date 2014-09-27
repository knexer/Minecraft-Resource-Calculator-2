package untouchedwagons.minecraft.mcrc2.api.recipes.filters;

import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

/**
 * A RecipeFilter allows the resource calculator to ignore a recipe that would
 * be undesirable, for example a Block -> 9 ingots recipe
 */
public abstract class RecipeFilter {
    private ILocalizationRegistry registry;

    /**
     * An array of RecipeWrapper classes that the filter can understand
     * @return java.lang.Class[]
     */
    public abstract Class[] getRecipeWrapperClasses();

    /**
     * Whether or not the filter is usable (Is the mod it is filtering for available?)
     * @return boolean
     */
    public abstract boolean shouldActivateFilter();

    /**
     * Whether or not the recipe should be ignored
     * @param untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper recipe
     * @return boolean
     */
    public abstract boolean shouldFilterRecipe(RecipeWrapper recipe);

    public ILocalizationRegistry getLocalizationRegistry() {
        return registry;
    }

    public void setLocalizationRegistry(ILocalizationRegistry registry)
    {
        this.registry = registry;
    }
}
