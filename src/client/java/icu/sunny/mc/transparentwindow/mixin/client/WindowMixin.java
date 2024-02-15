package icu.sunny.mc.transparentwindow.mixin.client;

import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(Window.class)
public class WindowMixin {
	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "glfwCreateWindow", remap = false))
	private void injectConstructor(CallbackInfo info) {
		GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, GLFW.GLFW_TRUE);
	}
}
