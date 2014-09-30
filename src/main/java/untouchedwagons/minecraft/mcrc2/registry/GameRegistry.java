package untouchedwagons.minecraft.mcrc2.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.oredict.OreDictionary;
import untouchedwagons.minecraft.mcrc2.CustomLocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.PotionHelper;
import untouchedwagons.minecraft.mcrc2.api.Utilities;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.filters.RecipeFilter;
import untouchedwagons.minecraft.mcrc2.api.stacks.StackWrapper;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameRegistry {
    private final Map<String, MinecraftMod> mods;
    private final HashMap<List, String> oredict_reverse_lookup;
    private final Map<Class, List<RecipeFilter>> recipe_filters;
    private final ILocalizationRegistry registry;
    private final List<IModSupportService> support_services;
    private final Map<String, Integer> selected_recipes;
    private final Map<String, Integer> tools;
    private final Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers;

    private boolean ready;

    public static final Pattern ModItemRegex = Pattern.compile("^(.+):(.+)$");

    public GameRegistry() {
        this.oredict_reverse_lookup = new HashMap<List, String>();
        this.recipe_filters = new HashMap<Class, List<RecipeFilter>>();
        this.registry = new CustomLocalizationRegistry();
        this.selected_recipes = new HashMap<String, Integer>();
        this.support_services = new ArrayList<IModSupportService>();
        this.tools = new HashMap<String, Integer>();
        this.wrapper_providers = new HashMap<Class<? extends IRecipe>, Class<? extends RecipeWrapper>>();

        this.ready = false;

        this.mods = new HashMap<String, MinecraftMod>();
    }

    public void collectOreDictRegistrations()
    {
        ItemStack first_item;
        List<ItemStack> oredict_items;
        MinecraftMod forge = this.mods.get("Forge");

        for (String oredict_name : OreDictionary.getOreNames())
        {
            oredict_items = OreDictionary.getOres(oredict_name);
            first_item = oredict_items.get(0);

            // Add the OreDict list to forge's list of items. We'll use the localized name of the first item in the
            // list as the item's name
            forge.getItems().put(oredict_name, new Item(oredict_name, this.registry.getLocalizedName(first_item)));

            this.oredict_reverse_lookup.put(oredict_items, oredict_name);
        }
    }

    public void collectMods()
    {
        String mod_id;
        Matcher matcher;

        this.mods.put("minecraft", new MinecraftMod("minecraft", "Vanilla"));

        for (ModContainer mod : Loader.instance().getActiveModList())
        {
            matcher = Utilities.ModIDRegex.matcher(mod.getModId());

            if (!matcher.find())
                continue;

            mod_id = matcher.group(1);

            if (this.mods.containsKey(mod_id))
                continue;

            this.mods.put(mod_id, new MinecraftMod(mod_id, mod.getName()));
        }
    }

    public void collectModItems()
    {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for (Object io : net.minecraft.item.Item.itemRegistry)
        {
            net.minecraft.item.Item i = (net.minecraft.item.Item)io;

            try {
                // PotionItem's getSubItems method can't be trusted
                if (i == Items.potionitem)
                    PotionHelper.getSubItems(items);
                else
                    i.getSubItems(i, null, items);
            }
            catch (Exception npe)
            {
                // Extra Utilities' Microblocks are known to cause NullPointerExceptions. *Looks in RWTema's general direction*
                // ChickenBones' Microblocks are known to cause NullPointerExceptions. *Looks in ChickenBones's general direction*
            }
        }

        String item_mod_id, unlocalized_name, localized_name;
        Matcher matcher;
        MinecraftMod mod;

        for (ItemStack is : items)
        {
            try {
                matcher = Utilities.ModIDRegex.matcher(Utilities.getModId(is.getItem()));

                if (!matcher.find())
                    continue;

                item_mod_id = matcher.group(1);
                mod = this.mods.get(item_mod_id);

                unlocalized_name = this.registry.getUnlocalizedName(is);
                localized_name = this.registry.getLocalizedName(is);

                mod.getItems()
                        .put(unlocalized_name,
                                new Item(unlocalized_name, localized_name));

            }
            catch (Exception n)
            {
                // Forestry's research notes are known to cause NPEs when getting the localized name. *Looks in Sengir's general direction*
            }
        }
    }

    public void collectFluids()
    {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        MinecraftMod forge_mod = this.mods.get("Forge");
        Fluid fluid;

        for (Map.Entry<String, Fluid> fluid_entry : fluids.entrySet())
        {
            fluid = fluid_entry.getValue();

            forge_mod.getItems().put(
                    fluid_entry.getKey(),
                    new Item(
                            fluid.getUnlocalizedName(),
                            fluid.getLocalizedName(
                                    new FluidStack(fluid, 0)
                            )
                    )
            );
        }
    }

    public void collectRecipeProviders()
    {
        Map<ItemStack, Integer> tools;

        for (IModSupportService service : ServiceLoader.load(IModSupportService.class))
        {
            service.setLocalizationRegistry(this.registry);
            service.setRecipeWrapperRepository(this.wrapper_providers);

            tools = service.getTools();

            if (tools != null)
            {
                for (Map.Entry<ItemStack, Integer> tool : tools.entrySet())
                {
                    String tool_domain = Utilities.getModId(tool.getKey());
                    String tool_name = this.registry.getUnlocalizedName(tool.getKey());

                    this.tools.put(String.format("%s:%s", tool_domain, tool_name), tool.getValue());
                }
            }

            this.support_services.add(service);
        }
    }

    public void collectRecipeFilters()
    {
        List<RecipeFilter> filter_list;

        for (RecipeFilter filter : ServiceLoader.load(RecipeFilter.class))
        {
            if (!filter.shouldActivateFilter())
                continue;

            filter.setLocalizationRegistry(this.registry);

            for (Class recipe_class : filter.getRecipeWrapperClasses())
            {
                if (this.recipe_filters.containsKey(recipe_class))
                    filter_list = this.recipe_filters.get(recipe_class);
                else
                    this.recipe_filters.put(recipe_class, filter_list = new ArrayList<RecipeFilter>());

                filter_list.add(filter);
            }
        }
    }

    public void collectRecipes()
    {
        boolean filter_recipe;
        StackWrapper result;
        Matcher matcher;
        String result_mod_id = null;
        String unlocalized_name = null;

        for (IModSupportService support_service : this.support_services)
        {
            for(RecipeWrapper wrapped_recipe : support_service) {
                try
                {
                    if (wrapped_recipe == null)
                        continue;

                    wrapped_recipe.parse();

                    filter_recipe = false;

                    if (this.recipe_filters.containsKey(wrapped_recipe.getClass()))
                    {
                        for (RecipeFilter filter : this.recipe_filters.get(wrapped_recipe.getClass()))
                        {
                            if (filter_recipe = filter.shouldFilterRecipe(wrapped_recipe))
                                break;
                        }
                    }

                    if (filter_recipe)
                        continue;

                    result = wrapped_recipe.getResult();

                    result_mod_id = result.getOwningMod();
                    unlocalized_name = result.getUnlocalizedName();

                    matcher = Utilities.ModIDRegex.matcher(result_mod_id);

                    if (!matcher.find())
                        continue;

                    result_mod_id = matcher.group(1);

                    this.mods.get(result_mod_id)
                            .getItems()
                            .get(unlocalized_name)
                            .getRecipes()
                            .add(wrapped_recipe);

                }
                catch (Exception e)
                {
                    //
                }
            }
        }
    }

    public Map<String, MinecraftMod> getMods() {
        return mods;
    }

    public String getOredictName(List oredict_list)
    {
        return this.oredict_reverse_lookup.get(oredict_list);
    }

    public HashMap<List, String> getOredictReverseLookup() { return oredict_reverse_lookup; }

    public Map<String, Integer> getSelectedRecipes() { return selected_recipes; }

    public Map<String, Integer> getTools() { return tools; }

    public MinecraftMod getMod(String mod_id)
    {
        return this.mods.get(mod_id);
    }

    public void markAsReady(boolean b) {
        this.ready = b;
    }

    public boolean isReady() {
        return ready;
    }
}
