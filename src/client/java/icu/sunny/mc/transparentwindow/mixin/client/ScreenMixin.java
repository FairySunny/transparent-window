package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "renderDarkening(Lnet/minecraft/client/gui/DrawContext;IIII)V", at = @At("HEAD"), cancellable = true)
    private void injectRenderDarkening(CallbackInfo info) {
        if (MinecraftClient.getInstance().world == null) {
            info.cancel();
        }
    }
}
