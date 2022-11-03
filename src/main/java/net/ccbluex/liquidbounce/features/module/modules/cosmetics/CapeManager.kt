/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.cosmetics

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.cape.GuiCapeManager

@ModuleInfo(name = "CapeManager", category = ModuleCategory.COSMETICS, canEnable = false)
class CapeManager : Module() {
    override fun onEnable() {
        mc.displayGuiScreen(GuiCapeManager)
    }
}