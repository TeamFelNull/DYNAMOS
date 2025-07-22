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
    public static final ModelTemplate TINTED_CUBE_ALL = new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "block/cube_all")), Optional.empty(), TextureSlot.ALL) {
        @Override
        public JsonElement createBaseModel(Block block, TextureMapping mapping) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", "minecraft:block/cube_all");

            JsonObject textures = new JsonObject();
            ResourceLocation texture = mapping.get(TextureSlot.ALL);
            JsonObject textureWithTint = new JsonObject();
            textureWithTint.addProperty("texture", texture.toString());
            textureWithTint.addProperty("tintindex", 0);
            textures.add("all", textureWithTint);

            model.add("textures", textures);
            return model;
        }
    };
}
