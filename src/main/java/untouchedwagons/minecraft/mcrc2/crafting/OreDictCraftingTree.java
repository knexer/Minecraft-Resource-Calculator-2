package untouchedwagons.minecraft.mcrc2.crafting;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

import java.util.Map;

public class OreDictCraftingTree implements ICraftingTree {
    private String result;
    private Integer amount;

    @Override
    public void craft(String item, Integer amount) {
        this.result = item;
        this.amount = amount;
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public Integer getAmount() {
        return this.amount;
    }

    @Override
    public boolean isExcess() {
        return false;
    }

    @Override
    public Map<String, ICraftingTree> getIngredients() {
        return null;
    }

    @Override
    public RecipeWrapper getRecipe() {
        return null;
    }

    @Override
    public Integer getRecipeCount() {
        return 0;
    }
}
