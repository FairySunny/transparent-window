package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "runTick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;blitToScreen()V"))
    private void setFinalAlpha(boolean tick, CallbackInfo info) {
        if (tick && Minecraft.getInstance().level != null) {
            GlStateManager._colorMask(false, false, false, true);
            GL11.glClearColor(0, 0, 0, 1);
            GlStateManager._clear(GlConst.GL_COLOR_BUFFER_BIT);
        }
    }
}
