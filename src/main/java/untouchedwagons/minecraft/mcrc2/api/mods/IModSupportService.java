package untouchedwagons.minecraft.mcrc2.api.mods;

import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

/**
 * Main interface that any addons should implement. The implementing class
 * must be able to supply an interator to a collection of RecipeWrappers
 */
public interface IModSupportService extends Iterable<RecipeWrapper> {
    /**
     * Sets the localization registry
     * @param untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry registry
     */
    public void setLocalizationRegistry(ILocalizationRegistry registry);

    /**
     * Sets the RecipeWrapper Factory Repository
     * @param untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository repository
     */
    public void setRecipeWrapperFactoryRepository(IRecipeWrapperFactoryRepository repository);
}
