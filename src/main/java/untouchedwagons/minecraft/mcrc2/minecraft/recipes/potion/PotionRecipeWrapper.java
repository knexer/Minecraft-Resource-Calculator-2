package untouchedwagons.minecraft.mcrc2.minecraft.recipes.potion;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

public class PotionRecipeWrapper extends RecipeWrapper {
    /**
     * @param IRecipe recipe
     * @param ILocalizationRegistry registry
     */
    public PotionRecipeWrapper(IRecipe recipe, ILocalizationRegistry registry) {
        super(recipe, registry);
    }

    @Override
    public void parse() {
        PotionRecipe recipe = (PotionRecipe) this.getRecipe();

        for (ItemStack input : recipe.inputs)
            this.addIngredient(input);
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((PotionRecipe)this.getRecipe()).getRecipeOutput(), this.getRegistry());
    }

    @Override
    public String getMachine()
    {
        return "brewing-stand";
    }
}
