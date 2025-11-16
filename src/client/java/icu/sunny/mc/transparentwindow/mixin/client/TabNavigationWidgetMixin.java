package icu.sunny.mc.transparentwindow.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(TabNavigationWidget.class)
public abstract class TabNavigationWidgetMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private boolean shouldFillBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        return false;
    }
}
