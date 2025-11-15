package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@SuppressWarnings("unused")
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void clearFramebuffer(CallbackInfo info) {
        MinecraftClient.getInstance().getFramebuffer().clear(MinecraftClient.IS_SYSTEM_MAC);
    }

    private static void drawFramebufferWithAlpha(Framebuffer framebuffer, int width, int height) {
        RenderSystem.assertOnRenderThread();
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, width, height);
        GlStateManager._disableBlend();

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ShaderProgram shaderProgram = Objects.requireNonNull(minecraftClient.gameRenderer.blitScreenProgram, "Blit shader not loaded");
        shaderProgram.addSampler("DiffuseSampler", framebuffer.getColorAttachment());
        shaderProgram.bind();
        BufferBuilder bufferBuilder = RenderSystem.renderThreadTesselator().begin(VertexFormat.DrawMode.QUADS, VertexFormats.BLIT_SCREEN);
        bufferBuilder.vertex(0.0F, 0.0F, 0.0F);
        bufferBuilder.vertex(1.0F, 0.0F, 0.0F);
        bufferBuilder.vertex(1.0F, 1.0F, 0.0F);
        bufferBuilder.vertex(0.0F, 1.0F, 0.0F);
        BufferRenderer.draw(bufferBuilder.end());
        shaderProgram.unbind();
        GlStateManager._depthMask(true);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;draw(II)V"))
    private void drawFramebuffer(Framebuffer framebuffer, int width, int height, boolean tick) {
        if (tick && MinecraftClient.getInstance().world != null) {
            framebuffer.draw(width, height);
            GlStateManager._colorMask(false, false, false, true);
            GlStateManager._clearColor(0, 0, 0, 1);
            GlStateManager._clear(GlConst.GL_COLOR_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
            GlStateManager._colorMask(true, true, true, true);
        } else {
            drawFramebufferWithAlpha(framebuffer, width, height);
        }
    }
}
