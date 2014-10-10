package untouchedwagons.minecraft.mcrc2.crafting;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.exceptions.InfiniteRecursionException;

import java.util.List;

public interface ICraftingTree {
    public void craft(String item, Integer amount) throws InfiniteRecursionException;

    public String getResult();

    public Integer getAmount();

    public boolean isExcess();

    public List<ICraftingTree> getIngredients();

    public RecipeWrapper getRecipe();

    public Integer getRecipeCount();
}
