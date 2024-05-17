package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void injectRenderPanoramaBackground(CallbackInfo info) {
        info.cancel();
    }
}
