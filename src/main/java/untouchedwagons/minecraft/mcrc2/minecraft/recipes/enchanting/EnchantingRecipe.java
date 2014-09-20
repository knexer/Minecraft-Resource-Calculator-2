package untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EnchantingRecipe implements IRecipe {
    public final ItemStack[] inputs;

    public EnchantingRecipe() {
        this.inputs = new ItemStack[1];
        this.inputs[0] = new ItemStack(Items.book);
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
        return new ItemStack(Items.enchanted_book);
    }
}
