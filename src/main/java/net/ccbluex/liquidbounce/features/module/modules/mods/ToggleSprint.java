/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 */

package net.ccbluex.liquidbounce.features.module.modules.mods;
// slight hyperium skidding...

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.forge.mixins.client.IMixinKeyBinding;
import net.minecraft.client.Minecraft;

@ModuleInfo(name = "ToggleSprint", category = ModuleCategory.MODS)
public final class ToggleSprint extends Module {
    public void onEnable() {
        ((IMixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(true);
    }
    public void onDisable() {
        ((IMixinKeyBinding) Minecraft.getMinecraft().gameSettings.keyBindSprint).setPressed(false);
    }
}
