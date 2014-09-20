package untouchedwagons.minecraft.mcrc2.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Registry to store and get custom localization info incase multiple things
 * share the same unlocalized or localized name
 */
public interface ILocalizationRegistry {
    /**
     * Register a single Block with a custom unlocalized name
     * @param net.minecraft.block.Block b
     * @param java.lang.String unlocalized_name
     */
    public void registerSingleBlock(Block b, String unlocalized_name);
    /**
     * Register a single Block with a custom unlocalized and localized name
     * @param net.minecraft.block.Block b
     * @param java.lang.String unlocalized_name
     * @param java.lang.String name
     */
    public void registerSingleBlockWithName(Block b, String unlocalized_name, String name);

    /**
     * Register a block that has subtypes (e.g. Planks) with custom unlocalized names
     * @param net.minecraft.block.b
     * @param java.lang.String[] unlocalized_names
     */
    public void registerMultiBlock(Block b, String[] unlocalized_names);

    /**
     * Register a block that has subtypes (e.g. Planks) with custom unlocalized and localized names
     * @param net.minecraft.block.b
     * @param java.lang.String[] unlocalized_names
     * @param java.lang.String[] names
     */
    public void registerMultiBlockWithNames(Block b, String[] unlocalized_names, String[] names);

    /**
     * Register a single item with a custom unlocalized name
     * @param net.minecraft.item.Item i
     * @param java.lang.String unlocalized_name
     */
    public void registerSingleItem(Item i, String unlocalized_name);

    /**
     * Register a single item with a custom unlocalized and localized name
     * @param net.minecraft.item.Item i
     * @param java.lang.String unlocalized_name
     * @param java.lang.String name
     */
    public void registerSingleItemWithName(Item i, String unlocalized_name, String name);

    /**
     * Register an item that has subtypes (e.g. Planks) with custom unlocalized names
     * @param net.minecraft.item.Item i
     * @param java.lang.String unlocalized_names
     */
    public void registerMultiItem(Item i, String[] unlocalized_names);

    /**
     * Register an item that has subtypes (e.g. dyes) with custom unlocalized and localized names
     * @param net.minecraft.item.Item i
     * @param java.lang.String[] unlocalized_names
     * @param java.lang.String[] names
     */
    public void registerMultiItemWithNames(Item i, String[] unlocalized_names, String[] names);

    /**
     * Get the unlocalized name of the supplied ItemStack, if none was ever supplied
     * then the name supplied by the ItemStack will be returned
     * @param net.minecraft.item.ItemStack is
     * @return
     */
    public String getUnlocalizedName(ItemStack is);

    /**
     * Get the localized name of the supplied ItemStack, if none was ever supplied
     * then the name supplied by the ItemStack will be returned
     * @param net.minecraft.item.ItemStack is
     * @return
     */
    public String getLocalizedName(ItemStack is);
}
