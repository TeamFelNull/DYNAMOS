package dev.felnull.dynamos.core.datagen;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.enums.DynamosIngotEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class DynamosRecipeProvider extends RecipeProvider {
    private static Logger logger = LoggerFactory.getLogger(DynamosRecipeProvider.class);

    public DynamosRecipeProvider(HolderLookup.Provider p_360573_, RecipeOutput recipeOutput) {
        super(p_360573_, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        logger.info("Registering recipes now...");
        for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
            registerMaterialRecipe(ingot);
            logger.info("Registered recipe for {}", ingot.name());
        }


        shaped(RecipeCategory.MISC, Items.APPLE, 10)
                .define('I', Items.DIAMOND)
                .define('K', Items.REDSTONE)
                .define('S', Items.APPLE)
                .pattern("IKS")
                .pattern("III")
                .pattern("III")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output, getRecipeKey("create_apple_form_diamond"));

        // this.cookRecipes("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);


        logger.info("Recipe registration completed!");
    }

    protected void registerMaterialRecipe(
            DynamosIngotEnum material
    ) {
        String baseName = material.itemName;
        biCompressRecipe(baseName, "nuggets", material.getRegisteredNugget(), material.getRegisteredIngot());
        biCompressRecipe(baseName + "_block", "ingot", material.getRegisteredIngot(), material.getRegisteredBlock());
    }

    protected void biCompressRecipe(
            String prefix,
            String suffix,
            ItemLike from,
            ItemLike to
    ) {
        threeByThreeRecipe(RecipeCategory.MISC, to, from)
                .save(output, getRecipeKey(prefix + "_from_" + suffix));
        shapeless(RecipeCategory.MISC, from, 9)
                .requires(to)
                .unlockedBy("has_item", has(to))
                .save(output, getRecipeKey(prefix + "_to_" + suffix));
    }

    protected ResourceKey<Recipe<?>> getRecipeKey(@NotNull String path) {
        return ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, path));
    }

    protected ShapedRecipeBuilder threeByThreeRecipe(
            RecipeCategory recipeCategory,
            ItemLike outputItem,
            ItemLike ingredientItem
    ) {
        return shaped(recipeCategory, outputItem)
                .define('#', ingredientItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_output", has(outputItem))
                .unlockedBy("has_ingredient", has(ingredientItem));
    }

    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new DynamosRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Dynamos Recipe Provider";
        }
    }
}
