/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.launch.data.modernui.clickgui.style.styles;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.launch.data.modernui.ClickGUIModule;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.Panel;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.style.Style;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.ui.i18n.LanguageManager;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.value.*;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class GlowStyle extends Style {

    private boolean mouseDown;
    private boolean rightMouseDown;

    @Override
    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        // draw header
        RenderUtils.newDrawRect((float) panel.getX() - 3, (float) panel.getY() -1, (float) panel.getX() + panel.getWidth() + 3, (float) panel.getY() + 18, new Color(0,0,0,96).getRGB());
        // draw panel background
        RenderUtils.newDrawRect((float) panel.getX() - 0, (float) panel.getY() + 18, (float) panel.getX() + panel.getWidth(), (float) panel.getY() + 22 + panel.getFade(), new Color(0,0,0,80).getRGB());
        // draw bottom bar
        if (panel.getFade() > 0) {
        RenderUtils.drawGradientRect(panel.getX() - 0, (float) panel.getY() + 22 + panel.getFade(), (float) panel.getX() + panel.getWidth(), (float) panel.getY() + 19 + panel.getFade(), ClickGUIModule.generateColor().getRGB(), new Color(0,0,0,20).getRGB());
        RenderUtils.drawGradientRect(panel.getX() - 0, (float) panel.getY() + 25 + panel.getFade(), (float) panel.getX() + panel.getWidth(), (float) panel.getY() + 27 + panel.getFade(), ClickGUIModule.generateColor().getRGB(), new Color(0,0,0,20).getRGB());
            int guiColor = ClickGUIModule.generateColor().getRGB();
        RenderUtils.newDrawRect((float) panel.getX() - 0, (float) panel.getY() + 22 + panel.getFade(), (float) panel.getX() + panel.getWidth(), (float) panel.getY() + 25 + panel.getFade(), guiColor);
        } else {
        RenderUtils.drawGradientRect(panel.getX() - 3, (float) panel.getY() + 22, (float) panel.getX() + panel.getWidth() + 3, (float) panel.getY() + 19 + panel.getFade(), ClickGUIModule.generateColor().getRGB(), new Color(0,0,0,20).getRGB());
        RenderUtils.drawGradientRect(panel.getX() - 3, (float) panel.getY() + 25, (float) panel.getX() + panel.getWidth() + 3, (float) panel.getY() + 27 + panel.getFade(), ClickGUIModule.generateColor().getRGB(), new Color(0,0,0,20).getRGB());
            int guiColor = ClickGUIModule.generateColor().getRGB();
        RenderUtils.newDrawRect((float) panel.getX() - 3, (float) panel.getY() + 22, (float) panel.getX() + panel.getWidth() + 3, (float) panel.getY() + 25 + panel.getFade(), guiColor); 
        }
        // draw title
        float textWidth = Fonts.font35.getStringWidth("§f" + StringUtils.stripControlCodes(LanguageManager.INSTANCE.get(panel.getName().replaceAll("%",""))));
        Fonts.font35.drawString("§f" + LanguageManager.INSTANCE.get(panel.getName().replaceAll("%","")), (int) panel.getX() + 10, panel.getY() + 7, -16777216);
        // draw scrollbar
       /* if(panel.getScrollbar() && panel.getFade() > 0) {
            RenderUtils.drawRect(panel.getX() - 2, panel.getY() + 21, panel.getX(), panel.getY() + 16 + panel.getFade(), Integer.MAX_VALUE);
            RenderUtils.drawRect(panel.getX() - 2, panel.getY() + 30 + (panel.getFade() - 24F) / (panel.getElements().size() - LiquidBounce.moduleManager.getModule(ClickGUIModule.class).maxElementsValue.get()) * panel.getDragged() - 10.0f, panel.getX(), panel.getY() + 40 + (panel.getFade() - 24.0f) / (panel.getElements().size() - LiquidBounce.moduleManager.getModule(ClickGUIModule.class).maxElementsValue.get()) * panel.getDragged(), Integer.MIN_VALUE);
        }*/ 
    }

    @Override
    public void drawDescription(int mouseX, int mouseY, String text) {
        // int textWidth = Fonts.font35.getStringWidth(LanguageManager.INSTANCE.get(text.replaceAll("%","")));

      //  RenderUtils.drawBorderedRect(mouseX + 9, mouseY, mouseX + textWidth + 14, mouseY + Fonts.font35.FONT_HEIGHT + 3, 1, new Color(255, 255, 255, 90).getRGB(), Integer.MIN_VALUE);
      //  GlStateManager.resetColor();
      //  Fonts.font35.drawString(LanguageManager.INSTANCE.get(text.replaceAll("%","")), mouseX + 12, mouseY + (Fonts.font35.FONT_HEIGHT) / 2, Integer.MAX_VALUE);
    } 

    @Override
    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        GlStateManager.resetColor();
        Fonts.font35.drawString(LanguageManager.INSTANCE.get(buttonElement.getDisplayName().replaceAll("%","")), (int) (buttonElement.getX() - (Fonts.font35.getStringWidth(LanguageManager.INSTANCE.get(buttonElement.getDisplayName().replaceAll("%",""))) - 100.0f) / 2.0f), buttonElement.getY() + 6, buttonElement.getColor());
    }

    @Override
    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        int guiColor = ClickGUIModule.generateColor().getRGB();
        GlStateManager.resetColor();
        // draw the module and blur it
        Fonts.font35.drawString(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%","")), (int) (moduleElement.getX() - (Fonts.font35.getStringWidth(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%",""))) - 100.0f) / 2.0f), moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);
        Fonts.font35.drawString(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%","")), (int) (moduleElement.getX() - (Fonts.font35.getStringWidth(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%",""))) - 100.0f) / 2.0f), moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);
        BlurUtils.INSTANCE.draw(moduleElement.getX() + 2, moduleElement.getY() + 3, moduleElement.getWidth() - 2, moduleElement.getHeight() - 3, 10);
        // draw the module 
        Fonts.font35.drawString(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%","")), (int) (moduleElement.getX() - (Fonts.font35.getStringWidth(LanguageManager.INSTANCE.get(moduleElement.getDisplayName().replaceAll("%",""))) - 100.0f) / 2.0f), moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);

        final List<Value<?>> moduleValues = moduleElement.getModule().getValues();
        if(!moduleValues.isEmpty()) {

            if(moduleElement.isShowSettings()) {
                int yPos = moduleElement.getY() + 4;
                for(final Value value : moduleValues) {
                    if(!value.getDisplayable())
                        continue;

                    if (value instanceof BoolValue) {
                        final String text = value.getName();
                        final float textWidth = Fonts.font35.getStringWidth(text);

                        if (moduleElement.getSettingsWidth() < textWidth + 8)
                            moduleElement.setSettingsWidth(textWidth + 8);

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);

                        if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14) {
                            if (Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                final BoolValue boolValue = (BoolValue) value;

                                boolValue.set(!boolValue.get());
                                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                            }
                        }

                        GlStateManager.resetColor();
                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, ((BoolValue) value).get() ? guiColor : Integer.MAX_VALUE);
                        yPos += 12;
                    }else if(value instanceof ListValue) {
                        ListValue listValue = (ListValue) value;

                        final String text = value.getName();
                        final float textWidth = Fonts.font35.getStringWidth(text);

                        if(moduleElement.getSettingsWidth() < textWidth + 16)
                            moduleElement.setSettingsWidth(textWidth + 16);

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                        GlStateManager.resetColor();
                        Fonts.font35.drawString("§c" + text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 0xffffff);
                        Fonts.font35.drawString(listValue.openList ? "-" : "+", (int) (moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() - (listValue.openList ? 5 : 6)), yPos + 4, 0xffffff);

                        if(mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14) {
                            if(Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listValue.openList = !listValue.openList;
                                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                            }
                        }

                        yPos += 12;

                        for(final String valueOfList : listValue.getValues()) {
                            final float textWidth2 = Fonts.font35.getStringWidth(">" + valueOfList);

                            if(moduleElement.getSettingsWidth() < textWidth2 + 8)
                                moduleElement.setSettingsWidth(textWidth2 + 8);

                            if (listValue.openList) {
                                RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);

                                if(mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14) {
                                    if(Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listValue.set(valueOfList);
                                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                                    }
                                }

                                GlStateManager.resetColor();
                                Fonts.font35.drawString(">", moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Integer.MAX_VALUE);
                                Fonts.font35.drawString(valueOfList, moduleElement.getX() + moduleElement.getWidth() + 14, yPos + 4, listValue.get() != null && listValue.get().equalsIgnoreCase(valueOfList) ? guiColor : Integer.MAX_VALUE);
                                yPos += 12;
                            }
                        }
                    }else if(value instanceof FloatValue) {
                        final FloatValue floatValue = (FloatValue) value;
                        final String text = value.getName() + "§f: §c" + round(floatValue.get());
                        final float textWidth = Fonts.font35.getStringWidth(text);

                        if(moduleElement.getSettingsWidth() < textWidth + 8)
                            moduleElement.setSettingsWidth(textWidth + 8);

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 24, Integer.MIN_VALUE);
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, yPos + 18, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() - 4, yPos + 19, Integer.MAX_VALUE);
                        final float sliderValue = moduleElement.getX() + moduleElement.getWidth() + ((moduleElement.getSettingsWidth() - 12) * (floatValue.get() - floatValue.getMinimum()) / (floatValue.getMaximum() - floatValue.getMinimum()));
                        RenderUtils.drawRect(8 + sliderValue, yPos + 15, sliderValue + 11, yPos + 21, guiColor);

                        if(mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() - 4 && mouseY >= yPos + 15 && mouseY <= yPos + 21) {
                            if(Mouse.isButtonDown(0)) {
                                double i = MathHelper.clamp_double((mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12), 0, 1);
                                floatValue.set(round((float) (floatValue.getMinimum() + (floatValue.getMaximum() - floatValue.getMinimum()) * i)).floatValue());
                            }
                        }

                        GlStateManager.resetColor();
                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 0xffffff);
                        yPos += 22;
                    }else if(value instanceof IntegerValue) {
                        final IntegerValue integerValue = (IntegerValue) value;
                        final String text = value.getName() + "§f: §c" + (value instanceof BlockValue ? BlockUtils.getBlockName(integerValue.get()) + " (" + integerValue.get() + ")" : integerValue.get());
                        final float textWidth = Fonts.font35.getStringWidth(text);

                        if(moduleElement.getSettingsWidth() < textWidth + 8)
                            moduleElement.setSettingsWidth(textWidth + 8);

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 24, Integer.MIN_VALUE);
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, yPos + 18, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() - 4, yPos + 19, Integer.MAX_VALUE);
                        final float sliderValue = moduleElement.getX() + moduleElement.getWidth() + ((moduleElement.getSettingsWidth() - 12) * (integerValue.get() - integerValue.getMinimum()) / (integerValue.getMaximum() - integerValue.getMinimum()));
                        RenderUtils.drawRect(8 + sliderValue, yPos + 15, sliderValue + 11, yPos + 21, guiColor);

                        if(mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 15 && mouseY <= yPos + 21) {
                            if(Mouse.isButtonDown(0)) {
                                double i = MathHelper.clamp_double((mouseX - moduleElement.getX() - moduleElement.getWidth() - 8) / (moduleElement.getSettingsWidth() - 12), 0, 1);
                                integerValue.set((int) (integerValue.getMinimum() + (integerValue.getMaximum() - integerValue.getMinimum()) * i));
                            }
                        }

                        GlStateManager.resetColor();
                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 0xffffff);
                        yPos += 22;
                    }else if(value instanceof FontValue) {
                        final FontValue fontValue = (FontValue) value;
                        final FontRenderer fontRenderer = fontValue.get();

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);

                        String displayString = "Font: Unknown";

                        if (fontRenderer instanceof GameFontRenderer) {
                            final GameFontRenderer liquidFontRenderer = (GameFontRenderer) fontRenderer;

                            displayString = "Font: " + liquidFontRenderer.getDefaultFont().getFont().getName() + " - " + liquidFontRenderer.getDefaultFont().getFont().getSize();
                        }else if(fontRenderer == Fonts.minecraftFont)
                            displayString = "Font: Minecraft";
                        else{
                            final Object[] objects = Fonts.getFontDetails(fontRenderer);

                            if(objects != null) {
                                displayString = objects[0] + ((int) objects[1] != -1 ? " - " + objects[1] : "");
                            }
                        }

                        Fonts.font35.drawString(displayString, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Color.WHITE.getRGB());
                        int stringWidth = Fonts.font35.getStringWidth(displayString);

                        if(moduleElement.getSettingsWidth() < stringWidth + 8)
                            moduleElement.setSettingsWidth(stringWidth + 8);

                        if((Mouse.isButtonDown(0) && !mouseDown || Mouse.isButtonDown(1) && !rightMouseDown) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 4 && mouseY <= yPos + 12) {
                            final List<FontRenderer> fonts = Fonts.getFonts();

                            if(Mouse.isButtonDown(0)) {
                                for(int i = 0; i < fonts.size(); i++) {
                                    final FontRenderer font = fonts.get(i);

                                    if(font == fontRenderer) {
                                        i++;

                                        if(i >= fonts.size())
                                            i = 0;

                                        fontValue.set(fonts.get(i));
                                        break;
                                    }
                                }
                            }else{
                                for(int i = fonts.size() - 1; i >= 0; i--) {
                                    final FontRenderer font = fonts.get(i);

                                    if(font == fontRenderer) {
                                        i--;

                                        if(i >= fonts.size())
                                            i = 0;

                                        if(i < 0)
                                            i = fonts.size() - 1;

                                        fontValue.set(fonts.get(i));
                                        break;
                                    }
                                }
                            }
                        }

                        yPos += 11;
                    }else{
                        String text = value.getName() + "§f: §c" + value.get();
                        float textWidth = Fonts.font35.getStringWidth(text);

                        if (moduleElement.getSettingsWidth() < textWidth + 8)
                            moduleElement.setSettingsWidth(textWidth + 8);

                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                        GlStateManager.resetColor();
                        Fonts.font35.drawString(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 0xffffff);
                        yPos += 12;
                    }
                }

                moduleElement.updatePressed();
                mouseDown = Mouse.isButtonDown(0);
                rightMouseDown = Mouse.isButtonDown(1);

                if(moduleElement.getSettingsWidth() > 0F && yPos > moduleElement.getY() + 4)
                    RenderUtils.drawBorderedRect(moduleElement.getX() + moduleElement.getWidth() + 4, moduleElement.getY() + 6, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 2, 1F, Integer.MIN_VALUE, 0);
            }
        }
    }

    private BigDecimal round(final float f) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(2, 4);
        return bd;
    }
}
