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
    private final Iterator recipes_iterator;
    private Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers;
    private ILocalizationRegistry registry;

    public MinecraftRecipeSupportService() {
        this.recipes_iterator = CraftingManager.getInstance().getRecipeList().iterator();
    }

    public boolean hasNext() {
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
            return (RecipeWrapper) wrapper_constructor.newInstance(recipe, this.registry);
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
        this.registry = registry;
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
            Class[] parameters = constructor.getParameterTypes();

            if (parameters.length != 2) continue;

            if (parameters[0] != IRecipe.class &&
                parameters[1] != ILocalizationRegistry.class) continue;

            return constructor;
        }

        return null;
    }
}
