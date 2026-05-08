package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(PanoramaRenderer.class)
public abstract class PanoramaRendererMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;render(Lnet/minecraft/client/Minecraft;FF)V"))
    private boolean shouldDrawCubeMap(CubeMap cubeMap, Minecraft minecraft, float x, float y) {
        return false;
    }
}
