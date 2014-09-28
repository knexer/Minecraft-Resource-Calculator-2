package untouchedwagons.minecraft.mcrc2.crafting;

import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.Utilities;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.Item;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftMod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class CraftingTree {
    private ILocalizationRegistry localization_registry;
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
                        Map<String, Integer> excess_items) {
        this.localization_registry = localization_registry;
        this.registry = registry;
        this.selected_recipes = registry.getSelectedRecipes();
        this.excess_items = excess_items;
        this.tool_registry = registry.getTools();

        this.ingredients = new HashMap<String, CraftingTree>();
        this.excess = false;
    }

    public void craft(String item, Integer amount) {
        this.result = item;
        this.amount = amount;

        Matcher mod_id_matcher = GameRegistry.ModItemRegex.matcher(item);
        MinecraftMod mod = this.registry.getMod(mod_id_matcher.group(1));
        Item item_obj = mod.getItem(mod_id_matcher.group(2));
        List<RecipeWrapper> recipes = item_obj.getRecipes();

        // No need to go any further if there's no recipes
        if ((this.recipe_count = recipes.size()) == 0) return;

        Integer recipe_pos = this.selected_recipes.get(item);

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
        if ((excess_count = this.excess_items.get(item)) != null &&
                excess_count > amount)
        {
            this.excess = true;
            this.excess_items.put(item, excess_count - amount);

            return;
        }

        // Calculate the number of sets of items we need
        Integer bundles = (int)Math.ceil(amount / this.recipe.getResult().getAmount());
        Integer excess = (this.recipe.getResult().getAmount() * bundles) - amount;

        // If there'll be leftover we'll add it to the list
        if (excess > 0)
            this.handleExcess(item, excess);

        // Take care of any by-products made by the recipe
        // For example, an empty bucket when making cake
        for (Map.Entry<ItemStack, Integer> by_product : this.recipe.getByProducts().entrySet())
        {
            String by_product_name = this.localization_registry.getLocalizedName(by_product.getKey());

            this.handleExcess(by_product_name, by_product.getValue());
        }

        for (Map.Entry<Object, Integer> ingredient : this.recipe.getIngredients().entrySet())
        {
            Integer items_required;

            if (ingredient.getKey() instanceof ItemStack)
            {
                // If the ingredient is a by-product we'll only need the ingredient count for all the crafting
                // steps since the ingredient can be reused
                if (this.recipe.getByProducts().get(ingredient.getKey()) == ingredient.getValue())
                    items_required = ingredient.getValue();
                else
                    items_required = ingredient.getValue() * bundles;

                String ingredient_name = String.format("%s:%s",
                        Utilities.getModId(((ItemStack)ingredient.getKey()).getItem()),
                        this.localization_registry.getUnlocalizedName((ItemStack) ingredient.getKey())
                );

                CraftingTree crafting_tree = new CraftingTree(this.localization_registry, this.registry, this.excess_items);
                crafting_tree.craft(ingredient_name, items_required);

                this.ingredients.put(ingredient_name, crafting_tree);
            }

            if (ingredient.getKey() instanceof List)
            {
                String oredict_name = String.format("%s:%s",
                        "Forge",
                        this.registry.getOredictName((List) ingredient.getKey())
                );

                CraftingTree crafting_tree = new CraftingTree(this.localization_registry, this.registry, this.excess_items);
                crafting_tree.craft(oredict_name, ingredient.getValue());

                this.ingredients.put(oredict_name, crafting_tree);
            }
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
