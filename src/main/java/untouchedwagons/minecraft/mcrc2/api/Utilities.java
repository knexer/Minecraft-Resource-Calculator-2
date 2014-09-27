package untouchedwagons.minecraft.mcrc2.api;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.regex.Pattern;

public class Utilities {
    public static final Pattern ModIDRegex = Pattern.compile("^(\\w+)");

    private Utilities() {}

    public static String getModId(Item item) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);

        return id == null || id.modId.equals("") ? "minecraft" : id.modId;
    }
}
