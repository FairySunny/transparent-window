package icu.sunny.mc.transparentwindow.mixin.client;

import icu.sunny.mc.transparentwindow.client.texture.TransparentTexture;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/util/Identifier;"))
    private static Identifier redirectStaticTexture(String id) {
        return TransparentTexture.TEXTURE;
    }
}
