/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
    @ModifyVariable(method = "renderString", at = @At("HEAD"), ordinal = 0)
    private String renderString(String string) {
        if (string == null || LiquidBounce.eventManager == null)
            return string;

        final TextEvent textEvent = new TextEvent(string);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }

    @ModifyVariable(method = "getStringWidth", at = @At("HEAD"), ordinal = 0)
    private String getStringWidth(String string) {
        if (string == null || LiquidBounce.eventManager == null)
            return string;

        final TextEvent textEvent = new TextEvent(string);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }
    // the below brreaks if u remove it idk why
     @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
     public void drawString(String p_drawString_1_, float p_drawString_2_, float p_drawString_3_, int p_drawString_4_, boolean p_drawString_5_, CallbackInfoReturnable<Integer> cir) {
         if(420 == 69){
             cir.setReturnValue((int) Fonts.font32.drawString(p_drawString_1_,p_drawString_2_,p_drawString_3_,p_drawString_4_,p_drawString_5_));
             cir.cancel();
         }
     }

     @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
     public void getStringWidth(String p_getStringWidth_1_, CallbackInfoReturnable<Integer> cir) {
         if(420 == 69){
             cir.setReturnValue(Fonts.font32.getStringWidth(p_getStringWidth_1_));
             cir.cancel();
         }
     }

}
