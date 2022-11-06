/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.launch.data.modernui;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.style.styles.*;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.style.styles.newVer.NewUi;
import net.ccbluex.liquidbounce.launch.options.modernuiLaunchOption;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleInfo(name = "ClickGUI", category = ModuleCategory.CLIENT, keyBind = Keyboard.KEY_RSHIFT, canEnable = false)
public class ClickGUIModule extends Module {
  //  public ListValue styleValue = new ListValue("Style", new String[]{"Astolfo", "LB+", "Jello", "LiquidBounce", "Tenacity5", "Glow"}, "LB+") {
 //       @Override
  //      protected void onChanged(final String oldValue, final String newValue) {
   //         updateStyle();
    //    }
  //  };

    public FloatValue scaleValue = new FloatValue("Scale", 0.70F, 0.7F, 2F);
    public final IntegerValue maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);
    public final ListValue backgroundValue = new ListValue("Background", new String[] {"Default", "Gradient", "None"}, "None");

    public final ListValue animationValue = new ListValue("Animation", new String[] {"Bread", "Slide", "LiquidBounce", "Zoom", "Ziul", "None"}, "Ziul");
    public static final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    public static final IntegerValue colorRedValue = (IntegerValue) new IntegerValue("R", 0, 0, 255).displayable(() -> !colorRainbow.get());
    public static final IntegerValue colorGreenValue = (IntegerValue) new IntegerValue("G", 160, 0, 255).displayable(() -> !colorRainbow.get());
    public static final IntegerValue colorBlueValue = (IntegerValue) new IntegerValue("B", 255, 0, 255).displayable(() -> !colorRainbow.get());
    public static final BoolValue fastRenderValue = new BoolValue("FastRender", false);


    public static Color generateColor() {
        return colorRainbow.get() ? ColorUtils.INSTANCE.rainbow() : new Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get());
    }
    @Override
    public void onEnable() {

       // if (styleValue.get().contains("LB+")){
            mc.displayGuiScreen(NewUi.getInstance());
     //   } else {
         //   updateStyle();
     //       mc.displayGuiScreen(modernuiLaunchOption.clickGui);
      //  }

    }

  /*  private void updateStyle() {
        switch (styleValue.get().toLowerCase()) {
            case "liquidbounce":
                modernuiLaunchOption.clickGui.style = new LiquidBounceStyle();
                break;
            case "slowly":
                modernuiLaunchOption.clickGui.style = new SlowlyStyle();
                break;
            case "jello":
                modernuiLaunchOption.clickGui.style = new JelloStyle();
                break;
            case "tenacity5":
                modernuiLaunchOption.clickGui.style = new TenacityStyle();
                break;
            case "glow":
                modernuiLaunchOption.clickGui.style = new GlowStyle();
                break;
            case "astolfo":
                modernuiLaunchOption.clickGui.style = new AstolfoStyle();
                break;
        }
    } */

    @EventTarget(ignoreCondition = true)
    public void onPacket(final PacketEvent event) {
        final Packet packet = event.getPacket();

        if (packet instanceof S2EPacketCloseWindow && mc.currentScreen instanceof ClickGui) {
            event.cancelEvent();
        }
    }
}
