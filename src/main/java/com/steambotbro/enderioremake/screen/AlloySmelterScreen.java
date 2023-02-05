package com.steambotbro.enderioremake.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.steambotbro.enderioremake.EnderioRemake;
import com.steambotbro.enderioremake.screen.renderer.EnergyInfoArea;
import com.steambotbro.enderioremake.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class AlloySmelterScreen extends AbstractContainerScreen<AlloySmelterMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(EnderioRemake.MOD_ID, "textures/gui/alloy_smelter_auto.png");

    private EnergyInfoArea energyInfoArea;

    public AlloySmelterScreen(AlloySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 16, y + 14, menu.blockEntity.getEnergyStorage(), 9, 42);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);

        //super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, 16, 14, 9, 42)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.f, 1.f, 1.f, 1.f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pPoseStack, x, y, 56, 49);
        renderProgressArrow(pPoseStack, x, y, 104, 49);
        energyInfoArea.draw(pPoseStack);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, int offsetX, int offsetY) {
        if (menu.isCrafting()) {
            blit(pPoseStack, x + offsetX, y + offsetY - menu.getScaledProgress(), 176, 14 - menu.getScaledProgress(), 14, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
