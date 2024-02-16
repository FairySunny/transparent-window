package icu.sunny.mc.transparentwindow.client.texture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public class TransparentTexture {
    public static final Identifier TEXTURE = new Identifier("transparentwindow", "textures/transparent");

    public static void register() {
        NativeImage img = new NativeImage(1, 1, false);
        img.setColor(0, 0, 0);
        img.untrack();
        MinecraftClient.getInstance().getTextureManager().registerTexture(TEXTURE, new NativeImageBackedTexture(img));
    }
}
