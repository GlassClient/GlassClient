/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Animations;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends MixinGuiScreen {
    @Shadow
    protected int xSize;
    @Shadow
    protected int ySize;
    @Shadow
    protected int guiLeft;
    @Shadow
    protected int guiTop;

    private long guiOpenTime = -1;
    private boolean translated = false;

    @Shadow
    protected abstract boolean checkHotbarKeys(int keyCode);

    @Shadow private int dragSplittingButton;
    @Shadow private int dragSplittingRemnant;

    @Inject(method = "initGui", at = @At("RETURN"))
    private void initGuiReturn(CallbackInfo callbackInfo) {
        guiOpenTime = System.currentTimeMillis();
    }

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    private void drawScreenHead(CallbackInfo callbackInfo) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen guiScreen = mc.currentScreen;
            mc.currentScreen.drawWorldBackground(0);

            final Animations animations = Animations.INSTANCE;
            double pct = Math.max(animations.getInvTimeValue().get() - (System.currentTimeMillis() - guiOpenTime), 0) / ((double) animations.getInvTimeValue().get());
            if (pct != 0) {
                GL11.glPushMatrix();

                pct = EaseUtils.INSTANCE.apply(EaseUtils.EnumEasingType.valueOf(animations.getInvEaseModeValue().get()),
                        EaseUtils.EnumEasingOrder.valueOf(animations.getInvEaseOrderModeValue().get()), pct);

                switch (animations.getInvModeValue().get().toLowerCase()) {
                    case "slide": {
                        GL11.glTranslated(0, -(guiTop + ySize) * pct, 0);
                        break;
                    }
                    case "zoom": {
                        double scale = 1 - pct;
                        GL11.glScaled(scale, scale, scale);
                        GL11.glTranslated(((guiLeft + (xSize * 0.5 * pct)) / scale) - guiLeft,
                                ((guiTop + (ySize * 0.5d * pct)) / scale) - guiTop,
                                0);
                    }
                }

                translated = true;
            }

    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void checkCloseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        if (mouseButton - 100 == mc.gameSettings.keyBindInventory.getKeyCode()) {
            mc.thePlayer.closeScreen();
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void checkHotbarClicks(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        checkHotbarKeys(mouseButton - 100);
    }

    @Inject(method = "updateDragSplitting", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void fixRemnants(CallbackInfo ci) {
        if (this.dragSplittingButton == 2) {
            this.dragSplittingRemnant = mc.thePlayer.inventory.getItemStack().getMaxStackSize();
            ci.cancel();
        }
    }

    @Inject(method = "drawScreen", at = @At("RETURN"))
    private void drawScreenReturn(CallbackInfo callbackInfo) {
        if (translated) {
            GL11.glPopMatrix();
            translated = false;
        }
    }

}



