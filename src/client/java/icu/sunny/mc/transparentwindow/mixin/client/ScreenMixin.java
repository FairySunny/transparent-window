package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    protected abstract void renderDarkening(DrawContext context);

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void injectRenderPanoramaBackground(DrawContext context, float deltaTicks, CallbackInfo info) {
        if (MinecraftClient.getInstance().world == null) {
            renderDarkening(context);
            info.cancel();
        }
    }
}
