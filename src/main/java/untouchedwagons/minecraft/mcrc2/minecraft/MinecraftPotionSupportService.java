package untouchedwagons.minecraft.mcrc2.minecraft;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.api.mods.IModSupportService;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.potion.PotionRecipe;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.potion.PotionRecipeWrapper;

import java.util.Iterator;
import java.util.Map;

public class MinecraftPotionSupportService implements Iterator<RecipeWrapper>, IModSupportService {
    private ILocalizationRegistry registry;

    private PotionRecipeWrapper[] potion_recipes;
    private int pos;

    public MinecraftPotionSupportService()
    {
        this.potion_recipes = new PotionRecipeWrapper[60];
    }

    @Override
    public void setLocalizationRegistry(ILocalizationRegistry registry) {
        this.registry = registry;
        this.createRecipes();
    }

    @Override
    public void setRecipeWrapperRepository(Map<Class<? extends IRecipe>, Class<? extends RecipeWrapper>> wrapper_providers) {

    }

    @Override
    public boolean shouldActivateService() {
        return true;
    }

    @Override
    public Map<ItemStack, Integer> getTools() {
        return null;
    }

    @Override
    public Iterator<RecipeWrapper> iterator() {
        this.pos = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.pos < this.potion_recipes.length;
    }

    @Override
    public RecipeWrapper next() {
        return this.potion_recipes[this.pos++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void createRecipes()
    {
        int i = 0;

        // Awkward Potion
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16, 0, new ItemStack(Items.nether_wart)), registry);
        // Regen
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8193, 16, new ItemStack(Items.ghast_tear)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8225, 8193, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8257, 8193, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16385, 8193, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16417, 8225, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16449, 8257, new ItemStack(Items.gunpowder)), registry);
        // Speed
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8194, 16, new ItemStack(Items.sugar)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8226, 8194, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8258, 8194, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16386, 8194, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16418, 8226, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16450, 8258, new ItemStack(Items.gunpowder)), registry);
        // Fire Resist
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8195, 16, new ItemStack(Items.magma_cream)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8259, 8195, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16387, 8195, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16451, 8259, new ItemStack(Items.gunpowder)), registry);
        // Poison
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8196, 16, new ItemStack(Items.spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8228, 8196, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8260, 8196, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16388, 8196, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16420, 8228, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16452, 8260, new ItemStack(Items.gunpowder)), registry);
        // Healing
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8197, 16, new ItemStack(Items.speckled_melon)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8229, 8197, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16389, 8197, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16421, 8229, new ItemStack(Items.gunpowder)), registry);
        // Night Vision
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8198, 16, new ItemStack(Items.golden_carrot)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8262, 8198, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16390, 8198, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16454, 8262, new ItemStack(Items.gunpowder)), registry);
        // Weakness
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8200, 0, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8264, 8200, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16392, 8200, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16456, 8264, new ItemStack(Items.gunpowder)), registry);
        // Strength
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8201, 16, new ItemStack(Items.blaze_powder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8233, 8201, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8265, 8201, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16393, 8201, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16425, 8233, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16457, 8265, new ItemStack(Items.nether_wart)), registry);
        // Slowness
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8202, 8194, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8202, 8195, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8266, 8202, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16394, 8202, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16458, 8266, new ItemStack(Items.gunpowder)), registry);
        // Harming
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8204, 8196, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8204, 8197, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8204, 8205, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8236, 8204, new ItemStack(Items.glowstone_dust)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16396, 8204, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16428, 8236, new ItemStack(Items.gunpowder)), registry);
        // Water Breathing
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8205, 16, new ItemStack(Items.fish, 1, 3)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8269, 8205, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16397, 8205, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16461, 8269, new ItemStack(Items.gunpowder)), registry);
        // Invisibility
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8206, 8198, new ItemStack(Items.fermented_spider_eye)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(8270, 8206, new ItemStack(Items.redstone)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16398, 8206, new ItemStack(Items.gunpowder)), registry);
        this.potion_recipes[i++] = new PotionRecipeWrapper(new PotionRecipe(16462, 8270, new ItemStack(Items.gunpowder)), registry);
        // Are your eyes spinning? Mine are
    }
}
