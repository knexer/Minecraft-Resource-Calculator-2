package untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Map;

public class FurnaceRecipeWrapper extends RecipeWrapper {
    public FurnaceRecipeWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
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
        return new ItemStackWrapper(((FurnaceRecipe)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
    }

    @Override
    public boolean usesSpecialMachine() {
        return true;
    }

    public String getMachine()
    {
        ItemStack furnace = new ItemStack(Blocks.furnace);
        return furnace.getDisplayName();
    }
}
