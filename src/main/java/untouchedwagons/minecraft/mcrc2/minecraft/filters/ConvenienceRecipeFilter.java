package untouchedwagons.minecraft.mcrc2.minecraft.filters;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.recipes.RecipeWrapper;
import untouchedwagons.minecraft.mcrc2.api.recipes.filters.RecipeFilter;
import untouchedwagons.minecraft.mcrc2.minecraft.recipes.standard.ShapedRecipesWrapper;

public class ConvenienceRecipeFilter extends RecipeFilter {
    @Override
    public Class[] getRecipeWrapperClasses() {
        return new Class[] { ShapedRecipesWrapper.class};
    }

    @Override
    public boolean shouldFilterRecipe(RecipeWrapper recipe) {
        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.iron_ingot, 9), new ItemStack(Blocks.iron_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.gold_ingot, 9), new ItemStack(Blocks.gold_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_nugget, 9)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.diamond, 9), new ItemStack(Blocks.diamond_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.redstone, 9), new ItemStack(Blocks.redstone_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.coal, 9), new ItemStack(Blocks.coal_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.emerald, 9), new ItemStack(Blocks.emerald_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.wheat, 9), new ItemStack(Blocks.wheat)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.dye, 9, 4), new ItemStack(Blocks.lapis_block)))
            return true;

        if (this.isConvenienceRecipe(recipe, new ItemStack(Items.quartz, 4), new ItemStack(Blocks.quartz_block)))
            return true;

        return false;
    }
}
