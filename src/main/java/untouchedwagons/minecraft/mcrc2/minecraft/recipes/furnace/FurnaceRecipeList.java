package untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.Map;

public class FurnaceRecipeList extends ArrayList<FurnaceRecipe> {
    public FurnaceRecipeList(FurnaceRecipes furnaceRecipes)
    {
        @SuppressWarnings("unchecked")
        Map<ItemStack,ItemStack> recipes = furnaceRecipes.getSmeltingList();
        ItemStack output;

        for(ItemStack input : recipes.keySet())
        {
            output = recipes.get(input);

            this.add(new FurnaceRecipe(input, output));
        }
    }
}
