package untouchedwagons.minecraft.mcrc2;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PotionHelper {
    public static final int[] potion_sub_ids = new int[] {
            0, // item.potion.water
            16, // item.potion.awkward
            8193, // item.potion.regen
            8225, // item.potion.regen.enhanced
            8257, // item.potion.regen.extended
            16385, // item.potion.regen.splash
            16417, // item.potion.regen.enhanced.splash
            16449, // item.potion.regen.extended.splash
            8194, // item.potion.speed
            8226, // item.potion.speed.enhanced
            8258, // item.potion.speed.extended
            16386, // item.potion.speed.splash
            16418, // item.potion.speed.enhanced.splash
            16450, // item.potion.speed.extended.splash
            8195, // item.potion.fire
            8259, // item.potion.fire.extended
            16387, // item.potion.fire.splash
            16451, // item.potion.fire.extended.splash
            8196, // item.potion.poison
            8228, // item.potion.poison.enhanced
            8260, // item.potion.poison.extended
            16388, // item.potion.poison.splash
            16420, // item.potion.poison.enhanced.splash
            16452, // item.potion.poison.extended.splash
            8197, // item.potion.healing
            8229, // item.potion.healing.enhanced
            16389, // item.potion.healing.splash
            16421, // item.potion.healing.enhanced.splash
            8198, // item.potion.vision
            8262, // item.potion.vision.extended
            16390, // item.potion.vision.splash
            16454, // item.potion.vision.extended.splash
            8200, // item.potion.weakness
            8264, // item.potion.weakness.extended
            16392, // item.potion.weakness.splash
            16456, // item.potion.weakness.splash.extended
            8201, // item.potion.strength
            8233, // item.potion.strength.enhanced
            8265, // item.potion.strength.extended
            16393, // item.potion.strength.splash
            16425, // item.potion.strength.enhanced.splash
            16457, // item.potion.strength.extended.splash
            8202, // item.potion.slowness
            8266, // item.potion.slowness.extended
            16394, // item.potion.slowness.splash
            16458, // item.potion.slowness.splash.extended
            8204, // item.potion.harming
            8236, // item.potion.harming.enhanced
            16396, // item.potion.harming.splash
            16428, // item.potion.harming.enhanced.splash
            8205, // item.potion.breathing
            8269, // item.potion.breathing.extended
            16397, // item.potion.breathing.splash
            16461, // item.potion.breathing.extended.splash
            8206, // item.potion.invisibility
            8270, // item.potion.invisibility.extended
            16398, // item.potion.invisibility.splash
            16462, // item.potion.invisibility.extended.splash
    };

    public static void getSubItems(List<ItemStack> p_150895_3_)
    {
        for (int damage_id : potion_sub_ids)
        {
            p_150895_3_.add(new ItemStack(Items.potionitem, 1, damage_id));
        }
    }
}
