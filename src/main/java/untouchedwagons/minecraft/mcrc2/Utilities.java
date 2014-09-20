package untouchedwagons.minecraft.mcrc2;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public abstract class Utilities {
    public static String getModId(Item item) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);

        return id == null || id.modId.equals("") ? "Vanilla" : id.modId;
    }
}
