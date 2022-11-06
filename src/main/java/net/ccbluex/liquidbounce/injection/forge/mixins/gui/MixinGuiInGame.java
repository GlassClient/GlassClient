/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Animations;
import net.ccbluex.liquidbounce.features.module.modules.client.HUD;
import net.ccbluex.liquidbounce.features.module.modules.mods.BetterHotbar;
import net.ccbluex.liquidbounce.features.module.modules.mods.ClearView;
import net.ccbluex.liquidbounce.features.module.modules.mods.Crosshair;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(GuiIngame.class)
public abstract class MixinGuiInGame extends MixinGui {

    @Shadow
    protected abstract void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player);

    @Shadow
    @Final
    protected static ResourceLocation widgetsTexPath;

    @Shadow
    @Final
    protected GuiPlayerTabOverlay overlayPlayerList;

    @Shadow
    @Final
    protected Minecraft mc;

    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    private void renderScoreboard(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(HUD.class).getState())
            callbackInfo.cancel();
    }

    /**
     * @author liulihaocai
     */
    @Overwrite
    protected void renderTooltip(ScaledResolution sr, float partialTicks) {
        final HUD hud = LiquidBounce.moduleManager.getModule(HUD.class);
        final BetterHotbar HotbarSettings = LiquidBounce.moduleManager.getModule(BetterHotbar.class);
        final EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();

        float tabHope = this.mc.gameSettings.keyBindPlayerList.isKeyDown() ? 1f : 0f;
        final Animations animations = Animations.INSTANCE;
        if(animations.getTabHopePercent() != tabHope) {
            animations.setLastTabSync(System.currentTimeMillis());
            animations.setTabHopePercent(tabHope);
        }
        if(animations.getTabPercent() > 0 && tabHope == 0) {
            overlayPlayerList.renderPlayerlist(sr.getScaledWidth(), mc.theWorld.getScoreboard(), mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0));
        }

        if(Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            String hotbarType = HotbarSettings.getHotbarValue().get();
            Minecraft mc = Minecraft.getMinecraft();
            int middleScreen = sr.getScaledWidth() / 2;
            GlStateManager.resetColor();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(widgetsTexPath);
            int i = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            this.zLevel = -90.0F;
            GlStateManager.resetColor();
            int itemX = i - 91 + HotbarSettings.INSTANCE.getHotbarEasePos(entityplayer.inventory.currentItem * 20);
            float posInv = 91 - i + itemX;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if(hotbarType == "Rise") {
                GlStateManager.disableTexture2D();
                RenderUtils.quickDrawRect(i - 91, sr.getScaledHeight() - 22, i + 91, sr.getScaledHeight(), new Color(0, 0, 0, HotbarSettings.INSTANCE.getHotbarAlphaValue().get()));
                RenderUtils.quickDrawRect(itemX, sr.getScaledHeight() - 22, itemX + 22, sr.getScaledHeight() - 21, ColorUtils.INSTANCE.rainbow());
                RenderUtils.quickDrawRect(itemX, sr.getScaledHeight() - 21, itemX + 22, sr.getScaledHeight(), new Color(0, 0, 0, HotbarSettings.INSTANCE.getHotbarAlphaValue().get()));
                GlStateManager.enableTexture2D();
            } else if(hotbarType == "Full") {
                GlStateManager.disableTexture2D();
                RenderUtils.quickDrawRect(0, sr.getScaledHeight() - 23, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, HotbarSettings.INSTANCE.getHotbarAlphaValue().get()));
                RenderUtils.quickDrawRect(itemX, sr.getScaledHeight() - 23, itemX + 22, sr.getScaledHeight() - 21, ColorUtils.INSTANCE.rainbow());
                RenderUtils.quickDrawRect(itemX, sr.getScaledHeight() - 21, itemX + 22, sr.getScaledHeight(), new Color(0, 0, 0, HotbarSettings.INSTANCE.getHotbarAlphaValue().get()));
                GlStateManager.enableTexture2D();
            } else if (hotbarType == "Rounded") {
                RenderUtils.originalRoundedRect(middleScreen - 91, sr.getScaledHeight() - 2, middleScreen + 91, sr.getScaledHeight() - 22, 3F, Integer.MIN_VALUE);
                RenderUtils.originalRoundedRect(middleScreen - 91 + posInv, sr.getScaledHeight() - 2, middleScreen - 91 + posInv + 22, sr.getScaledHeight() - 22, 3F, Integer.MAX_VALUE);
            } else if (hotbarType == "LB") {
                RenderUtils.quickDrawRect(middleScreen - 91, sr.getScaledHeight() - 24, middleScreen + 90, sr.getScaledHeight(), Integer.MIN_VALUE);
                RenderUtils.quickDrawRect(middleScreen - 91 - 1 + posInv + 1, sr.getScaledHeight() - 24, middleScreen - 91 - 1 + posInv + 22, sr.getScaledHeight() - 22 - 1 + 24, Integer.MAX_VALUE);
            } else if (hotbarType == "Minecraft") {
                this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
                this.drawTexturedModalRect(itemX - 1, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
                RenderHelper.enableGUIStandardItemLighting();
                for (int j = 0; j < 9; ++j) {
                    int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                    int l = sr.getScaledHeight() - 19;
                    this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
                }
                RenderHelper.disableStandardItemLighting();
            }
            this.zLevel = f;
            RenderHelper.enableGUIStandardItemLighting();
            if (hotbarType == "Rounded") {
                for (int j = 0; j < 9; ++j) {
                    int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                    int l = sr.getScaledHeight() - 20;
                    this.renderHotbarItem(j, k, l, partialTicks, entityplayer);

                }
            } else {
                for (int j = 0; j < 9; ++j) {
                    int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                    int l = sr.getScaledHeight() - 19;
                    this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
                }
            }
            RenderHelper.disableStandardItemLighting();
        //}

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
        LiquidBounce.eventManager.callEvent(new Render2DEvent(partialTicks, StaticStorage.scaledResolution));
    }

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    private void renderPumpkinOverlay(final CallbackInfo callbackInfo) {
        final ClearView ClearView = LiquidBounce.moduleManager.getModule(ClearView.class);

        if(ClearView.getState() && ClearView.getPumpkinEffectValue().get())
            callbackInfo.cancel();
    }

    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    private void renderBossHealth(CallbackInfo callbackInfo) {
        final ClearView ClearView = (ClearView) LiquidBounce.moduleManager.getModule(ClearView.class);
        if (ClearView.getState() && ClearView.getBossHealth().get())
            callbackInfo.cancel();
    }

    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    private void injectCrosshair(CallbackInfoReturnable<Boolean> cir) {
        final Crosshair crossHair = LiquidBounce.moduleManager.getModule(Crosshair.class);
        if (crossHair.getState())
            cir.setReturnValue(false);
    }
}
