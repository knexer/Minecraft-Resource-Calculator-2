package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.IRecipeWrapperFactoryRepository;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapperFactory;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.InvalidRecipeException;
import untouchedwagons.minecraft.mcrc2.api.recipes.exceptions.UnknownRecipeException;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict.ShapedOreRecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict.ShapelessOreRecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapedRecipesWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapelessRecipesWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MinecraftRecipeSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private Iterator recipes_iterator;
    private IRecipeWrapperFactoryRepository wrapper_repo;
    private List<Class> reported_unknown_recipes = new ArrayList<Class>();

    public boolean hasNext() {
        if (this.recipes_iterator == null)
            this.recipes_iterator = CraftingManager.getInstance().getRecipeList().iterator();

        return this.recipes_iterator.hasNext();
    }

    public RecipeWrapper next() {
        if (!this.hasNext())
            return null;

        IRecipe recipe = (IRecipe)this.recipes_iterator.next();
        RecipeWrapperFactory factory;

        try {
            factory = this.wrapper_repo.get(recipe);
        } catch (UnknownRecipeException e) {
            if (!this.reported_unknown_recipes.contains(recipe.getClass()))
            {
                this.reported_unknown_recipes.add(recipe.getClass());
                System.out.println("Warning: could not process recipe of type " + recipe.getClass().toString());
            }
            return null;
        }

        try {
            return factory.createWrapper(recipe);
        } catch (InvalidRecipeException e) {
            System.out.println("InvalidRecipeException");
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocalizationRegistry(ILocalizationRegistry registry) {

    }

    @Override
    public void setRecipeWrapperFactoryRepository(IRecipeWrapperFactoryRepository wrapper_repo) {
        this.wrapper_repo = wrapper_repo;

        wrapper_repo.put(ShapelessOreRecipe.class, new ShapelessOreRecipesWrapperFactory());
        wrapper_repo.put(ShapedOreRecipe.class, new ShapedOreRecipesWrapperFactory());
        wrapper_repo.put(ShapelessRecipes.class, new ShapelessRecipesWrapperFactory());
        wrapper_repo.put(ShapedRecipes.class, new ShapedRecipesWrapperFactory());
    }

    @Override
    public void setToolRegistry(Map<String, Integer> registry) {

    }

    @Override
    public Iterator<RecipeWrapper> iterator() {
        return this;
    }

    private class ShapelessOreRecipesWrapperFactory extends RecipeWrapperFactory
    {
        public RecipeWrapper createWrapper(IRecipe recipe) throws InvalidRecipeException {
            return new ShapelessOreRecipeWrapper(recipe, this.getRegistry());
        }
    }

    private class ShapedOreRecipesWrapperFactory extends RecipeWrapperFactory
    {
        public RecipeWrapper createWrapper(IRecipe recipe) throws InvalidRecipeException {
            return new ShapedOreRecipeWrapper(recipe, this.getRegistry());
        }
    }

    private class ShapelessRecipesWrapperFactory extends RecipeWrapperFactory
    {
        public RecipeWrapper createWrapper(IRecipe recipe) throws InvalidRecipeException {
            return new ShapelessRecipesWrapper(recipe, this.getRegistry());
        }
    }

    private class ShapedRecipesWrapperFactory extends RecipeWrapperFactory
    {
        public RecipeWrapper createWrapper(IRecipe recipe) throws InvalidRecipeException {
            return new ShapedRecipesWrapper(recipe, this.getRegistry());
        }
    }
}
