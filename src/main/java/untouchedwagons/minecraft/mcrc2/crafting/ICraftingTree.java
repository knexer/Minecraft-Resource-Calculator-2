package untouchedwagons.minecraft.mcrc2.crafting;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

import java.util.Map;

public interface ICraftingTree {
    public void craft(String item, Integer amount);

    public String getResult();

    public Integer getAmount();

    public boolean isExcess();

    public Map<String, ICraftingTree> getIngredients();

    public RecipeWrapper getRecipe();

    public Integer getRecipeCount();
}
