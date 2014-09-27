package untouchedwagons.minecraft.mcrc2.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for handling recipes
 */
public abstract class RecipeWrapper
{
    private Map<ItemStack, Integer> by_products;
    private Map<Object, Integer> ingredients;
    private Object recipe;
    private ILocalizationRegistry registry;

    /**
     *
     * @param net.minecraft.item.crafting.IRecipe recipe
     * @param untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry registry
     */
    public RecipeWrapper(Object recipe, ILocalizationRegistry registry)
    {
        this.recipe = recipe;
        this.registry = registry;

        this.by_products = new HashMap<ItemStack, Integer>();
        this.ingredients = new HashMap<Object, Integer>();
    }

    /**
     * Get all by-products (returned items like emtpy buckets and used tools)
     * @return java.util.Map
     */
    public Map<ItemStack, Integer> getByProducts() {
        return by_products;
    }

    /**
     * Get all the ingredients needed for this recipe
     * @return java.util.Map
     */
    public Map<Object, Integer> getIngredients()
    {
        return this.ingredients;
    }

    /**
     * Gets the underlying recipe
     * @return net.minecraft.item.crafting.IRecipe
     */
    public Object getRecipe()
    {
        return this.recipe;
    }

    /**
     * Gets the localization registry passed through the constructor
     * @return untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry
     */
    public ILocalizationRegistry getRegistry() {
        return registry;
    }

    /**
     * Get the result of the crafting step
     * @return net.minecraft.item.ItemStack
     */
    public abstract StackWrapper getResult();

    /**
     * Template method that concrete classes can override,
     * gets the machine needed to perform this recipe
     * @return java.lang.String
     */
    public String getMachine()
    {
        return "crafting-table";
    }

    /**
     * Process the recipe
     */
    public abstract void parse();

    /**
     * Add a regular ingredient
     * @param net.minecraft.item.ItemStack is
     */
    protected void addIngredient(ItemStack is)
    {
        if (is.getItem().hasContainerItem(is))
            this.addByProduct(is.getItem().getContainerItem(is));

        ItemStack is2;

        for (Object o : this.getIngredients().keySet())
        {
            if (o instanceof List) continue;

            is2 = (ItemStack) o;

            if (is2.isItemEqual(is))
            {
                is2.stackSize += is.stackSize;

                this.getIngredients().put(
                    is2,
                    this.getIngredients().get(is2) + is.stackSize
                );

                return;
            }
        }

        this.getIngredients().put(is, is.stackSize);
    }

    /**
     * Add an OreDict ingredient (redirects to addIngredient(List, int))
     * @param java.util.List list The OreDict list entry in the recipe
     */
    protected void addIngredient(List list)
    {
        this.addIngredient(list, 1);
    }

    /**
     * Adds a specific number of OreDict ingredients
     * @param java.util.List list
     * @param int count
     */
    protected void addIngredient(List list, int count)
    {
        if (!this.getIngredients().containsKey(list))
            this.getIngredients().put(list, count);
        else
            this.getIngredients().put(
                    list,
                    this.getIngredients().get(list) + count
            );
    }

    /**
     * Adds a FluidStack ingredient, multiple identical fluidstacks are combined
     * @param net.minecraftforge.fluids.FluidStack fluid
     */
    protected void addIngredient(FluidStack fluid)
    {
        FluidStack fs;

        for (Map.Entry<Object, Integer> entry : this.ingredients.entrySet())
        {
            if (!(entry.getKey() instanceof FluidStack)) continue;

            fs = (FluidStack) entry.getKey();

            if (fs.fluidID == fluid.fluidID)
                fs.amount += fluid.amount;
        }
    }

    private void addByProduct(ItemStack by_product)
    {
        if (by_product == null)
            return;

        if (!this.by_products.containsKey(by_product))
            this.by_products.put(by_product, by_product.stackSize);
        else
            this.by_products.put(
                    by_product,
                    this.by_products.get(by_product) + by_product.stackSize
            );
    }
}
