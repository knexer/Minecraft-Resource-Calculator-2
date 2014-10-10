package untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Map;

public class EnchantingRecipeWrapper extends RecipeWrapper {
    public EnchantingRecipeWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
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
        return new ItemStackWrapper(((EnchantingRecipe)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
    }

    @Override
    public boolean usesSpecialMachine() {
        return true;
    }

    /**
     * Template method that concrete classes can override,
     * gets the machine needed to perform this recipe
     * @return java.lang.String
     */
    @Override
    public String getMachine()
    {
        return "Enchanting Table";
    }
}
