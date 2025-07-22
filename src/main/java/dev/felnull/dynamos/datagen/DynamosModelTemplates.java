package dev.felnull.dynamos.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Optional;

public class DynamosModelTemplates {
    public static final ModelTemplate TINTED_CUBE_ALL = new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all")), Optional.empty(), TextureSlot.ALL) {

        @Override
        public JsonObject createBaseTemplate(ResourceLocation modelPath, Map<TextureSlot, ResourceLocation> textureMap) {
            // Fake mappingから TextureMapping に戻して createBaseModel に渡すにゃ
            TextureMapping mapping = new TextureMapping();
            textureMap.forEach(mapping::put);

            return createBaseModel(null, mapping); // block は null でもいい（未使用なら）
        }

        public JsonObject createBaseModel(Block block, TextureMapping mapping) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", "minecraft:block/cube_all");

            JsonObject textures = new JsonObject();
            ResourceLocation tex = mapping.get(TextureSlot.ALL);

            JsonObject tintLayer = new JsonObject();
            tintLayer.addProperty("texture", tex.toString());
            tintLayer.addProperty("tintindex", 0);
            textures.add("all", tintLayer);

            model.add("textures", textures);
            return model;
        }
    };

}
