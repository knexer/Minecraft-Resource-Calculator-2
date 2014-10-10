package untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Arrays;
import java.util.Map;

public class ShapelessRecipesWrapper extends RecipeWrapper
{
    public ShapelessRecipesWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((ShapelessRecipes)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
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
