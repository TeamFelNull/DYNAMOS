package dev.felnull.dynamos.datagen;

import dev.felnull.dynamos.register.DynamosIngotEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TintedTextureGenerator {

    private static final File OUTPUT_DIR = new File("../src/main/resources/assets/dynamos/textures/block");


    public static void generateAll() {
        try {
            // 必要なら途中のフォルダも含めて全部作成
            if (!OUTPUT_DIR.exists() && !OUTPUT_DIR.mkdirs()) {
                System.err.println("❌ textures/block フォルダの作成に失敗したにゃ！");
                return;
            }

            InputStream stream = TintedTextureGenerator.class.getClassLoader().getResourceAsStream(
                    "assets/dynamos/textures/block/material_block_base.png");

            if (stream == null) {
                throw new FileNotFoundException("Could not find base texture!");
            }

            BufferedImage base = ImageIO.read(stream);


            for (DynamosIngotEnum ingot : DynamosIngotEnum.values()) {
                BufferedImage tinted = applyTint(base, ingot.color);
                File output = new File(OUTPUT_DIR, ingot.itemName + "_block_tinted.png");

                // 画像書き込み時の成功確認も念のため
                boolean success = ImageIO.write(tinted, "PNG", output);
                if (!output.exists()) {
                    System.err.println("❌ Broken owowowowo: " + output.getAbsolutePath());
                } else {
                    System.out.println("✅ VeryveryGoodSuccess foo: " + output.getAbsolutePath());
                }
                if (!success) {
                    System.err.println("❌ fuck: " + output.getPath());
                } else {
                    System.out.println("[TintedTextureGenerator] Generated: " + output.getPath());
                }
                System.out.println("zettai path: " + output.getCanonicalPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyTint(BufferedImage src, Color tint) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int rgba = src.getRGB(x, y);
                Color base = new Color(rgba, true);

                // RGBを乗算してティント適用
                int r = base.getRed()   * tint.getRed()   / 255;
                int g = base.getGreen() * tint.getGreen() / 255;
                int b = base.getBlue()  * tint.getBlue()  / 255;

                Color result = new Color(r, g, b, base.getAlpha());
                out.setRGB(x, y, result.getRGB());
            }
        }
        return out;
    }
}
