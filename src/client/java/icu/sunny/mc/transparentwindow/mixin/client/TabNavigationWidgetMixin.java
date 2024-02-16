package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(TabNavigationWidget.class)
public class TabNavigationWidgetMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void redirectRenderFill(DrawContext context, int x1, int y1, int x2, int y2, int color) {
    }
}
