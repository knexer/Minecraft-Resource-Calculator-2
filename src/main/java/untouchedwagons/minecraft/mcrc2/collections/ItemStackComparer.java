package untouchedwagons.minecraft.mcrc2.collections;

import net.minecraft.item.ItemStack;

public class ItemStackComparer implements Equatable {
    @Override
    public boolean objectsAreEqual(Object o1, Object o2) {
        ItemStack is1 = (ItemStack) o1;
        ItemStack is2 = (ItemStack) o2;

        return is1.isItemEqual(is2) || (is1.getItem() == is2.getItem() && (is1.getItemDamage() == Short.MAX_VALUE || is2.getItemDamage() == Short.MAX_VALUE));
    }
}
