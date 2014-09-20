package untouchedwagons.minecraft.mcrc2;

import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapperFactory;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.UnknownRecipeException;

import java.util.HashMap;

/**
 * Created by Jordan on 8/6/2014.
 */
public class RecipeWrapperFactoryRepository extends HashMap<Class, RecipeWrapperFactory> implements IRecipeWrapperFactoryRepository {
    private ILocalizationRegistry registry;

    public RecipeWrapperFactoryRepository(ILocalizationRegistry registry)
    {
        this.registry = registry;
    }

    public RecipeWrapperFactory get(IRecipe recipe) throws UnknownRecipeException {
        if (this.containsKey(recipe.getClass()))
            return this.get(recipe.getClass());
        else
            throw new UnknownRecipeException("");
    }

    public RecipeWrapperFactory put(Class recipeClass, RecipeWrapperFactory factory)
    {
        super.put(recipeClass, factory);

        factory.setRegistry(this.registry);

        return factory;
    }
}
