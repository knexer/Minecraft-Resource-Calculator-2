package untouchedwagons.minecraft.mcrc2.api.stacks;

public interface StackWrapper {
    public int getAmount();

    public String getOwningMod();

    public Object getUnderlyingStack();

    public String getUnlocalizedName();

    public String getLocalizedName();
}
