/*
 * GlassClient PVP Client
 * A free open source mixin-based injection pvp client based on liquidbounce with  all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.launch.data.modernui.mainmenu;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.utils.MainMenuButton;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class ModernGuiMainMenu extends GuiScreen {
    public ArrayList butt = new ArrayList();
    private float currentX;
    private float currentY;
    private ScaledResolution res;

    public void initGui() {
        this.butt.clear();
        this.butt.add(new MainMenuButton(this, 0, "G", "SinglePlayer", () -> {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }));
        this.butt.add(new MainMenuButton(this, 1, "H", "MultiPlayer", () -> {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }));
        this.butt.add(new MainMenuButton(this, 2, "I", "AltManager", () -> {
            this.mc.displayGuiScreen(new GuiAltManager(this));
        }));
        this.butt.add(new MainMenuButton(this, 3, "J", "Mods", () -> {
            this.mc.displayGuiScreen(new GuiModList(this));
        }, 0.5F));
        this.butt.add(new MainMenuButton(this, 4, "K", "Options", () -> {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }));
        this.butt.add(new MainMenuButton(this, 5, "L", "Languages", () -> {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }));
        this.butt.add(new MainMenuButton(this, 6, "M", "Quit", () -> {
            this.mc.shutdown();
        }));
        this.res = new ScaledResolution(this.mc);
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            this.drawGradientRect(0, 0, this.width, this.height, 16777215, 16777215);
            int h = this.height;
            int w = this.width;
            float xDiff = ((float) (mouseX - h / 2) - this.currentX) / (float) this.res.getScaleFactor();
            float yDiff = ((float) (mouseY - w / 2) - this.currentY) / (float) this.res.getScaleFactor();
            this.currentX += xDiff * 0.3F;
            this.currentY += yDiff * 0.3F;
            GlStateManager.translate(this.currentX / 30.0F, this.currentY / 15.0F, 0.0F);
            RenderUtils.drawImage(new ResourceLocation("fdpclient/background.png"), -30, -30, this.res.getScaledWidth() + 60, this.res.getScaledHeight() + 60);
            GlStateManager.translate(-this.currentX / 30.0F, -this.currentY / 15.0F, 0.0F);
            FontLoaders.F40.drawCenteredString("GlassClient",(float)this.width / 2.0F,(float)this.height / 2.0F - 55.0F,new Color(255,255,255).getRGB());
            ParticleUtils.drawParticles(mouseX, mouseY);
            float startX = (float) this.width / 2.0F - 64.5F * ((float) this.butt.size() / 2.0F);

            for (Iterator var9 = this.butt.iterator(); var9.hasNext(); startX += 75.0F) {
                MainMenuButton button = (MainMenuButton) var9.next();
                button.draw(startX, (float) this.height / 2.0F + 20.0F, mouseX, mouseY);
            }
            FontLoaders.F40.drawCenteredString("GlassClient",(float)this.width / 2.0F,(float)this.height / 2.0F - 55.0F,new Color(255,255,255).getRGB());
            FontLoaders.F18.drawCenteredString(LiquidBounce.INSTANCE.CLIENT_VERSION,(float)this.width / 2.0F,(float)this.height / 2.0F - 30.0F,new Color(255,255,255).getRGB());

        }catch (Exception e){
            e.printStackTrace();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Iterator var4 = this.butt.iterator();

        while(var4.hasNext()) {
            MainMenuButton button = (MainMenuButton)var4.next();
            button.mouseClick(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.res = new ScaledResolution(this.mc);
        super.updateScreen();
    }
}
