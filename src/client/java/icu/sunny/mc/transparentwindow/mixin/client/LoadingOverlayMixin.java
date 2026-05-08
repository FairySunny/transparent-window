package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private boolean shouldRenderScreen(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float deltaTicks) {
        return Minecraft.getInstance().level != null;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private boolean shouldFillBackground(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
        return Minecraft.getInstance().level != null;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "clearColorTexture", remap = false))
    private boolean shouldClearBackground(CommandEncoder commandEncoder, GpuTexture texture, int color) {
        return Minecraft.getInstance().level != null;
    }
}
