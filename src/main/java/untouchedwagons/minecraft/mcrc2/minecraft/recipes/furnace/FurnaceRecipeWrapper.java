package untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;

/**
 * Created by Jordan on 8/5/2014.
 */
public class FurnaceRecipeWrapper extends RecipeWrapper {
    public FurnaceRecipeWrapper(IRecipe recipe, ILocalizationRegistry registry) throws InvalidRecipeException {
        super(recipe, registry);

        if (!(recipe instanceof FurnaceRecipe))
            throw new InvalidRecipeException("Recipe instance is not of a FurnaceRecipe.");
    }
    @Override
    public void parse() {
        ItemStack[] items = ((FurnaceRecipe)this.getRecipe()).getIngredients();

        for (ItemStack is : items)
        {
            addIngredient(is);
        }
    }

    @Override
    public ItemStack getResult() {
        return ((FurnaceRecipe)this.getRecipe()).getRecipeOutput();
    }

    public String getMachine()
    {
        return "furnace";
    }
}
