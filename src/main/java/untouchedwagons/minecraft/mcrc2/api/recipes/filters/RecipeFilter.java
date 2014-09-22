package untouchedwagons.minecraft.mcrc2.api.recipes.filters;

import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

public abstract class RecipeFilter {
    private ILocalizationRegistry registry;

    public abstract Class[] getRecipeWrapperClasses();

    public abstract boolean shouldFilterRecipe(RecipeWrapper recipe);

    public ILocalizationRegistry getLocalizationRegistry() {
        return registry;
    }

    public void setLocalizationRegistry(ILocalizationRegistry registry)
    {
        this.registry = registry;
    }

    public boolean isConvenienceRecipe(RecipeWrapper recipe,
                                       ItemStack output,
                                       ItemStack input)
    {
        String input_unlocalized_name = registry.getUnlocalizedName(input);
        int input_unlocalized_count = input.stackSize;

        return ItemStack.areItemStacksEqual(recipe.getResult(), output) &&
                recipe.getIngredients().size() == 1 &&
                recipe.getIngredients().containsKey(input_unlocalized_name) &&
                recipe.getIngredients().get(input_unlocalized_name) == input_unlocalized_count;
    }
}
