
var cached_item_lists = {};
var machine_list = {};

var current_crafting_tree;

function get_item_namespace(item)
{
    var namespace = item.substr(0, item.indexOf(":"));

    if (namespace == "")
        namespace = "mc";

    return namespace;
}

function get_machine_list ()
{
    $.ajax({
        url: "machine-list",
        type: "GET",
        dataType : "json",
        async: false,
        success: function(machine_list_obj) {
            machine_list = machine_list_obj;
        },
        error: function() {
            alert("Could not load machine list. Sorry.");
        }
    });
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
        error: function() {
            alert("Could not load mod list. Sorry.");
        }
    });

    return mod_list;
}

function get_item_list (mod_to_load)
{
    if (cached_item_lists[mod_to_load])
        return cached_item_lists[mod_to_load];

    $.ajax({
        url: "item-list/" + mod_to_load,
        type: "GET",
        dataType : "json",
        async: false,
        success: function(obj) {
            cached_item_lists[mod_to_load] = obj;
        },
        error: function() {
            alert("Could not load item list. Sorry.");
        }
    });

    return cached_item_lists[mod_to_load];
}

function get_item_use_list (item)
{
    var item_use_list = [];

    $.ajax({
        url: "uses/" + item,
        type: "GET",
        dataType : "json",
        async: false,
        success: function (obj) {
            item_use_list = obj;
        },
        error: function() {
            alert("Could not load item use list. Sorry.");
        }
    });

    return item_use_list;
}

function process_craft ()
{
    var item = $("#item-list").val();
    var amount = $("#amount").val();
    var number_regex = /^\d+$/;

    var item_ns  = get_item_namespace(item);

    if (cached_item_lists[item_ns][item] == null) return;
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
                details_elem.empty().append($("<p>").append("It seems this item has no recipes. Maybe the 'Item Uses' tab has something useful?"));
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
    var mod_list = get_mod_list();

    var sorted_mod_list = [];

    $.each (mod_list, function (mod_ns){
        if (mod_ns == "mc") return;

        sorted_mod_list.push(mod_ns);
    });

    sorted_mod_list.sort();
    sorted_mod_list.unshift("mc");

    var mods_list_select = $("#mod-list");
    var mods_list_inputs = [];

    $.each (sorted_mod_list, function (pos, mod) {
        mods_list_inputs.push ($("<option>", { value: mod }).append(mod_list[mod]));
    });

    mods_list_select.append(mods_list_inputs);
}

function display_item_list (mod_to_load)
{
    var mod_item_list = get_item_list (mod_to_load);

    var item_list_select = $("<select>", { id: "item-list" });
    var items_list = [];

    $.each(mod_item_list, function (item) {
        items_list.push(item);
    });
    items_list.sort();

    var item_list_inputs = [];
    $.each(items_list, function (pos, item) {
        var item_name = mod_item_list[item];

        item_list_inputs.push($("<option>", { value: item }).append(item_name));
    });

    var parent_container = $("#item-list").parent("").empty();
    item_list_select.append(item_list_inputs);
    parent_container.append(item_list_select);

    item_list_select.chosen({width: "30%"}).on('change', function(){
        process_craft();
        display_item_usages();
    }).change();
}

function display_crafting_tree (crafting_tree, container)
{
    var temp = $("<li>");
    container.append(temp);

    var item = crafting_tree['item'];
    var item_ns = get_item_namespace(item);
    var item_list = get_item_list(item_ns);
    var item_text = item_list[item] + " X " + crafting_tree['amount'];
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
            var machine_ext_name = machine_list[machine_int_name];

            var s = "This recipe requires the use of a " + machine_ext_name;

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

        var item_text = cached_item_lists[item_ns][ingredient];
        item_text += " X ";
        item_text += ingredient_count;

        ul_container.append($("<li>").append(item_text));
    });
}

function display_item_usages ()
{
    var item = $("#item-list").val();
    var item_use_list = get_item_use_list(item);

    var f = function (item_use_list, pos)
    {
        var ingredients = $("<ul>");
        var uses_elem = $("#uses").empty();

        var result_item = item_use_list[pos];

        $.each (result_item["recipe"]["ingredients"], function (ingredient, ingredient_count) {
            var ingredient_ns = get_item_namespace(ingredient);

            if (cached_item_lists[ingredient_ns] == null)
                get_item_list(ingredient_ns);

            var ingredient_text = cached_item_lists[ingredient_ns][ingredient];
            ingredient_text += " X ";
            ingredient_text += ingredient_count;

            ingredients.append ($("<li>", { class: "sub-recipes" }).append(ingredient_text));
        });

        var result_ns = get_item_namespace(result_item["name"]);

        if (cached_item_lists[result_ns] == null)
            get_item_list(result_ns);

        var result_name = cached_item_lists[result_ns][result_item["name"]];
        var breadcrumb_result_name = result_name;
        result_name += " X ";
        result_name += result_item["recipe"]["produces"];

        var result = $("<li>", { class: "sub-recipes" }).append(result_name).append(ingredients);
        var recipe = $("<ul>", { class: "use-list" }).append(result);

        uses_elem.append(recipe);

        if (item_use_list.length > 1) {
            var prev_pos = (pos == 0) ? item_use_list.length - 1 : pos - 1;
            var next_pos = (pos == item_use_list.length - 1) ? 0 : pos + 1;

            var prev_li = $("<li>").append($("<a>").append("<<-- Previous").click(function (){
                f (item_use_list, prev_pos);
            }));
            var item_li = $("<li>").append(breadcrumb_result_name);
            var next_li = $("<li>").append($("<a>").append("Next -->>").click (function (){
                f (item_use_list, next_pos);
            }));

            var usages_breadcrumb = $("<ul>", { class: "use-list-breadcrumb" })
                                    .append(prev_li)
                                    .append(item_li)
                                    .append(next_li);

            uses_elem.append(usages_breadcrumb);
        }
    };

    if (item_use_list.length)
        f (item_use_list, 0);
    else
        $("#uses").empty().append($("<p>").append("It seems this item is not used in anything."));
}

$(document).ready(function() {
    $("#tabs").tabs();

    $.cookie.json = true;

    get_machine_list();
    display_mod_list();

    $("#mod-list").chosen({
        width: "30%"
    }).change(function () {
        display_item_list($(this).children("option:selected").val());
    }).change();

    $("#amount").spinner({
        stop: process_craft
    });

    $("a.ui-spinner-button").removeClass("ui-state-default");
});
