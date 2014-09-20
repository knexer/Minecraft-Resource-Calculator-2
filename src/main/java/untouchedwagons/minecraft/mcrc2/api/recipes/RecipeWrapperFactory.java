package untouchedwagons.minecraft.mcrc2.api.recipes;

import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;

/**
 * Several mods add custom recipes that implement the IRecipe interface. Since the dumper cannot natively
 * understand them, support services can extend this class so that the dumper can have the IRecipe object
 * wrapped with something it can understand (the RecipeWrapper class).
 */
public abstract class RecipeWrapperFactory {

    private ILocalizationRegistry registry;

    /**
     * Get the localization registry
     * @return untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry
     */
    public ILocalizationRegistry getRegistry()
    {
        return this.registry;
    }

    /**
     *
     * @param untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry registry
     */
    public final void setRegistry(ILocalizationRegistry registry)
    {
        this.registry = registry;
    }

    /**
     * Create a new untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper
     * @param net.minecraft.item.crafting.IRecipe recipe
     * @return untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper
     * @throws untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException
     */
    public abstract RecipeWrapper createWrapper(IRecipe recipe) throws InvalidRecipeException;
}
