package untouchedwagons.minecraft.mcrc2.registry;

import java.util.HashMap;
import java.util.Map;

public class MinecraftMod {
    private final String mod_id;
    private final String name;
    private final Map<String, MinecraftItem> items;

    public MinecraftMod(String mod_id, String name) {
        this.mod_id = mod_id;
        this.name = name;

        this.items = new HashMap<String, MinecraftItem>();
    }

    public String getModID() {
        return mod_id;
    }

    public String getName() {
        return name;
    }

    public Map<String, MinecraftItem> getItems() { return items; }

    public MinecraftItem getItem(String item)
    {
        return this.items.get(item);
    }
}
