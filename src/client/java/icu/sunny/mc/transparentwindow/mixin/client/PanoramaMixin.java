package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.renderer.Panorama;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.state.gui.PanoramaRenderState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(Panorama.class)
public abstract class PanoramaMixin {
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "FIELD", target = "panoramaRenderState:Lnet/minecraft/client/renderer/state/gui/PanoramaRenderState;", opcode = Opcodes.PUTFIELD))
    private boolean shouldRenderCubeMap(GuiRenderState state, PanoramaRenderState panoramaRenderState) {
        return false;
    }
}
