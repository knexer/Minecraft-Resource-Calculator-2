package untouchedwagons.minecraft.mcrc2.crafting;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.exceptions.InfiniteRecursionException;

import java.util.ArrayList;

public class UsedToolCraftingTree implements ICraftingTree {
    private String result_domain;
    private String result;
    private Integer amount;

    @Override
    public void craft(String domain, String item, Integer amount) throws InfiniteRecursionException {
        this.result_domain = domain;
        this.result = item;
        this.amount = amount;
    }

    @Override
    public String getResult() {
        return String.format("%s:%s", this.result_domain, this.result);
    }

    @Override
    public Integer getAmount() {
        return this.amount;
    }

    @Override
    public boolean isExcess() {
        return true;
    }

    @Override
    public java.util.List<ICraftingTree> getIngredients() {
        return new ArrayList<ICraftingTree>();
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
