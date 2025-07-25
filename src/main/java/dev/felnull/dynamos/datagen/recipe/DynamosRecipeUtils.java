package dev.felnull.dynamos.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Map;

public class DynamosRecipeUtils {

    public static JsonObject makeShapedRecipe(ResourceLocation id, Item result, int count, String[] pattern, Map<Character, Item> keys) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");

        JsonArray patternArray = new JsonArray();
        for (String line : pattern) {
            patternArray.add(line);
        }
        recipe.add("pattern", patternArray);

        JsonObject keyObject = new JsonObject();
        for (Map.Entry<Character, Item> entry : keys.entrySet()) {
            String itemId = BuiltInRegistries.ITEM.getKey(entry.getValue()).toString();
            keyObject.addProperty(entry.getKey().toString(), itemId); // ← ここをシンプルに
        }
        recipe.add("key", keyObject);

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("id", BuiltInRegistries.ITEM.getKey(result).toString()); // ← "id" に修正
        if (count > 1) resultObj.addProperty("count", count);
        recipe.add("result", resultObj);

        return recipe;
    }

    public static JsonObject makeShapelessRecipe(ResourceLocation id, Item result, int count, List<Item> ingredients) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shapeless");
        recipe.addProperty("category", "misc");

        JsonArray ingredientsArray = new JsonArray();
        for (Item item : ingredients) {
            String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
            ingredientsArray.add(itemId); // ← シンプルに itemId だけ
        }
        recipe.add("ingredients", ingredientsArray);

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("id", BuiltInRegistries.ITEM.getKey(result).toString());
        if (count > 1) resultObj.addProperty("count", count);
        recipe.add("result", resultObj);

        return recipe;
    }
}
