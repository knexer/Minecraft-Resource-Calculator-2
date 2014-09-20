package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace.FurnaceRecipeList;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.furnace.FurnaceRecipeWrapper;

import java.util.Iterator;

/**
 * Created by Jordan on 8/9/2014.
 */
public class MinecraftFurnaceSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private Iterator furnace_iterator;
    private ILocalizationRegistry registry;

    @Override
    public void setLocalizationRegistry(ILocalizationRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void setRecipeWrapperFactoryRepository(IRecipeWrapperFactoryRepository repository) {

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
        RecipeWrapper wrapper;
        try {
            wrapper = new FurnaceRecipeWrapper((IRecipe)this.furnace_iterator.next(), this.registry);
        } catch (InvalidRecipeException e) {
            return null;
        }

        return wrapper;
    }

    @Override
    public void remove() {

    }
}