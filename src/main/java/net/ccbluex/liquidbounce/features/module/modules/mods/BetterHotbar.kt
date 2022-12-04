/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.mods

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.font.FontLoaders
import net.ccbluex.liquidbounce.utils.render.Animation
import net.ccbluex.liquidbounce.utils.render.EaseUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.text.SimpleDateFormat

@ModuleInfo(name = "Hotbar", category = ModuleCategory.MODS, array = false, defaultOn = true)
object BetterHotbar : Module() {
    val hotbarValue = ListValue("HotbarMode", arrayOf("Minecraft", "Rounded", "Full", "LB", "Rise", "Gradient", "Overflow", "Glow", "Dock", "Exhi", "BlueIce", "Win11"), "Rounded")
    val hotbarAlphaValue = IntegerValue("HotbarAlpha", 70, 0, 255)
    val hotbarEaseValue = BoolValue("HotbarEase", true)
    private val hotbarAnimSpeedValue = IntegerValue("HotbarAnimSpeed", 10, 5, 20).displayable { hotbarEaseValue.get() }
    private val hotbarAnimTypeValue = EaseUtils.getEnumEasingList("HotbarAnimType").displayable { hotbarEaseValue.get() }
    private val hotbarAnimOrderValue = EaseUtils.getEnumEasingOrderList("HotbarAnimOrder").displayable { hotbarEaseValue.get() }
    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        if(hotbarValue.get().equals("Win11")){
            val sr = event.scaledResolution
            val dateFormat = SimpleDateFormat("dd/MM/yy")
            val date = dateFormat.format(System.currentTimeMillis())
            FontLoaders.F14.drawString(date, sr.getScaledWidth() - FontLoaders.F14.getStringWidth(date) - 4F,sr.getScaledHeight() - 9F,  Color(255, 255, 255).rgb)
            val hourFormat = SimpleDateFormat("HH:mm")
            val time = hourFormat.format(System.currentTimeMillis())
            FontLoaders.F14.drawString(time, sr.getScaledWidth() - FontLoaders.F14.getStringWidth(time) - 4F,sr.getScaledHeight() - 18F,  Color(255, 255, 255).rgb)
            val padding = Math.max(FontLoaders.F14.getStringWidth(time),FontLoaders.F14.getStringWidth(date)) + 10
            RenderUtils.drawImage(ResourceLocation("fdpclient/ui/hotbar/1.png"), (sr.scaledWidth - padding) - 10, sr.scaledHeight - 17, 10, 10)
            RenderUtils.drawImage(ResourceLocation("fdpclient/ui/hotbar/2.png"), (sr.scaledWidth - padding) - 28, sr.scaledHeight - 17, 10, 10)
            RenderUtils.drawImage(ResourceLocation("fdpclient/ui/hotbar/3.png"), (sr.scaledWidth - padding) - 46, sr.scaledHeight - 17, 10, 10)
            val loccode = mc.gameSettings.language.uppercase()
            val lang = loccode.substringBefore("_", "null")
            val region = loccode.substringAfter("_", "null")
            FontLoaders.F14.drawString(lang, (sr.getScaledWidth() - padding) - 62F,sr.getScaledHeight() - 17F,  Color(255, 255, 255).rgb)
            FontLoaders.F14.drawString(region, (sr.getScaledWidth() - padding) - 62F,sr.getScaledHeight() - 10F,  Color(255, 255, 255).rgb)
            val paddingAfter = (Math.max(FontLoaders.F14.getStringWidth(time),FontLoaders.F14.getStringWidth(date)) + 10) + (Math.max(FontLoaders.F14.getStringWidth(lang),FontLoaders.F14.getStringWidth(region))+ 2)
            RenderUtils.drawImage(ResourceLocation("fdpclient/ui/hotbar/up.png"), (sr.scaledWidth - paddingAfter) - 68, sr.scaledHeight - 17, 10, 10)
        }
    }

    // rise
    private var easeAnimation: Animation? = null
    private var easingValue = 0
        get() {
            if (easeAnimation != null) {
                field = easeAnimation!!.value.toInt()
                if (easeAnimation!!.state == Animation.EnumAnimationState.STOPPED) {
                    easeAnimation = null
                }
            }
            return field
        }
        set(value) {
            var hotbarSpeed = hotbarAnimSpeedValue.get()
            if(hotbarValue.get() == "Dock"){ hotbarSpeed = 4}
            if (easeAnimation == null || (easeAnimation != null && easeAnimation!!.to != value.toDouble())) {
                easeAnimation = Animation(
                    EaseUtils.EnumEasingType.valueOf(hotbarAnimTypeValue.get()),
                    EaseUtils.EnumEasingOrder.valueOf(hotbarAnimOrderValue.get()),
                    field.toDouble(),
                    value.toDouble(),
                    hotbarSpeed * 30L
                ).start()
            }
        }

    fun getHotbarEasePos(x: Int): Int {
        if (!hotbarEaseValue.get()) return x
        easingValue = x
        return easingValue
    }
}