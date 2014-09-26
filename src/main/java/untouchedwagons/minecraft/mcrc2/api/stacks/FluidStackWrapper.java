package untouchedwagons.minecraft.mcrc2.api.stacks;

import net.minecraftforge.fluids.FluidStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;

public class FluidStackWrapper implements StackWrapper {
    private final FluidStack stack;
    private final ILocalizationRegistry registry;

    public FluidStackWrapper(FluidStack stack, ILocalizationRegistry registry) {
        this.stack = stack;
        this.registry = registry;
    }

    @Override
    public int getAmount() {
        return this.stack.amount;
    }

    @Override
    public String getOwningMod() {
        return "Forge";
    }

    @Override
    public Object getUnderlyingStack() {
        return this.stack;
    }

    @Override
    public String getUnlocalizedName() {
        return this.stack.getUnlocalizedName();
    }

    @Override
    public String getLocalizedName() {
        return this.stack.getLocalizedName();
    }
}
