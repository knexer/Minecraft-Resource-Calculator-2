package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace.FurnaceRecipeList;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace.FurnaceRecipeWrapper;

import java.util.Iterator;
import java.util.Map;

public class MinecraftFurnaceSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private Iterator furnace_iterator;
    private Map<Item, String> item_id_lookup;

    @Override
    public void setItemIdReverseLookup(Map<Item, String> item_id_lookup) {
        this.item_id_lookup = item_id_lookup;
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
        if (this.furnace_iterator == null)
            this.furnace_iterator = new FurnaceRecipeList(FurnaceRecipes.smelting()).iterator();

        return this.furnace_iterator.hasNext();
    }

    @Override
    public RecipeWrapper next() {
        return new FurnaceRecipeWrapper((IRecipe)this.furnace_iterator.next(), this.item_id_lookup);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
