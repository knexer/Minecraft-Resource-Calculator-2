package untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.List;

public class ShapelessOreRecipeWrapper extends RecipeWrapper {
    public ShapelessOreRecipeWrapper(IRecipe recipe, ILocalizationRegistry registry) throws InvalidRecipeException
    {
        super(recipe, registry);

        if (!(recipe instanceof ShapelessOreRecipe))
            throw new InvalidRecipeException("Recipe instance is not of a ShapelessOreRecipe.");
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((ShapelessOreRecipe)this.getRecipe()).getRecipeOutput(), this.getRegistry());
    }

    @Override
    public void parse() {
        for (Object o : ((ShapelessOreRecipe) this.getRecipe()).getInput().toArray())
        {
            if (o instanceof List)
            {
                addIngredient((List)o);
            }
            else if (o instanceof ItemStack)
            {
                addIngredient((ItemStack)o);
            }
        }
    }
}
