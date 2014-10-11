package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting.EnchantingRecipe;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting.EnchantingRecipeWrapper;

import java.util.Iterator;
import java.util.Map;

public class MinecraftEnchantedBookSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private final RecipeWrapper[] recipes;

    private int pos = 0;
    private Map<Item, String> item_id_lookup;

    public MinecraftEnchantedBookSupportService() {
        this.recipes = new RecipeWrapper[1];
    }

    @Override
    public void setItemIdReverseLookup(Map<Item, String> item_id_lookup) {
        this.item_id_lookup = item_id_lookup;

        this.createRecipes();
    }

    @Override
    public void setRecipeWrapperRepository(Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers) {

    }

    @Override
    public boolean shouldActivateService() {
        return true;
    }

    @Override
    public Iterator<RecipeWrapper> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return pos < recipes.length;
    }

    @Override
    public RecipeWrapper next() {
        return this.recipes[pos++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void createRecipes()
    {
        this.recipes[0] = new EnchantingRecipeWrapper(new EnchantingRecipe(), this.item_id_lookup);
    }
}
