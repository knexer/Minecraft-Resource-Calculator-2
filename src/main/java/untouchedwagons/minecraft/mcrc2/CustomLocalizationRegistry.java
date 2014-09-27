package untouchedwagons.minecraft.mcrc2;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.collections.CustomizableHashMap;
import untouchedwagons.minecraft.mcrc2.collections.ItemStackComparer;

import java.util.ArrayList;

public class CustomLocalizationRegistry implements ILocalizationRegistry {
    private CustomizableHashMap<ItemStack, String> registry;
    private CustomizableHashMap<ItemStack, String> name_registry;

    public CustomLocalizationRegistry()
    {
        this.registry = new CustomizableHashMap<ItemStack, String>(new ItemStackComparer());
        this.name_registry = new CustomizableHashMap<ItemStack, String>(new ItemStackComparer());

        registerSingleBlockWithName(Blocks.brown_mushroom, "tile.mushroom.brown", "Brown Mushroom");
        registerSingleBlockWithName(Blocks.red_mushroom, "tile.mushroom.red", "Red Mushroom");
        registerSingleBlockWithName(Blocks.brown_mushroom_block, "tile.mushroomBlock.brown", "Brown Mushroom Block");
        registerSingleBlockWithName(Blocks.red_mushroom_block, "tile.mushroomBlock.red", "Red Mushroom Block");

        registerSingleBlockWithName(Blocks.stone_pressure_plate, "tile.pressurePlate.stone", "Stone Pressure Plate");
        registerSingleBlockWithName(Blocks.wooden_pressure_plate, "tile.pressurePlate.wood", "Wood Pressure Plate");

        registerSingleItemWithName(Items.record_11, "item.record.11", "Record - 11");
        registerSingleItemWithName(Items.record_13, "item.record.13", "Record - 13");
        registerSingleItemWithName(Items.record_blocks, "item.record.blocks", "Record - Blocks");
        registerSingleItemWithName(Items.record_cat, "item.record.cat", "Record - Cat");
        registerSingleItemWithName(Items.record_chirp, "item.record.chirp", "Record - Chirp");
        registerSingleItemWithName(Items.record_far, "item.record.far", "Record - Far");
        registerSingleItemWithName(Items.record_mall, "item.record.mall", "Record - Mall");
        registerSingleItemWithName(Items.record_mellohi, "item.record.mellohi", "Record - Mellohi");
        registerSingleItemWithName(Items.record_stal, "item.record.stal", "Record - Stal");
        registerSingleItemWithName(Items.record_strad, "item.record.strad", "Record - Strad");
        registerSingleItemWithName(Items.record_wait, "item.record.wait", "Record - Wait");
        registerSingleItemWithName(Items.record_ward, "item.record.ward", "Record - Ward");

        registerMultiBlock(Blocks.dirt, new String[]{"tile.dirt", "tile.dirt.podzol"});
        registerMultiBlock(Blocks.planks, new String[]{
                "tile.planks.oak",
                "tile.planks.spruce",
                "tile.planks.birch",
                "tile.planks.jungle",
                "tile.planks.acacia",
                "tile.planks.darkoak"
        });
        registerMultiBlock(Blocks.sapling, new String[]{
                "tile.sapling.oak",
                "tile.sapling.spruce",
                "tile.sapling.birch",
                "tile.sapling.jungle",
                "tile.sapling.acacia",
                "tile.sapling.darkoak"
        });
        registerMultiBlock(Blocks.sand, new String[]{
                "tile.sand.default",
                "tile.sand.red"
        });
        registerMultiBlock(Blocks.log, new String[]{
                "tile.wood.oak",
                "tile.wood.spruce",
                "tile.wood.birch",
                "tile.wood.jungle",
                "tile.wood.acacia",
                "tile.wood.darkoak"
        });
        registerMultiBlock(Blocks.leaves, new String[]{
                "tile.leaves.oak",
                "tile.leaves.spruce",
                "tile.leaves.birch",
                "tile.leaves.jungle"
        });
        registerMultiBlock(Blocks.sandstone, new String[]{
                "tile.sandStone.default",
                "tile.sandStone.chiseled",
                "tile.sandStone.smooth"
        });

        registerSingleBlockWithName(Blocks.wooden_button, "tile.button.wood", "Wood Button");
        registerSingleBlockWithName(Blocks.stone_button, "tile.button.stone", "Stone Button");

        registerMultiItemWithNames(Items.golden_apple,
                new String[]{
                        "item.appleGold.default",
                        "item.appleGold.notch"
                },
                new String[]{
                        "Gold Apple",
                        "Notch Apple"
                });

        // We can't trust PotionItem's getSubItems method to give correct information
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 0), "item.potion.water", "Water Bottle");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16), "item.potion.awkward", "Awkward Potion");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8193), "item.potion.regen", "Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8225), "item.potion.regen.enhanced", "Enhanced Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8257), "item.potion.regen.extended", "Extended Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16385), "item.potion.regen.splash", "Splash Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16417), "item.potion.regen.enhanced.splash", "Enhanced Splash Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16449), "item.potion.regen.extended.splash", "Extended Splash Potion of Regeneration");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8194), "item.potion.speed", "Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8226), "item.potion.speed.enhanced", "Enhanced Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8258), "item.potion.speed.extended", "Extended Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16386), "item.potion.speed.splash", "Splash Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16418), "item.potion.speed.enhanced.splash", "Enhanced Splash Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16450), "item.potion.speed.extended.splash", "Extended Splash Potion of Swiftness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8195), "item.potion.fire", "Potion of Fire Resistance");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8259), "item.potion.fire.extended", "Extended Potion of Fire Resistance");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16387), "item.potion.fire.splash", "Splash Potion of Fire Resistance");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16451), "item.potion.fire.extended.splash", "Extended Splash Potion of Fire Resistance");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8196), "item.potion.poison", "Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8228), "item.potion.poison.enhanced", "Enhanced Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8260), "item.potion.poison.extended", "Extended Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16388), "item.potion.poison.splash", "Splash Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16420), "item.potion.poison.enhanced.splash", "Enhanced Splash Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16452), "item.potion.poison.extended.splash", "Extended Splash Potion of Poison");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8197), "item.potion.healing", "Potion of Healing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8229), "item.potion.healing.enhanced", "Enhanced Potion of Healing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16389), "item.potion.healing.splash", "Splash Potion of Healing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16421), "item.potion.healing.enhanced.splash", "Enhanced Splash Potion of Healing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8198), "item.potion.vision", "Potion of Night Vision");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8262), "item.potion.vision.extended", "Extended Potion of Night Vision");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16390), "item.potion.vision.splash", "Splash Potion of Night Vision");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16454), "item.potion.vision.extended.splash", "Extended Splash Potion of Night Vision");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8200), "item.potion.weakness", "Potion of Weakness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8264), "item.potion.weakness.extended", "Extended Potion of Weakness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16392), "item.potion.weakness.splash", "Splash Potion of Weakness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16456), "item.potion.weakness.splash.extended", "Extended Splash Potion of Weakness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8201), "item.potion.strength", "Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8233), "item.potion.strength.enhanced", "Enhanced Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8265), "item.potion.strength.extended", "Extended Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16393), "item.potion.strength.splash", "Splash Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16425), "item.potion.strength.enhanced.splash", "Enhanced Splash Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16457), "item.potion.strength.extended.splash", "Extended Splash Potion of Strength");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8202), "item.potion.slowness", "Potion of Slowness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8266), "item.potion.slowness.extended", "Extended Potion of Slowness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16394), "item.potion.slowness.splash", "Splash Potion of Slowness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16458), "item.potion.slowness.splash.extended", "Extended Splash Potion of Slowness");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8204), "item.potion.harming", "Potion of Harming");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8236), "item.potion.harming.enhanced", "Enhanced Potion of Harming");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16396), "item.potion.harming.splash", "Splash Potion of Harming");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16428), "item.potion.harming.enhanced.splash", "Enhanced Splash Potion of Harming");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8205), "item.potion.breathing", "Potion of Water Breathing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8269), "item.potion.breathing.extended", "Extended Potion of Water Breathing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16397), "item.potion.breathing.splash", "Splash Potion of Water Breathing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16461), "item.potion.breathing.extended.splash", "Extended Splash Potion of Water Breathing");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8206), "item.potion.invisibility", "Potion of Invisibility");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 8270), "item.potion.invisibility.extended", "Extended Potion of Invisibility");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16398), "item.potion.invisibility.splash", "Splash Potion of Invisibility");
        this.registerItemStackWithName(new ItemStack(Items.potionitem, 1, 16462), "item.potion.invisibility.extended.splash", "Extended Splash Potion of Invisibility");
    }

    public void registerSingleBlock(Block b, String unlocalized_name)
    {
        registerSingleItem(Item.getItemFromBlock(b), unlocalized_name);
    }

    public void registerSingleBlockWithName(Block b, String unlocalized_name, String name)
    {
        registerSingleItemWithName(Item.getItemFromBlock(b), unlocalized_name, name);
    }

    public void registerMultiBlock(Block b, String[] unlocalized_names)
    {
        registerMultiItem(Item.getItemFromBlock(b), unlocalized_names);
    }

    public void registerMultiBlockWithNames(Block b, String[] unlocalized_names, String[] names)
    {
        registerMultiItemWithNames(Item.getItemFromBlock(b), unlocalized_names, names);
    }

    public void registerSingleItem(Item i, String unlocalized_name)
    {
        ArrayList<ItemStack> sub_items = new ArrayList<ItemStack>();

        i.getSubItems(i, null, sub_items);

        this.registry.put(sub_items.get(0), unlocalized_name);
    }

    public void registerSingleItemWithName(Item i, String unlocalized_name, String name)
    {
        ArrayList<ItemStack> sub_items = new ArrayList<ItemStack>();

        i.getSubItems(i, null, sub_items);

        this.registry.put(sub_items.get(0), unlocalized_name);
        this.name_registry.put(sub_items.get(0), name);
    }

    public void registerMultiItem(Item i, String[] unlocalized_names)
    {
        ArrayList<ItemStack> sub_items = new ArrayList<ItemStack>();

        i.getSubItems(i, null, sub_items);

        for (int j = 0; j < sub_items.size(); j++)
        {
            this.registry.put(sub_items.get(j), unlocalized_names[j]);
        }
    }

    public void registerMultiItemWithNames(Item i, String[] unlocalized_names, String[] names)
    {
        ArrayList<ItemStack> sub_items = new ArrayList<ItemStack>();

        i.getSubItems(i, null, sub_items);

        for (int j = 0; j < sub_items.size(); j++)
        {
            this.registry.put(sub_items.get(j), unlocalized_names[j]);
            this.name_registry.put(sub_items.get(j), names[j]);
        }
    }

    public void registerItemStack(ItemStack stack, String unlocalized_name)
    {
        this.registerItemStackWithName(stack, unlocalized_name, stack.getDisplayName());
    }

    public void registerItemStackWithName(ItemStack stack, String unlocalized_name, String name)
    {
        this.registry.put(stack, unlocalized_name);
        this.name_registry.put(stack, name);
    }

    public String getUnlocalizedName(ItemStack is)
    {
        StringBuilder builder = new StringBuilder();

        if (this.registry.containsKey(is))
            builder.append(this.registry.get(is));
        else
            builder.append(is.getUnlocalizedName());

        if (is.getHasSubtypes())
        {
            builder.append(":");
            builder.append(is.getItemDamage());
        }

        return builder.toString();
    }

    public String getLocalizedName(ItemStack is)
    {
        if (this.name_registry.containsKey(is))
            return this.name_registry.get(is);
        else
            return is.getDisplayName();
    }
}
