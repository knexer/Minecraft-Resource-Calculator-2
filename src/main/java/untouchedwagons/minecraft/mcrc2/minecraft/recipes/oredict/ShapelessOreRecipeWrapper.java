package untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.List;
import java.util.Map;

public class ShapelessOreRecipeWrapper extends RecipeWrapper {
    public ShapelessOreRecipeWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((ShapelessOreRecipe)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
    }

    @Override
    public void parse() {
        for (Object o : ((ShapelessOreRecipe) this.getRecipe()).getInput().toArray())
        {
            if (o instanceof List)
            {
                addIngredient((List)o);
            }
            else if (o instanceof ItemStack)
            {
                addIngredient((ItemStack)o);
            }
        }
    }
}
