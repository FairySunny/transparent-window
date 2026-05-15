package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderStateWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V"))
    private boolean shouldRenderScreen(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks) {
        return Minecraft.getInstance().level != null;
    }

    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private boolean shouldFillBackground(GuiGraphicsExtractor graphics, int x1, int y1, int x2, int y2, int color) {
        return Minecraft.getInstance().level != null;
    }

    @WrapWithCondition(method = "extractRenderState", at = @At(value = "FIELD", target = "clearColorOverride:I", opcode = Opcodes.PUTFIELD))
    private boolean shouldClearBackground(GuiRenderState state, int clearColorOverride) {
        return Minecraft.getInstance().level != null;
    }
}
