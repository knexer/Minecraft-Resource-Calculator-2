package untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * Created by Jordan on 8/5/2014.
 */
public class FurnaceRecipe implements IRecipe {
    private ItemStack input, output;

    public FurnaceRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        return this.output;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    public ItemStack[] getIngredients()
    {
        return new ItemStack[] { this.input };
    }
}
