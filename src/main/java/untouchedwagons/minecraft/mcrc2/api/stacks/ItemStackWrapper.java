package untouchedwagons.minecraft.mcrc2.api.stacks;

import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.Utilities;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;

/**
 * The most common stack wrapper you'll use.
 */
public class ItemStackWrapper implements StackWrapper {
    private final ItemStack stack;
    private final ILocalizationRegistry registry;

    public ItemStackWrapper(ItemStack stack, ILocalizationRegistry registry) {
        this.stack = stack;
        this.registry = registry;
    }

    @Override
    public int getAmount() {
        return this.stack.stackSize;
    }

    @Override
    public String getOwningMod() {
        return Utilities.getModId(this.stack.getItem());
    }

    @Override
    public Object getUnderlyingStack() {
        return this.stack;
    }

    @Override
    public String getUnlocalizedName() {
        return this.registry.getUnlocalizedName(this.stack);
    }

    @Override
    public String getLocalizedName() {
        return this.stack.getDisplayName();
    }
}
