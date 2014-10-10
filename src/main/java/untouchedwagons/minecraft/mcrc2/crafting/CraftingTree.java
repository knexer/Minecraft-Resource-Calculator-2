package untouchedwagons.minecraft.mcrc2.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.exceptions.InfiniteRecursionException;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftItem;

import java.util.*;

public class CraftingTree implements ICraftingTree {
    private Map<String, Integer> tools_in_use;
    private long start_time;
    private String result;
    private Integer amount;
    private Boolean excess;
    private RecipeWrapper recipe;
    private Integer recipe_count;
    private Map<String, Integer> excess_items;

    private GameRegistry registry;
    private Map<Item, String> item_id_lookup;
    private Map<String, Integer> selected_recipes;
    private List<ICraftingTree> ingredients;
    private Map<String, Integer> tool_registry;

    public CraftingTree(GameRegistry game_registry,
                        Map<String, Integer> excess_items,
                        Map<String, Integer> tools_in_use,
                        long start_time) {
        this.registry = game_registry;

        this.item_id_lookup = this.registry.getItemIdReverseLookup();
        this.selected_recipes = game_registry.getSelectedRecipes();
        this.excess_items = excess_items;
        this.tools_in_use = tools_in_use;
        this.tool_registry = game_registry.getTools();
        this.start_time = start_time;

        this.ingredients = new ArrayList<ICraftingTree>();
        this.excess = false;
    }

    public void craft(String item, Integer amount) throws InfiniteRecursionException {
        this.result = item;
        this.amount = amount;

        // If 5 seconds have passed since starting, we bail since we're probably stuck
        // in an infinite loop
        if(System.currentTimeMillis() - this.start_time > 5000)
            throw new InfiniteRecursionException();

        MinecraftItem item_obj = this.registry.getItems().get(item);
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
            String by_product_name = this.item_id_lookup.get(by_product.getKey().getItem());

            if (by_product.getKey().getHasSubtypes())
                by_product_name += String.format(":%d", by_product.getKey().getItemDamage());

            this.handleExcess(by_product_name, by_product.getValue());
        }

        for (Map.Entry<Object, Integer> ingredient : this.recipe.getIngredients().entrySet())
        {
            Integer items_required = 1;
            String ingredient_name = "";
            ICraftingTree crafting_tree;

            if (ingredient.getKey() instanceof ItemStack)
            {
                ItemStack ingredient_is = (ItemStack) ingredient.getKey();

                ingredient_name = this.registry.getItemIdReverseLookup().get(ingredient_is.getItem());

                if (ingredient_is.getHasSubtypes())
                    ingredient_name += String.format(":%d", ingredient_is.getItemDamage());

                Integer new_tool_durability;
                Integer used_tool_durability;

                // Is the ingredient a tool that takes durability?
                if ((new_tool_durability = this.tool_registry.get(ingredient_name)) != null)
                {
                    Integer temp_bundles = bundles;

                    // If the tool has already been made and has more than enough durability we'll use it first
                    if ((used_tool_durability = this.tools_in_use.get(ingredient_name)) != null)
                    {
                        // If there's not enough or just enough durability we'll use up the tool first
                        if (used_tool_durability <= temp_bundles)
                        {
                            temp_bundles -= used_tool_durability;
                            this.tools_in_use.remove(ingredient_name);

                            crafting_tree = new UsedToolCraftingTree();
                            crafting_tree.craft(ingredient_name, 1);

                            this.ingredients.add(crafting_tree);
                        }

                        // If we still need to make more
                        if (temp_bundles > 0)
                        {
                            items_required = (int)Math.ceil(temp_bundles / new_tool_durability);
                            used_tool_durability = (items_required * new_tool_durability) - temp_bundles;

                            this.tools_in_use.put(ingredient_name, used_tool_durability);
                        }
                    }
                }
                // If the ingredient is a by-product we'll only need the ingredient count for all the crafting
                // steps since the ingredient can be reused
                else if (this.recipe.getByProducts().get(ingredient.getKey()) == ingredient.getValue())
                    items_required = ingredient.getValue();
                else
                    items_required = ingredient.getValue() * bundles;
            }
            else if (ingredient.getKey() instanceof List)
            {
                ingredient_name = this.registry.getOredictName((List) ingredient.getKey());
            }
            else if (ingredient.getKey() instanceof FluidStack)
            {
                ingredient_name = "Forge:" + ((FluidStack)ingredient.getKey()).getUnlocalizedName();
            }
            else
            {
                System.out.println("Found unknown ingredient type. Expected: ItemStack, List or FluidStack, got: " + ingredient.getKey().getClass());
                continue;
            }

            crafting_tree = new CraftingTree(this.registry, this.excess_items, this.tools_in_use, this.start_time);
            crafting_tree.craft(ingredient_name, items_required);

            this.ingredients.add(crafting_tree);
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

    public List<ICraftingTree> getIngredients() {
        return this.ingredients;
    }

    public RecipeWrapper getRecipe() {
        return this.recipe;
    }

    public Integer getRecipeCount() {
        return this.recipe_count;
    }
}
