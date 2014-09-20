package untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;

import java.util.Arrays;

public class ShapelessRecipesWrapper extends RecipeWrapper
{
    public ShapelessRecipesWrapper(IRecipe recipe, ILocalizationRegistry registry) throws InvalidRecipeException
    {
        super(recipe, registry);

        if (!(recipe instanceof ShapelessRecipes))
            throw new InvalidRecipeException("Recipe instance is not of a ShapelessRecipes.");
    }

    @Override
    public ItemStack getResult() {
        return ((ShapelessRecipes)this.getRecipe()).getRecipeOutput();
    }

    @Override
    public void parse()
    {
        // It seems Notch never head of "program to an interface, not an implementation"
        Object[] temp = ((ShapelessRecipes) this.getRecipe()).recipeItems.toArray();
        ItemStack[] items = Arrays.copyOf(temp, temp.length, ItemStack[].class);

        for (ItemStack itemStack : items)
        {
            if (itemStack == null) continue;

            addIngredient(itemStack);
        }
    }
}
