/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.mods

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.render.Animation
import net.ccbluex.liquidbounce.utils.render.EaseUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue

@ModuleInfo(name = "Hotbar", category = ModuleCategory.MODS, array = false, defaultOn = true)
object BetterHotbar : Module() {
    val hotbarValue = ListValue("HotbarMode", arrayOf("Minecraft", "Rounded", "Full", "LB", "Rise"), "Rounded")
    val hotbarAlphaValue = IntegerValue("HotbarAlpha", 70, 0, 255)
    val hotbarEaseValue = BoolValue("HotbarEase", true)
    private val hotbarAnimSpeedValue = IntegerValue("HotbarAnimSpeed", 10, 5, 20).displayable { hotbarEaseValue.get() }
    private val hotbarAnimTypeValue = EaseUtils.getEnumEasingList("HotbarAnimType").displayable { hotbarEaseValue.get() }
    private val hotbarAnimOrderValue = EaseUtils.getEnumEasingOrderList("HotbarAnimOrder").displayable { hotbarEaseValue.get() }
    
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
            if (easeAnimation == null || (easeAnimation != null && easeAnimation!!.to != value.toDouble())) {
                easeAnimation = Animation(
                    EaseUtils.EnumEasingType.valueOf(hotbarAnimTypeValue.get()),
                    EaseUtils.EnumEasingOrder.valueOf(hotbarAnimOrderValue.get()),
                    field.toDouble(),
                    value.toDouble(),
                    hotbarAnimSpeedValue.get() * 30L
                ).start()
            }
        }

    fun getHotbarEasePos(x: Int): Int {
        if (!hotbarEaseValue.get()) return x
        easingValue = x
        return easingValue
    }
    // rounded
    private var hotBarX = 0F


}