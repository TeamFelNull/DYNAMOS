package dev.felnull.dynamos.core.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage applyTint(BufferedImage src, Color tint) {
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
