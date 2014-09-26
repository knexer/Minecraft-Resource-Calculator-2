package untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

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
    public StackWrapper getResult() {
        return new ItemStackWrapper(((FurnaceRecipe)this.getRecipe()).getRecipeOutput(), this.getRegistry());
    }

    public String getMachine()
    {
        return "furnace";
    }
}
