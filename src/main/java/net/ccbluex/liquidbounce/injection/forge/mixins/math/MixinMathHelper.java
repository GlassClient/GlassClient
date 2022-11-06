/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.math;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.mods.BetterFPS;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MathHelper.class)
public class MixinMathHelper {
    @Inject(method = "sin", at = @At("HEAD"), cancellable = true)
    private static void sin(float value, CallbackInfoReturnable<Float> callbackInfoReturnable){
        Float result=LiquidBounce.moduleManager.getModule(BetterFPS.class).sin(value);

        if(result!=null)
            callbackInfoReturnable.setReturnValue(result);
    }

    @Inject(method = "cos", at = @At("HEAD"), cancellable = true)
    private static void cos(float value, CallbackInfoReturnable<Float> callbackInfoReturnable){
        Float result=LiquidBounce.moduleManager.getModule(BetterFPS.class).cos(value);

        if(result!=null)
            callbackInfoReturnable.setReturnValue(result);
    }
}