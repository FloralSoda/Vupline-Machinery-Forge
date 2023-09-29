//package xyz.hydroxa.vulpine_machinery.mixin;
//
//import net.minecraft.client.player.LocalPlayer;
//import net.minecraft.client.renderer.ItemInHandRenderer;
//import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(ItemInHandLayer.class)
//public class GunRenderMixin {
//    @Inject(method = "evaluateWhichHandsToRender(Lnet/minecraft/client/player/LocalPlayer;)Lnet/minecraft/client/renderer/ItemInHandRenderer$HandRenderSelection;", at = @At("TAIL"))
//    public void whichHands(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
//
//    }
//}
