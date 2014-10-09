package untouchedwagons.minecraft.mcrc2.views.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import untouchedwagons.minecraft.mcrc2.crafting.ICraftingTree;
import untouchedwagons.minecraft.mcrc2.crafting.UsedToolCraftingTree;
import untouchedwagons.minecraft.mcrc2.views.IView;

public class CraftingTreeView implements IView<ICraftingTree> {
    private JsonObject json_object = new JsonObject();

    public void process(ICraftingTree tree_node)
    {
        this.json_object.add("item", new JsonPrimitive(tree_node.getResult()));
        this.json_object.add("amount", new JsonPrimitive(tree_node.getAmount()));
        this.json_object.add("excess", new JsonPrimitive(tree_node.isExcess()));
        this.json_object.add("recipe-count", new JsonPrimitive(tree_node.getRecipeCount()));
        this.json_object.add("is-used-tool", new JsonPrimitive(tree_node instanceof UsedToolCraftingTree));

        JsonArray ingredients = new JsonArray();

        for (ICraftingTree ingredient : tree_node.getIngredients())
        {
            CraftingTreeView ingredient_view = new CraftingTreeView();
            ingredient_view.process(ingredient);

            ingredients.add(ingredient_view.getJsonObject());
        }

        this.json_object.add("ingredients", ingredients);

        if (tree_node.getRecipe() != null)
        {
            RecipeView recipe_view = new RecipeView();
            recipe_view.process(tree_node.getRecipe());

            this.json_object.add("recipe", recipe_view.getJsonObject());
        }
    }

    public JsonObject getJsonObject()
    {
        return this.json_object;
    }
}
