package icu.sunny.mc.transparentwindow.mixin.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import icu.sunny.mc.transparentwindow.client.texture.TransparentTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerTexture(CallbackInfo info) {
        TransparentTexture.register();
    }

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
        ShaderProgram shaderProgram = minecraftClient.gameRenderer.blitScreenProgram;
        shaderProgram.addSampler("DiffuseSampler", framebuffer.getColorAttachment());
        Matrix4f matrix4f = new Matrix4f().setOrtho(0.0f, width, height, 0.0f, 1000.0f, 3000.0f);
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorter.BY_Z);
        if (shaderProgram.modelViewMat != null) {
            shaderProgram.modelViewMat.set(new Matrix4f().translation(0.0f, 0.0f, -2000.0f));
        }
        if (shaderProgram.projectionMat != null) {
            shaderProgram.projectionMat.set(matrix4f);
        }
        shaderProgram.bind();
        float h = (float)framebuffer.viewportWidth / (float)framebuffer.textureWidth;
        float i = (float)framebuffer.viewportHeight / (float)framebuffer.textureHeight;
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0, height, 0.0).texture(0.0f, 0.0f).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(width, height, 0.0).texture(h, 0.0f).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(width, 0.0, 0.0).texture(h, i).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(0.0, 0.0, 0.0).texture(0.0f, i).color(255, 255, 255, 255).next();
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
