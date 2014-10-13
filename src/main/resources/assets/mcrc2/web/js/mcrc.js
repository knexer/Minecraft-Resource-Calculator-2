
var mod_data = {};
var mod_list = {};

var current_crafting_tree;

function get_item_namespace(item)
{
    return item.substr(0, item.indexOf(":"));
}

function get_mod_list ()
{
    var mod_list = {};

    $.ajax({
        url: "mod-list",
        type: "GET",
        dataType : "json",
        async: false,
        success: function(obj) {
            mod_list = obj;
        },
        statusCode: {
            503: function() {
                alert("HTTP 503 - This is probably because the Resource Calculator isn't ready.")
            }
        }
    });

    return mod_list;
}

function get_item_list (mod_to_load)
{
    if (mod_data[mod_to_load]["items"].length)
        return mod_data[mod_to_load]["items"];

    $.ajax({
        url: "item-list/" + mod_to_load,
        type: "GET",
        dataType : "json",
        async: false,
        success: function(obj) {
            $.each (obj, function (unlocalized_name, data)
            {
                mod_data[mod_to_load]["items"][unlocalized_name] = {
                    id: unlocalized_name,
                    name: data['name'],
                    hidden: data['hidden']
                };
            });
        },
        statusCode: {
            503: function() {
                alert("HTTP 503 - This is probably because the Resource Calculator isn't ready.")
            }
        }
    });

    return mod_data[mod_to_load]["items"];
}

function initialize_mod_data(data)
{
    mod_list = data;

    $.each (data, function (mod_id, mod_name)
    {
        mod_data[mod_id] = {
            name: mod_name,
            items: {}
        };
    });
}

function process_craft ()
{
    var item = $("#item-list").val();
    var amount = $("#amount").val();
    var number_regex = /^\d+$/;

    var item_ns = get_item_namespace(item);

    if (mod_data[item_ns]["items"][item] == null) return;
    if (number_regex.test(amount) == false) return;

    $.ajax({
        url: "craft/" + item + "/" + amount,
        type: "GET",
        dataType : "json",
        async: false,
        success: function(crafting_tree) {
            current_crafting_tree = crafting_tree;

            var details_elem = $("#details");

            if (crafting_tree["recipe-count"] == 0)
            {
                details_elem.empty().append($("<p>").append("It seems this item has no recipes."));
                return;
            }

            var temp = $("<ul>");

            details_elem.empty().append(temp);

            display_crafting_tree(crafting_tree, temp);

            var base_item_list = {};

            prepare_base_item_list(crafting_tree, base_item_list);

            display_summary(base_item_list);
        },
        error: function() {
            alert("Could not get crafting tree. Sorry.");
        }
    });
}

function prepare_base_item_list (crafting_tree, list)
{
    // If the item was leftover from another step or was hidden
    // we simply ignore it
    if (crafting_tree['excess']) return;

    if (crafting_tree['ingredients'].length && !crafting_tree['hidden'])
    {
        $.each (crafting_tree['ingredients'], function (ingredient, obj)
        {
            prepare_base_item_list (obj, list);
        });

        return;
    }

    if (list[crafting_tree['item']] == null)
        list[crafting_tree['item']] = 0;

    list[crafting_tree['item']] += crafting_tree['amount'];
}

function display_mod_list ()
{
    var sorted_mod_list = [];

    $.each (mod_list, function (mod_ns){
        if (mod_ns == "minecraft") return;

        sorted_mod_list.push(mod_ns);
    });

    sorted_mod_list.sort();
    sorted_mod_list.unshift("minecraft");

    var mods_list_select = $("#mod-list");
    var mods_list_inputs = [];

    $.each (sorted_mod_list, function (pos, mod) {
        mods_list_inputs.push ($("<option>", { value: mod }).append(mod_list[mod]));
    });

    mods_list_select.append(mods_list_inputs);
}

