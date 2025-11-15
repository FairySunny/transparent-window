package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void clearFramebuffer(CallbackInfo info) {
        MinecraftClient.getInstance().getFramebuffer().clear();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gl/Framebuffer;draw(II)V"))
    private void setFinalAlpha(boolean tick, CallbackInfo info) {
        if (tick && MinecraftClient.getInstance().world != null) {
            GlStateManager._colorMask(false, false, false, true);
            GlStateManager._clearColor(0, 0, 0, 1);
            GlStateManager._clear(GlConst.GL_COLOR_BUFFER_BIT);
            GlStateManager._colorMask(true, true, true, true);
        }
    }
}
