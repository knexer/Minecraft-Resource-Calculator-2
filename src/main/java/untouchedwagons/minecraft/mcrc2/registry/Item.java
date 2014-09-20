package untouchedwagons.minecraft.mcrc2.registry;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final String localized_name;
    private final String unlocalized_name;
    private final List<RecipeWrapper> recipes;

    public Item(String unlocalized_name, String localized_name) {
        this.unlocalized_name = unlocalized_name;
        this.localized_name = localized_name;

        this.recipes = new ArrayList<RecipeWrapper>();
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public String getUnlocalized_name() {
        return unlocalized_name;
    }

    public List<RecipeWrapper> getRecipes() { return recipes; }
}
