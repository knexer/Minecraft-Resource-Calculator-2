package untouchedwagons.minecraft.mcrc2.minecraft.recipes.potion;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PotionRecipe implements IRecipe {
    private final ItemStack result;

    public final ItemStack[] inputs;

    public PotionRecipe(int result_damage_id, int ingredient_damage_id, ItemStack altering_ingredient)
    {
        this.result = new ItemStack(Items.potionitem, 3, result_damage_id);

        this.inputs = new ItemStack[2];
        this.inputs[0] = new ItemStack(Items.potionitem, 3, ingredient_damage_id);
        this.inputs[1] = altering_ingredient;
    }

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }
}
