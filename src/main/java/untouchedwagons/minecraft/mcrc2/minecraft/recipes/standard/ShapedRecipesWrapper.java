package untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Map;

public class ShapedRecipesWrapper extends RecipeWrapper
{
    public ShapedRecipesWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((ShapedRecipes)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
    }

    @Override
    public void parse()
    {
        ItemStack[] items = ((ShapedRecipes) this.getRecipe()).recipeItems;

        for (ItemStack itemStack : items)
        {
            if (itemStack == null) {
                continue;
            }

            addIngredient(itemStack);
        }
    }
}
