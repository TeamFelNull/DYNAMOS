package dev.felnull.dynamos.menu.brokenchest;

import dev.felnull.dynamos.Dynamos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.Mod;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

public class BrokenChestScreen extends AbstractContainerScreen<BrokenChestMenu> {

    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(Dynamos.MODID, "textures/gui/broken_chest.png");

    public BrokenChestScreen(BrokenChestMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // ① UV を 0..1 で指定する版（楽）
        graphics.blit(BG,
                x, y,
                x + this.imageWidth, y + this.imageHeight,
                0.0F, 1.0F,
                0.0F, 1.0F);

        // ② 「昔の」使い方に近い版（テクスチャ 256x256 前提）
        // graphics.blit(RenderPipelines.GUI_TEXTURED,
        //        BG,
        //        x, y,
        //        0.0F, 0.0F,
        //        this.imageWidth, this.imageHeight,
        //        256, 256);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(g, mouseX, mouseY, partialTicks);
        super.render(g, mouseX, mouseY, partialTicks);
        this.renderTooltip(g, mouseX, mouseY);
    }
}