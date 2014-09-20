package untouchedwagons.minecraft.mcrc2.api.recipes;

import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.UnknownRecipeException;

/**
 * Interface for registering IRecipe handlers, see the MinecraftRecipeSupportService class to see how it's used
 */
public interface IRecipeWrapperFactoryRepository {
    /**
     *
     * @param IRecipe recipe An object implementing the IRecipe interface that you wish to wrap
     * @return RecipeWrapperFactory|null The appropriate wrapper factory that can create a WrappedRecipe for the provided recipe
     * @throws UnknownRecipeException
     */
    public RecipeWrapperFactory get(IRecipe recipe) throws UnknownRecipeException;

    /**
     * Register a wrapper factory for a given recipe class
     * @param Class recipeClass The class descriptor for a class implementing the IRecipe interface
     * @param RecipeWrapperFactory factory The factory to associate with the recipe type
     * @return RecipeWrapperFactory The supplied factory
     */
    public RecipeWrapperFactory put(Class recipeClass, RecipeWrapperFactory factory);
}
