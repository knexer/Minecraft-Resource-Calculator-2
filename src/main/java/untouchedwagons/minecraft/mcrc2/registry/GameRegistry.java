package untouchedwagons.minecraft.mcrc2.registry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.oredict.OreDictionary;
import untouchedwagons.minecraft.mcrc2.MinecraftResourceCalculatorMod;
import untouchedwagons.minecraft.mcrc2.PotionHelper;
import untouchedwagons.minecraft.mcrc2.api.Utilities;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.filters.RecipeFilter;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.*;

public class GameRegistry {
    private final Map<Item, String> item_id_reverse_lookup;
    private final Map<String, MinecraftItem> items;
    private final Map<String, Integer> mod_item_counts;
    private final Map<String, String> mod_names;

    private final HashMap<List, String> oredict_reverse_lookup;
    private final Map<Class, List<RecipeFilter>> recipe_filters;
    private final List<IModSupportService> support_services;
    private final Map<String, Integer> selected_recipes;
    private final Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers;

    private boolean ready;

    public GameRegistry() {
        this.item_id_reverse_lookup = new HashMap<Item, String>();
        this.items = new HashMap<String, MinecraftItem>();
        this.mod_item_counts = new HashMap<String, Integer>();
        this.mod_names = new HashMap<String, String>();

        this.oredict_reverse_lookup = new HashMap<List, String>();
        this.recipe_filters = new HashMap<Class, List<RecipeFilter>>();
        this.selected_recipes = new HashMap<String, Integer>();
        this.support_services = new ArrayList<IModSupportService>();
        this.wrapper_providers = new HashMap<Class<? extends IRecipe>, Class<? extends RecipeWrapper>>();

        this.ready = false;

    }

    public void collectMods() {
        this.mod_item_counts.put("minecraft", 0);
        this.mod_names.put("minecraft", "Minecraft");

        for (ModContainer mod : Loader.instance().getActiveModList())
        {
            this.mod_item_counts.put(mod.getModId(), 0);
            this.mod_names.put(mod.getModId(), mod.getName());
        }
    }

    public void collectOreDictRegistrations()
    {
        List<ItemStack> oredict_items;
        String ore_id, unlocalized_name, display_name;

        for (String oredict_name : OreDictionary.getOreNames())
        {
            oredict_items = OreDictionary.getOres(oredict_name);

            ore_id = "Forge:" + oredict_name;
            unlocalized_name = "item.Forge." + oredict_name + ".name";

            if (StatCollector.canTranslate(unlocalized_name)) {
                display_name = StatCollector.translateToLocal(unlocalized_name);
            } else {
                if (MinecraftResourceCalculatorMod.do_logging)
                    MinecraftResourceCalculatorMod.error_logger.println(String.format("Could not find localization for %s", unlocalized_name));

                display_name = unlocalized_name;
            }

            // Add the OreDict list to forge's list of items. We'll use the localized name of the first item in the
            // list as the item's name
            this.items.put(ore_id, new MinecraftItem(ore_id, display_name, "Forge"));

            this.oredict_reverse_lookup.put(oredict_items, ore_id);

            this.mod_item_counts.put(
                    "Forge",
                    this.mod_item_counts.get("Forge") + 1);
        }
    }

    public void collectItems()
    {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        //noinspection unchecked
        for (String item_name : (Set<String>) Item.itemRegistry.getKeys()) {
            Item item = (Item) Item.itemRegistry.getObject(item_name);

            this.item_id_reverse_lookup.put(item, item_name);

            try {
                // PotionItem's getSubItems method can't be trusted
                if (item == Items.potionitem) {
                    PotionHelper.getSubItems(items);
                }
                else {
                    item.getSubItems(item, null, items);
                }

                if (item.getHasSubtypes())
                {
                    items.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
                }
            }
            catch (Exception npe)
            {
                // Extra Utilities' Microblocks are known to cause NullPointerExceptions. *Looks in RWTema's general direction*
                // ChickenBones' Microblocks are known to cause NullPointerExceptions. *Looks in ChickenBones's general direction*
                if (MinecraftResourceCalculatorMod.do_logging) {
                    npe.printStackTrace(MinecraftResourceCalculatorMod.error_logger);
                }
            }
        }

        String itemstack_id, unlocalized_name, display_name, owning_mod;

        for (ItemStack is : items)
        {
            try {
                itemstack_id = this.item_id_reverse_lookup.get(is.getItem());
                owning_mod = Utilities.getModId(is);

                if (is.getHasSubtypes()) {
                    itemstack_id += String.format(":%d", is.getItemDamage());
                }

                if (is.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    unlocalized_name = is.getUnlocalizedName() + ".wildcard.name";

                    if (StatCollector.canTranslate(unlocalized_name)) {
                        display_name = StatCollector.translateToLocal(unlocalized_name);
                    }
                    else {
                        FMLLog.warning("Localization needed for '%s' from mod '%s'\n",
                                unlocalized_name, Utilities.getModId(is));

                        display_name = unlocalized_name;
                    }
                }
                else {
                    display_name = is.getDisplayName();
                }

                this.items
                        .put(itemstack_id,
                                new MinecraftItem(itemstack_id, display_name, owning_mod));

                this.mod_item_counts.put(
                        owning_mod,
                        this.mod_item_counts.get(owning_mod) + 1);
            }
            catch (Exception e)
            {
                // Forestry's research notes are known to cause NPEs when getting the localized name. *Looks in Sengir's general direction*
                if (MinecraftResourceCalculatorMod.do_logging) {
                    e.printStackTrace(MinecraftResourceCalculatorMod.error_logger);
                }
            }
        }
    }

