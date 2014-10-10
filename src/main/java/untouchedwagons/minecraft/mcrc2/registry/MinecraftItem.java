package untouchedwagons.minecraft.mcrc2.registry;

import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public class MinecraftItem {
    private final String localized_name;
    private final String unlocalized_name;
    private final List<RecipeWrapper> recipes;
    private final String owning_mod;

    public MinecraftItem(String unlocalized_name, String localized_name, String owning_mod) {
        this.unlocalized_name = unlocalized_name;
        this.localized_name = localized_name;
        this.owning_mod = owning_mod;

        this.recipes = new ArrayList<RecipeWrapper>();
    }

    public String getLocalizedName() {
        return localized_name;
    }

    public String getOwningMod() {
        return owning_mod;
    }

    public String getUnlocalizedName() {
        return unlocalized_name;
    }

    public List<RecipeWrapper> getRecipes() { return recipes; }
}
