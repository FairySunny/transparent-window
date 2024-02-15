package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(TabButtonWidget.class)
public class TabButtonWidgetMixin {
    private static final ButtonTextures TAB_BUTTON_TEXTURES = new ButtonTextures(new Identifier("widget/button"), new Identifier("widget/button_disabled"), new Identifier("widget/button_highlighted"));

    @Redirect(method = "renderWidget", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/TabButtonWidget;TAB_BUTTON_TEXTURES:Lnet/minecraft/client/gui/screen/ButtonTextures;"))
    private ButtonTextures redirectRenderWidgetTexture() {
        return TAB_BUTTON_TEXTURES;
    }
}
