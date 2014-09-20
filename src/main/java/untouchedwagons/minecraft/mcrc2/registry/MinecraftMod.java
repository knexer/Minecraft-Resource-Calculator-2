package untouchedwagons.minecraft.mcrc2.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftMod {
    private final String mod_id;
    private final String name;
    private final Map<String, Item> items;

    public MinecraftMod(String mod_id, String name) {
        this.mod_id = mod_id;
        this.name = name;

        this.items = new HashMap<String, Item>();
    }

    public String getModID() {
        return mod_id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Item> getItems() { return items; }
}
