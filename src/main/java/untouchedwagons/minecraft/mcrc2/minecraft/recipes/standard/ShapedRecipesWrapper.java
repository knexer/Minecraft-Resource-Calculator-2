package untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;


public class ShapedRecipesWrapper extends RecipeWrapper
{
    public ShapedRecipesWrapper(IRecipe recipe, ILocalizationRegistry registry) throws InvalidRecipeException {
        super(recipe, registry);

        if (!(recipe instanceof ShapedRecipes))
            throw new InvalidRecipeException("Recipe instance is not of a ShapedRecipes.");
    }

    @Override
    public ItemStack getResult() {
        return ((ShapedRecipes)this.getRecipe()).getRecipeOutput();
    }

    @Override
    public void parse()
    {
        ItemStack[] items = ((ShapedRecipes) this.getRecipe()).recipeItems;

        for (ItemStack itemStack : items)
        {
            if (itemStack == null) continue;

            addIngredient(itemStack);
        }
    }
}
