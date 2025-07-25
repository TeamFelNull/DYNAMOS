package dev.felnull.dynamos.util;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DataProviderUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(DataProviderUtil.class);

    public static CompletableFuture<?> saveImage(CachedOutput cache, RenderedImage renderedImage, Path path) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.debug("Trying to save image to {}", path);
            path.toFile().getParentFile().mkdirs();
            try {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), bytearrayoutputstream);

                if(!ImageIO.write(renderedImage, "PNG", hashingoutputstream)) {
                    LOGGER.error("Failed to write rendered image.");
                    return;
                }

                hashingoutputstream.flush();
                LOGGER.debug("ByteArray Size: {}", bytearrayoutputstream.size());

                cache.writeIfNeeded(path, bytearrayoutputstream.toByteArray(), hashingoutputstream.hash());
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }

        }, Util.backgroundExecutor().forName("saveImage"));
    }
}
