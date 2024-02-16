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
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/client/gui/screen/ButtonTextures;"))
    private static ButtonTextures redirectStaticTexture(Identifier enabled, Identifier disabled, Identifier enabledFocused, Identifier disabledFocused) {
        return new ButtonTextures(new Identifier("widget/button"), new Identifier("widget/button_disabled"), new Identifier("widget/button_highlighted"));
    }
}
