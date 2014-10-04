package untouchedwagons.minecraft.mcrc2.crafting.views;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;

public class RecipeView {
    private JsonObject json_object = new JsonObject();

    public void process(RecipeWrapper recipe)
    {
        this.json_object.add("produces", new JsonPrimitive(recipe.getResult().getAmount()));

        if (recipe.usesSpecialMachine())
            this.json_object.add("machine", new JsonPrimitive(recipe.getMachine()));

        if (recipe.hasExtraInformation())
            this.json_object.add("extra-information", new JsonPrimitive(recipe.getExtraInformation()));
    }

    public JsonObject getJsonObject()
    {
        return this.json_object;
    }
}
