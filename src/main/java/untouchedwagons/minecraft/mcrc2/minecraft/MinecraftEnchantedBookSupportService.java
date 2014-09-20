package untouchedwagons.minecraft.mcrc2.minecraft;

import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting.EnchantingRecipe;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.enchanting.EnchantingRecipeWrapper;

import java.util.Iterator;

public class MinecraftEnchantedBookSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private final RecipeWrapper[] recipes;
    private ILocalizationRegistry registry;

    private int pos = 0;

    public MinecraftEnchantedBookSupportService() {
        this.recipes = new RecipeWrapper[1];
    }

    @Override
    public void setLocalizationRegistry(ILocalizationRegistry registry) {
        this.registry = registry;
        this.createRecipes();
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
        this.recipes[0] = new EnchantingRecipeWrapper(new EnchantingRecipe(), this.registry);
    }
}