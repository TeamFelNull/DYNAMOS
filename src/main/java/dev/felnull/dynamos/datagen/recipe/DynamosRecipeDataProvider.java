package dev.felnull.dynamos.datagen.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.register.DynamosIngotEnum;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DynamosRecipeDataProvider implements DataProvider {

    private final PackOutput output;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public DynamosRecipeDataProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return CompletableFuture.runAsync(() -> {
            for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
                String baseName = ingot.itemName;
                Item nugget = ingot.getNugget().getRegisteredItem().get();
                Item ingotItem = ingot.entry.getRegisteredItem().get();
                Item block = ingot.getBlock().getRegisteredBlock().get().asItem();

                // Nugget → Ingot
                JsonObject nuggetToIngot = DynamosRecipeUtils.makeShapedRecipe(
                        ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, baseName + "_from_nuggets"),
                        ingotItem, 1,
                        new String[]{"###", "###", "###"},
                        Map.of('#', nugget)
                );
                saveRecipe(cache, nuggetToIngot, baseName + "_from_nuggets");

                // Ingot → Nugget
                JsonObject ingotToNugget = DynamosRecipeUtils.makeShapelessRecipe(
                        ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, baseName + "_to_nuggets"),
                        nugget, 9,
                        List.of(ingotItem)
                );
                saveRecipe(cache, ingotToNugget, baseName + "_to_nuggets");

                // Ingot → Block
                JsonObject ingotToBlock = DynamosRecipeUtils.makeShapedRecipe(
                        ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, baseName + "_block_from_ingots"),
                        block, 1,
                        new String[]{"###", "###", "###"},
                        Map.of('#', ingotItem)
                );
                saveRecipe(cache, ingotToBlock, baseName + "_block_from_ingots");

                // Block → Ingot
                JsonObject blockToIngot = DynamosRecipeUtils.makeShapelessRecipe(
                        ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, baseName + "_block_to_ingots"),
                        ingotItem, 9,
                        List.of(block)
                );
                saveRecipe(cache, blockToIngot, baseName + "_block_to_ingots");
            }
        });
    }

    private void saveRecipe(CachedOutput cache, JsonObject recipe, String name) {
        //なぜか生成物が消えるのでmainに直生成するためにコメント化
//        Path path = output.getOutputFolder()
//                .resolve("data")
//                .resolve(Dynamos.MODID)
//                .resolve("recipe") // ← このまま使うなら後で recipes に移動することを忘れずに
//                .resolve(name + ".json");
        Path path = Paths.get("../src", "main", "resources", "data", Dynamos.MODID, "recipe", name + ".json");
        try {
            Files.createDirectories(path.getParent()); // フォルダ生成
            Files.write(path, recipe.toString().getBytes(StandardCharsets.UTF_8)); // 強制書き込み
            System.out.println("Writing to: " + path.toAbsolutePath());
            System.out.println("📄 Absolute output path: " + path.toAbsolutePath());
            System.out.println("[DynamosRecipeDataProvider] FORCED write: " + path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write recipe: " + name, e);
        }
    }

    @Override
    public @NotNull String getName() {
        return "Dynamos Recipes";
    }
}

