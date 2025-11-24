package dev.felnull.dynamos.core.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DynamosModelTemplates {
    public static final ModelTemplate TINTED_CUBE_ALL = new ModelTemplate(
            Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all")),
            Optional.empty(),
            TextureSlot.ALL
    ) {
        @Override
        public JsonObject createBaseTemplate(ResourceLocation modelPath, Map<TextureSlot, ResourceLocation> textureMap) {
            TextureMapping mapping = new TextureMapping();
            textureMap.forEach(mapping::put);

            return createBaseModel(mapping);
        }

        public JsonObject createBaseModel(TextureMapping mapping) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", "minecraft:block/cube_all");

            // textures セクションは通常通り
            JsonObject textures = new JsonObject();
            textures.addProperty("all", mapping.get(TextureSlot.ALL).toString());
            model.add("textures", textures);

            // elements セクションを追加して tintindex を各面に指定
            JsonObject face = new JsonObject();
            face.addProperty("texture", "#all");
            face.addProperty("tintindex", 0);

            JsonObject faces = new JsonObject();
            for (String dir : List.of("north", "south", "east", "west", "up", "down")) {
                faces.add(dir, face.deepCopy());
            }

            JsonObject element = new JsonObject();
            element.add("from", JsonUtils.arrayOf(0, 0, 0));
            element.add("to", JsonUtils.arrayOf(16, 16, 16));
            element.add("faces", faces);

            model.add("elements", JsonUtils.arrayOf(element));
            return model;
        }
    };

    // JsonUtils はユーティリティ：arrayOf(...) とか用意しておくと便利
    public static class JsonUtils {
        public static JsonArray arrayOf(int... values) {
            JsonArray arr = new JsonArray();
            for (int v : values) arr.add(v);
            return arr;
        }

        public static JsonArray arrayOf(JsonObject... objects) {
            JsonArray arr = new JsonArray();
            for (JsonObject o : objects) arr.add(o);
            return arr;
        }
    }
}

