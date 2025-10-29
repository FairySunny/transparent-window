package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gl/Framebuffer;blitToScreen()V"))
    private void setFinalAlpha(boolean tick, CallbackInfo info) {
        if (tick && MinecraftClient.getInstance().world != null) {
            GlStateManager._colorMask(false, false, false, true);
            GL11.glClearColor(0, 0, 0, 1);
            GlStateManager._clear(GlConst.GL_COLOR_BUFFER_BIT);
        }
    }
}
