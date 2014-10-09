package untouchedwagons.minecraft.mcrc2.views.debugging;

import com.google.gson.JsonObject;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftItem;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftMod;
import untouchedwagons.minecraft.mcrc2.views.IView;

import java.util.Map;

public class ModView implements IView<MinecraftMod> {
    private final GameRegistry game_registry;
    private JsonObject json_object = new JsonObject();

    public ModView(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public void process(MinecraftMod mod)
    {
        JsonObject items_object = new JsonObject();

        this.json_object.add("items", items_object);

        for(Map.Entry<String, MinecraftItem> item : mod.getItems().entrySet())
        {
            ItemView item_view = new ItemView(this.game_registry);
            item_view.process(item.getValue());

            items_object.add(item.getKey(), item_view.getJsonObject());
        }
    }

    @Override
    public JsonObject getJsonObject()
    {
        return this.json_object;
    }
}
