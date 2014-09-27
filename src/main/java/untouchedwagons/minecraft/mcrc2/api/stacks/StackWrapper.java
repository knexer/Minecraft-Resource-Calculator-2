package untouchedwagons.minecraft.mcrc2.api.stacks;

/**
 * Nearly all recipes have an ItemStack as their result, but some have a FluidStack as their result so we need
 * to be able to provide a fairly uniform way to present them in RecipeWrappers
 */
public interface StackWrapper {
    /**
     * Not much to say on this one is there? FluidStacks present the amount as millibuckets
     * @return int
     */
    public int getAmount();

    /**
     * Get the mod this stack's underlying item belongs to. FluidStacks belong to Forge, see
     * untouchedwagons.minecraft.mcrc2.api.Utilities#getModId(net.minecraft.item.Item) for
     * ItemStacks
     * @return String
     */
    public String getOwningMod();

    /**
     * Could be an ItemStack, could be a FluidStack. Who knows.
     * @return Object
     */
    public Object getUnderlyingStack();

    /**
     * Gets the underlying stack's unlocalized_name. ItemStackWrapper uses the localization registry,
     * FluidStacks are called directly since it's harder to break them
     * @return String
     */
    public String getUnlocalizedName();

    /**
     * Gets the underlying stack's display name. ItemStackWrapper uses the localization registry,
     * FluidStacks are called directly since it's harder to break them
     * @return String
     */
    public String getLocalizedName();
}
