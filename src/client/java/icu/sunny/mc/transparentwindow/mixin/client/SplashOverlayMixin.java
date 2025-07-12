package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void redirectRenderScreen(Screen screen, DrawContext context, int mouseX, int mouseY, float delta) {
        if (MinecraftClient.getInstance().world != null) {
            screen.render(context, mouseX, mouseY, delta);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V"))
    private void redirectRenderFill(DrawContext context, RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
        if (MinecraftClient.getInstance().world != null) {
            context.fill(layer, x1, y1, x2, y2, color);
        } else {
            GlStateManager._clearColor(0, 0, 0, 0);
            GlStateManager._clear(GlConst.GL_COLOR_BUFFER_BIT);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "_clearColor", remap = false))
    private void redirectRenderClearColor(float red, float green, float blue, float alpha) {
        if (MinecraftClient.getInstance().world != null) {
            GlStateManager._clearColor(red, green, blue, alpha);
        } else {
            GlStateManager._clearColor(0, 0, 0, 0);
        }
    }
}