    public void collectFluids()
    {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        Fluid fluid;
        String fluid_name;

        for (Map.Entry<String, Fluid> fluid_entry : fluids.entrySet())
        {
            fluid = fluid_entry.getValue();
            fluid_name = "Forge:" + fluid.getUnlocalizedName();

            this.items.put(
                    fluid_name,
                    new MinecraftItem(
                            fluid_name,
                            fluid.getLocalizedName(
                                    new FluidStack(fluid, 0)
                            ),
                            "Forge"
                    )
            );
        }
    }

    public void collectRecipeProviders()
    {
        for (IModSupportService service : ServiceLoader.load(IModSupportService.class))
        {
            if (!service.shouldActivateService()) {
                continue;
            }

            service.setItemIdReverseLookup(this.item_id_reverse_lookup);
            service.setRecipeWrapperRepository(this.wrapper_providers);

            this.support_services.add(service);
        }
    }

    public void collectRecipeFilters()
    {
        List<RecipeFilter> filter_list;

        for (RecipeFilter filter : ServiceLoader.load(RecipeFilter.class))
        {
            if (!filter.shouldActivateFilter()) {
                continue;
            }

            for (Class recipe_class : filter.getRecipeWrapperClasses())
            {
                if (this.recipe_filters.containsKey(recipe_class)) {
                    filter_list = this.recipe_filters.get(recipe_class);
                }
                else {
                    this.recipe_filters.put(recipe_class, filter_list = new ArrayList<RecipeFilter>());
                }

                filter_list.add(filter);
            }
        }
    }

    public void collectRecipes()
    {
        boolean filter_recipe;
        StackWrapper result;
        String unlocalized_name;

        for (IModSupportService support_service : this.support_services)
        {
            for (RecipeWrapper wrapped_recipe : support_service) {
                try
                {
                    if (wrapped_recipe == null) {
                        continue;
                    }

                    wrapped_recipe.parse();

                    filter_recipe = false;

                    if (this.recipe_filters.containsKey(wrapped_recipe.getClass()))
                    {
                        for (RecipeFilter filter : this.recipe_filters.get(wrapped_recipe.getClass()))
                        {
                            if (filter_recipe = filter.shouldFilterRecipe(wrapped_recipe)) {
                                break;
                            }
                        }
                    }

                    if (filter_recipe) {
                        if (MinecraftResourceCalculatorMod.do_logging) {
                            MinecraftResourceCalculatorMod.error_logger.println(
                                    String.format("Filtered recipe with result of %s", wrapped_recipe.getResult().getLocalizedName())
                            );
                        }

                        continue;
                    }

                    result = wrapped_recipe.getResult();

                    unlocalized_name = result.getUnlocalizedName();

                    this.items.get(unlocalized_name)
                        .getRecipes()
                        .add(wrapped_recipe);
                }
                catch (Exception e)
                {
                    if (MinecraftResourceCalculatorMod.do_logging) {
                        e.printStackTrace(MinecraftResourceCalculatorMod.error_logger);
                    }
                }
            }
        }
    }

    public Map<String, MinecraftItem> getItems() {
        return items;
    }

    public Map<String, Integer> getModItemCounts() {
        return mod_item_counts;
    }

    public Map<String, String> getModNames() {
        return mod_names;
    }

    public String getOredictName(List oredict_list)
    {
        return this.oredict_reverse_lookup.get(oredict_list);
    }

    public Map<Item, String> getItemIdReverseLookup() {
        return item_id_reverse_lookup;
    }

    public HashMap<List, String> getOredictReverseLookup() { return oredict_reverse_lookup; }

    public Map<String, Integer> getSelectedRecipes() { return selected_recipes; }

    public void markAsReady(boolean b) {
        this.ready = b;
    }

    public boolean isReady() {
        return ready;
    }
}
