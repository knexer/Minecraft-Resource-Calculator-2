package untouchedwagons.minecraft.mcrc2.views.debugging;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftItem;
import untouchedwagons.minecraft.mcrc2.views.IView;

public class ItemView implements IView<MinecraftItem> {
    private final GameRegistry game_registry;
    private JsonObject json_object = new JsonObject();

    public ItemView(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public void process(MinecraftItem item) {
        this.json_object.add("localized-name", new JsonPrimitive(item.getLocalizedName()));

        JsonArray recipes = new JsonArray();

        for(RecipeWrapper recipe : item.getRecipes())
        {
            RecipeView recipe_view = new RecipeView(this.game_registry);
            recipe_view.process(recipe);

            recipes.add(recipe_view.getJsonObject());
        }

        this.json_object.add("recipes", recipes);
    }

    @Override
    public JsonObject getJsonObject() {
        return this.json_object;
    }
}
