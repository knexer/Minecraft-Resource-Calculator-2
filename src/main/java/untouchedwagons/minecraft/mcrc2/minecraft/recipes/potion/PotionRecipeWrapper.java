package untouchedwagons.minecraft.mcrc2.minecraft.recipes.potion;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.ItemStackWrapper;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Map;

public class PotionRecipeWrapper extends RecipeWrapper {
    public PotionRecipeWrapper(IRecipe recipe, Map<Item, String> item_id_lookup) {
        super(recipe, item_id_lookup);
    }

    @Override
    public void parse() {
        PotionRecipe recipe = (PotionRecipe) this.getRecipe();

        for (ItemStack input : recipe.inputs) {
            this.addIngredient(input);
        }
    }

    @Override
    public StackWrapper getResult() {
        return new ItemStackWrapper(((PotionRecipe)this.getRecipe()).getRecipeOutput(), this.getItemIdLookupTable());
    }

    @Override
    public boolean usesSpecialMachine() {
        return true;
    }

    @Override
    public String getMachine()
    {
        ItemStack brewing_stand = new ItemStack(Items.brewing_stand);
        return brewing_stand.getDisplayName();
    }
}
