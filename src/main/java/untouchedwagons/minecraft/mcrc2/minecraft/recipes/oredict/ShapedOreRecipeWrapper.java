package untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;

import java.util.List;

public class ShapedOreRecipeWrapper extends RecipeWrapper
{
    public ShapedOreRecipeWrapper(IRecipe recipe, ILocalizationRegistry registry) throws InvalidRecipeException
    {
        super(recipe, registry);
        
        if (!(recipe instanceof ShapedOreRecipe))
            throw new InvalidRecipeException("Recipe instance is not of a ShapedOreRecipe.");
    }

    @Override
    public ItemStack getResult() {
        return ((ShapedOreRecipe)this.getRecipe()).getRecipeOutput();
    }

    @Override
    public void parse() {
        for (Object o : ((ShapedOreRecipe) this.getRecipe()).getInput())
        {
            if (o instanceof List)
            {
                addIngredient((List)o);
            }
            else if (o instanceof ItemStack)
            {
                addIngredient((ItemStack) o);
            }
        }
    }
}
