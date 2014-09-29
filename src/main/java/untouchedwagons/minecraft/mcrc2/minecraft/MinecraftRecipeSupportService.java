package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict.ShapedOreRecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.oredict.ShapelessOreRecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapedRecipesWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapelessRecipesWrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MinecraftRecipeSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private Iterator recipes_iterator;
    private List<Class> reported_unknown_recipes = new ArrayList<Class>();
    private Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers;

    public boolean hasNext() {
        if (this.recipes_iterator == null)
            this.recipes_iterator = CraftingManager.getInstance().getRecipeList().iterator();

        return this.recipes_iterator.hasNext();
    }

    public RecipeWrapper next() {
        if (!this.hasNext())
            return null;

        IRecipe recipe = (IRecipe)this.recipes_iterator.next();
        Class<? extends RecipeWrapper> recipe_wrapper_class = this.wrapper_providers.get(recipe.getClass());

        if (recipe_wrapper_class == null) {
            System.out.println("Warning: could not process recipe of type " + recipe.getClass());
            return null;
        }

        Constructor wrapper_constructor = this.getConstructorForRecipeWrapper(recipe_wrapper_class);

        if (wrapper_constructor == null) {
            System.out.println("Warning: could not find appropriate constructor for RecipeWrapper subclass: " + recipe_wrapper_class);
            return null;
        }

        try {
            return (RecipeWrapper) wrapper_constructor.newInstance(recipe);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocalizationRegistry(ILocalizationRegistry registry) {

    }

    @Override
    public void setRecipeWrapperRepository(Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers) {
        this.wrapper_providers = wrapper_providers;

        wrapper_providers.put(ShapelessOreRecipe.class, ShapelessOreRecipeWrapper.class);
        wrapper_providers.put(ShapedOreRecipe.class, ShapedOreRecipeWrapper.class);
        wrapper_providers.put(ShapelessRecipes.class, ShapelessRecipesWrapper.class);
        wrapper_providers.put(ShapedRecipes.class, ShapedRecipesWrapper.class);
    }

    @Override
    public void setToolRegistry(Map<String, Integer> registry) {

    }

    @Override
    public Iterator<RecipeWrapper> iterator() {
        return this;
    }

    private Constructor getConstructorForRecipeWrapper(Class<? extends RecipeWrapper> wrapper)
    {
        for (Constructor constructor : wrapper.getConstructors())
        {
            Class[] parameters = constructor.getExceptionTypes();

            if (parameters.length != 1) continue;

            if (parameters[0] != IRecipe.class) continue;

            return constructor;
        }

        return null;
    }
}
