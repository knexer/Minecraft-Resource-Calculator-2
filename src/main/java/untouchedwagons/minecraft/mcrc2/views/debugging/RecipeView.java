package untouchedwagons.minecraft.mcrc2.views.debugging;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.views.IView;

import java.util.List;
import java.util.Map;

public class RecipeView implements IView<RecipeWrapper>
{
    private final GameRegistry game_registry;
    private final ILocalizationRegistry localization_registry;

    private JsonObject json_object = new JsonObject();

    public RecipeView(GameRegistry game_registry) {
        this.game_registry = game_registry;
        this.localization_registry = game_registry.getLocalizationRegistry();
    }

    @Override
    public void process(RecipeWrapper recipe) {
        this.json_object.add("produces", new JsonPrimitive(recipe.getResult().getAmount()));

        if (recipe.hasExtraInformation())
            this.json_object.add("extra-information", new JsonPrimitive(recipe.getExtraInformation()));

        if (recipe.usesSpecialMachine())
            this.json_object.add("machine", new JsonPrimitive(recipe.getMachine()));

        JsonObject ingredients = new JsonObject();

        for (Map.Entry<Object, Integer> ingredient : recipe.getIngredients().entrySet())
        {
            if (ingredient.getKey() instanceof ItemStack)
            {
                String unlocalized_name = this.localization_registry.getUnlocalizedName((ItemStack) ingredient.getKey());

                ingredients.add(unlocalized_name, new JsonPrimitive(ingredient.getValue()));
            }
            else if (ingredient.getKey() instanceof List)
            {
                String oredict_name = this.game_registry.getOredictName((List) ingredient.getKey());

                ingredients.add(oredict_name, new JsonPrimitive(ingredient.getValue()));
            }
            else if (ingredient.getKey() instanceof FluidStack)
            {
                String fluid_name = ((FluidStack)ingredient.getKey()).getUnlocalizedName();

                ingredients.add(fluid_name, new JsonPrimitive(ingredient.getValue()));
            }
        }

        this.json_object.add("ingredients", ingredients);
    }

    @Override
    public JsonObject getJsonObject() {
        return this.json_object;
    }
}
