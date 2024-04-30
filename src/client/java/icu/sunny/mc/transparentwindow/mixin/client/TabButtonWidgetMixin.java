package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(TabButtonWidget.class)
public class TabButtonWidgetMixin {
    @Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawNineSlicedTexture(Lnet/minecraft/util/Identifier;IIIIIIIIIIII)V"))
    private void redirectRenderButtonDraw(DrawContext context, Identifier texture, int x, int y, int width, int height, int leftSliceWidth, int topSliceHeight, int rightSliceWidth, int bottomSliceHeight, int centerSliceWidth, int centerSliceHeight, int u, int v) {
        int i = v / 24 + 1;
        if (i >= 3) i = 0;
        context.drawNineSlicedTexture(ClickableWidget.WIDGETS_TEXTURE, x, y, width, height, 20, 4, 200, 20, 0, 46 + i * 20);
    }
}
