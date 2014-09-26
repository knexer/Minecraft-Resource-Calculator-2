package untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

public class EnchantingRecipeWrapper extends RecipeWrapper {
    /**
     * @param IRecipe recipe
     * @param ILocalizationRegistry registry
     */
    public EnchantingRecipeWrapper(IRecipe recipe, ILocalizationRegistry registry) {
        super(recipe, registry);
    }

    @Override
    public void parse() {
        EnchantingRecipe recipe = (EnchantingRecipe) this.getRecipe();

        for (ItemStack is : recipe.inputs)
        {
            addIngredient(is);
        }
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((EnchantingRecipe)this.getRecipe()).getRecipeOutput(), this.getRegistry());
    }

    /**
     * Template method that concrete classes can override,
     * gets the machine needed to perform this recipe
     * @return java.lang.String
     */
    @Override
    public String getMachine()
    {
        return "enchanting-table";
    }
}
