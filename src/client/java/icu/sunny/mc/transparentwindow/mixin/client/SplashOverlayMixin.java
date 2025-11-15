package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(SplashOverlay.class)
public abstract class SplashOverlayMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private boolean shouldRenderScreen(Screen screen, DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        return MinecraftClient.getInstance().world != null;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private boolean shouldFillBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        return MinecraftClient.getInstance().world != null;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "clearColorTexture", remap = false))
    private boolean shouldClearBackground(CommandEncoder commandEncoder, GpuTexture texture, int color) {
        return MinecraftClient.getInstance().world != null;
    }
}
