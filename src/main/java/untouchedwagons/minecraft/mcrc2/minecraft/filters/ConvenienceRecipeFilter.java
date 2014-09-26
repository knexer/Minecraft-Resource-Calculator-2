package untouchedwagons.minecraft.mcrc2.minecraft.filters;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.filters.RecipeFilter;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapedRecipesWrapper;

import java.util.List;
import java.util.regex.Pattern;

public class ConvenienceRecipeFilter extends RecipeFilter {
    public static final Pattern nugget_pattern = Pattern.compile("nugget", Pattern.CASE_INSENSITIVE);
    public static final Pattern ingot_pattern = Pattern.compile("ingot", Pattern.CASE_INSENSITIVE);

    @Override
    public Class[] getRecipeWrapperClasses() {
        return new Class[] { ShapedRecipesWrapper.class};
    }

    @Override
    public boolean shouldActivateFilter() {
        return true;
    }

    @Override
    public boolean shouldFilterRecipe(RecipeWrapper recipe)
    {
        if (this.isBlockToIngotRecipe(recipe))
            return true;

        if (this.isNuggetToIngotRecipe(recipe))
            return true;

        if (this.isSelfReplicatingRecipe(recipe))
            return true;

        return false;
    }

    public boolean isBlockToIngotRecipe(RecipeWrapper recipe)
    {
        if (recipe.getIngredients().size() != 1) return false;

        // All this to get the first and only key in the map...
        Object ingredient = recipe.getIngredients().keySet().toArray()[0];

        if (ingredient instanceof List) return false;

        ItemStack ingredient_stack = (ItemStack) ingredient;
        StackWrapper result_wrapper = recipe.getResult();

        if (!(result_wrapper instanceof ItemStackWrapper))
            return false;

        ItemStack result_stack = (ItemStack)result_wrapper.getUnderlyingStack();

        return (ingredient_stack.getItem() instanceof ItemBlock && ingredient_stack.stackSize == 1 &&
                !(result_stack.getItem() instanceof ItemBlock) && result_stack.stackSize == 9);
    }

    public boolean isNuggetToIngotRecipe(RecipeWrapper recipe)
    {
        if (recipe.getIngredients().size() != 1) return false;

        // All this to get the first and only key in the map...
        Object ingredient = recipe.getIngredients().keySet().toArray()[0];

        if (ingredient instanceof List) return false;

        ItemStack ingredient_stack = (ItemStack) ingredient;
        StackWrapper result_wrapper = recipe.getResult();

        if (!(result_wrapper instanceof ItemStackWrapper))
            return false;

        ItemStack result_stack = (ItemStack)result_wrapper.getUnderlyingStack();

        boolean nugget_ingredient = nugget_pattern.matcher(ingredient_stack.getUnlocalizedName()).find();
        boolean ingot_result = ingot_pattern.matcher(result_stack.getUnlocalizedName()).find();

        return (nugget_ingredient && ingredient_stack.stackSize == 9 &&
                ingot_result && result_stack.stackSize == 1);
    }

    public boolean isSelfReplicatingRecipe(RecipeWrapper recipe)
    {
        return false;
    }
}