function display_item_list (mod_to_load)
{
    var mod_item_list = get_item_list(mod_to_load);

    var item_list_select = $("#item-list").empty();
    var items_list = [];

    $.each(mod_item_list, function (item, data) {
        if (data['hidden'])
            return;

        items_list.push(data);
    });

    items_list.sort(function (a, b) {
        if (a["name"] < b["name"])
            return -1;
        if (a["name"] > b["name"])
            return 1;
        return 0;
    });

    $.each(items_list, function (pos, item_data) {
        item_list_select.append($("<option>", { value: item_data["id"] }).append(item_data["name"]));
    });
}

function display_crafting_tree (crafting_tree, container)
{
    var temp = $("<li>");
    container.append(temp);

    var item = crafting_tree['item'];
    var item_ns = get_item_namespace(item);
    var item_list = get_item_list(item_ns);
    var item_text = item_list[item]['name'] + " X " + crafting_tree['amount'];
    var recipe_count = crafting_tree['recipe-count'];

    // Base items would have a recipe_count of 0
    if (recipe_count && crafting_tree['excess'] == false)
    {
        temp.addClass("sub-recipes");

        var span = $("<span>", { class: "tree-collapse down" }).append("&nbsp;").click(function ()
        {
            crafting_tree['hidden'] = !crafting_tree['hidden'];

            if ($(this).hasClass("down"))
                $(this).removeClass("down").addClass("right");
            else
                $(this).removeClass("right").addClass("down");

            $(this).siblings(".change-recipe").toggle();
            $(this).siblings("ul").toggle();

            var updated_base_item_list = {};

            prepare_base_item_list(current_crafting_tree, updated_base_item_list);

            display_summary(updated_base_item_list);
        });

        temp.append(span);
    }

    temp.append(item_text);

    if (crafting_tree["ingredients"].length)
    {
        if (crafting_tree['recipe']['machine'])
        {
            var machine_int_name = crafting_tree['recipe']['machine'];
            var s = "This recipe requires the use of a " + machine_int_name;

            temp.append("&nbsp;");
            temp.append($("<img>", { "src": "images/info.png", "title": s }));
        }
        if (crafting_tree['recipe']['extra-information'])
        {
            temp.append("&nbsp;");
            temp.append($("<img>", { "src": "images/warning.png", "title": crafting_tree['recipe']['extra-information'] }));
        }
    }

    if (recipe_count > 1)
    {
        temp.append($("<img>", { "src": "images/gear.png" }).addClass("change-recipe").click(function ()
        {
            var selected_recipies = $.cookie("selected-recipes");

            if (selected_recipies == null)
                selected_recipies = {};

            if (selected_recipies[item] == null)
                selected_recipies[item] = 0;

            if (selected_recipies[item] + 1 == recipe_count)
                selected_recipies[item] = 0;
            else
                selected_recipies[item]++;

            console.log(selected_recipies[item]);

            $.cookie('selected-recipes', selected_recipies, { expires: 7 });

            process_craft();

        }).prop("title", "This item has multiple recipes, click this gear to choose another."));
    }

    if (crafting_tree["ingredients"].length)
    {
        var ingredient_container = $("<ul>");
        temp.append(ingredient_container);

        $.each (crafting_tree["ingredients"], function (ingredient)
        {
            display_crafting_tree(crafting_tree["ingredients"][ingredient], ingredient_container)
        });
    }
}

function display_summary (base_items_needed)
{
    var container_obj = $("#summary").empty();

    var ul_container = $("<ul>");
    container_obj.append(ul_container);

    $.each (base_items_needed, function (ingredient, ingredient_count)
    {
        var item_ns = get_item_namespace(ingredient);

        var item_text = mod_data[item_ns]["items"][ingredient];
        item_text += " X ";
        item_text += ingredient_count;

        ul_container.append($("<li>").append(item_text));
    });
}

$(document).ready(function() {
    $("#tabs").tabs();

    //$.cookie.json = true;

    initialize_mod_data(get_mod_list());
    display_mod_list();
    display_item_list("minecraft");

    $("#mod-list").change(function()
    {
        display_item_list($("#mod-list").val());
    }).change();

    $("#item-list").change(function()
    {
        process_craft();
    }).change();

    $("#amount").change(function()
    {
        process_craft();
    }).change();
});
