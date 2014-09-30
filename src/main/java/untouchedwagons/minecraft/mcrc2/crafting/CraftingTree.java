package untouchedwagons.minecraft.mcrc2.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.Utilities;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.exceptions.InfiniteRecursionException;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.Item;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftMod;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class CraftingTree {
    private Map<String, Integer> tools_in_use;
    private Date start_date;
    private ILocalizationRegistry localization_registry;
    private String result_domain;
    private String result;
    private Integer amount;
    private Boolean excess;
    private RecipeWrapper recipe;
    private Integer recipe_count;
    private Map<String, Integer> excess_items;

    private GameRegistry registry;
    private Map<String, Integer> selected_recipes;
    private Map<String, CraftingTree> ingredients;
    private Map<String, Integer> tool_registry;

    public CraftingTree(ILocalizationRegistry localization_registry,
                        GameRegistry registry,
                        Map<String, Integer> excess_items,
                        Map<String, Integer> tools_in_use,
                        Date start_date) {
        this.localization_registry = localization_registry;
        this.registry = registry;
        this.selected_recipes = registry.getSelectedRecipes();
        this.excess_items = excess_items;
        this.tools_in_use = tools_in_use;
        this.tool_registry = registry.getTools();
        this.start_date = start_date;

        this.ingredients = new HashMap<String, CraftingTree>();
        this.excess = false;
    }

    public void craft(String domain, String item, Integer amount) throws InfiniteRecursionException {
        this.result_domain = domain;
        this.result = item;
        this.amount = amount;

        // If 5 seconds have passed since starting, we bail since we're probably stuck
        // in an infinite loop
        if(new Date().getTime() - this.start_date.getTime() > 5000)
            throw new InfiniteRecursionException();

        MinecraftMod mod = this.registry.getMod(this.result_domain);
        Item item_obj = mod.getItem(this.result);
        List<RecipeWrapper> recipes = item_obj.getRecipes();

        // No need to go any further if there's no recipes
        if ((this.recipe_count = recipes.size()) == 0) return;

        Integer recipe_pos = this.selected_recipes.get(this.result);

        // If the recipe position is out of bounds or the user has not
        // changed the default recipe
        if (recipe_pos == null ||
            recipe_pos < 0 ||
            recipe_pos >= this.recipe_count)
            recipe_pos = 0;

        this.recipe = recipes.get(recipe_pos);

        /* TODO
         * This can be improved a bit. If 6 items are required and there's
         * 2 excess and the item is made in batches of 4, 8 will be crafted
         * when the excess can be used.
         */
        // If there is sufficient excess, use it
        Integer excess_count;
        if ((excess_count = this.excess_items.get(this.result)) != null &&
                excess_count > amount)
        {
            this.excess = true;
            this.excess_items.put(this.result, excess_count - amount);

            return;
        }

        // Calculate the number of sets of items we need
        Integer bundles = (int)Math.ceil(amount / this.recipe.getResult().getAmount());
        Integer excess = (this.recipe.getResult().getAmount() * bundles) - amount;

        // If there'll be leftover we'll add it to the list
        if (excess > 0)
            this.handleExcess(this.result, excess);

        // Take care of any by-products made by the recipe
        // For example, an empty bucket when making cake
        for (Map.Entry<ItemStack, Integer> by_product : this.recipe.getByProducts().entrySet())
        {
            String by_product_name = this.localization_registry.getLocalizedName(by_product.getKey());

            this.handleExcess(by_product_name, by_product.getValue());
        }

        for (Map.Entry<Object, Integer> ingredient : this.recipe.getIngredients().entrySet())
        {
            Integer items_required = 1;
            String ingredient_domain = "";
            String ingredient_name = "";

            if (ingredient.getKey() instanceof ItemStack)
            {
                // If the ingredient is a by-product we'll only need the ingredient count for all the crafting
                // steps since the ingredient can be reused
                if (this.recipe.getByProducts().get(ingredient.getKey()) == ingredient.getValue())
                    items_required = ingredient.getValue();
                else
                    items_required = ingredient.getValue() * bundles;

                ingredient_domain = Utilities.getModId(((ItemStack)ingredient.getKey()).getItem());
                ingredient_name = this.localization_registry.getUnlocalizedName((ItemStack) ingredient.getKey());
            }
            else if (ingredient.getKey() instanceof List)
            {
                ingredient_domain = "Forge";
                ingredient_name = this.registry.getOredictName((List) ingredient.getKey());
            }
            else if (ingredient.getKey() instanceof FluidStack)
            {
                ingredient_domain = "Forge";
                ingredient_name = ((FluidStack)ingredient.getKey()).getLocalizedName();
            }
            else
            {
                System.out.println("Found unknown ingredient type. Expected: ItemStack, List or FluidStack, got: " + ingredient.getKey().getClass());
                continue;
            }

            CraftingTree crafting_tree = new CraftingTree(this.localization_registry, this.registry, this.excess_items, this.tools_in_use, this.start_date);
            crafting_tree.craft(this.result_domain, ingredient_name, items_required);

            this.ingredients.put(ingredient_domain + ":" + ingredient_name, crafting_tree);
        }
    }

    protected void handleExcess(String item, Integer amount)
    {
        Integer current_excess = this.excess_items.get(item);

        if (current_excess == null)
            this.excess_items.put(item, amount);
        else
            this.excess_items.put(item, current_excess + amount);
    }

    public String getResult() {
        return this.result;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public boolean isExcess() {
        return this.excess;
    }

    public Map<String, CraftingTree> getIngredients() {
        return this.ingredients;
    }

    public RecipeWrapper getRecipe() {
        return this.recipe;
    }

    public Integer getRecipeCount() {
        return this.recipe_count;
    }
}
