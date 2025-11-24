package dev.felnull.dynamos.core.datagen.texture;

import dev.felnull.dynamos.Dynamos;
import dev.felnull.dynamos.enums.DynamosIngotEnum;
import dev.felnull.dynamos.core.util.DataProviderUtil;
import dev.felnull.dynamos.core.util.ImageUtil;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
/*

public class TintedTextureGenerator implements DataProvider {
    private final PackOutput output;

    public TintedTextureGenerator(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path folderPath = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(Dynamos.MODID).resolve("textures").resolve("block");

                InputStream stream = TintedTextureGenerator.class.getClassLoader().getResourceAsStream(
                        "assets/dynamos/textures/block/material_block_base.png");

                if (stream == null) {
                    throw new FileNotFoundException("Could not find base texture!");
                }

                BufferedImage base = ImageIO.read(stream);


                for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
                    BufferedImage tinted = ImageUtil.applyTint(base, ingot.color);
                    DataProviderUtil.saveImage(cachedOutput, tinted, folderPath.resolve(ingot.itemName + "_block_tinted.png")).join();
                }
            } catch (Exception e) {
                LOGGER.error("Failed to process texture", e);
            }
        });
    }


    @Override
    public String getName() {
        return "Tinted Texture Generator";
    }
}
*/
