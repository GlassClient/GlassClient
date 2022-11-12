/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.banable

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.event.StrafeEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.Rotation
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.value.BoolValue

@ModuleInfo(name = "Freelook", category = ModuleCategory.BANABLE, triggerType = ModuleInfo.EnumTriggerType.PRESS)
class EdgeJump : Module() {
    private val thirdPerson = BoolValue("ThirdPersonView", true)
    
    var playerYaw = 0f
    var playerPitch = 0f
    var cameraView = 0
  
    @EventTarget
    fun onEnable() {
        playerYaw = mc.thePlayer.rotationYaw
        playerPitch = mc.thePlayer.rotationPitch
        cameraView = mc.gameSettings.thirdPersonView
    }
    
    @EventTarget
    fun onDisable() {
        mc.thePlayer.rotationYaw = playerYaw
        mc.thePlayer.rotationPitch = PlayerPitch
        mc.gameSettings.thirdPersonView = cameraView
    }
  

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (thirdPerson.get()) {
            mc.gameSettings.thirdPersonView = 3
        }
        RotationUtils.setTargetRotation(Rotation(playerYaw, playerPitch), 3)
    }
    
    @EventTarget 
    fun onStrafe(event:StrafeEvent) {
      val (yaw) = RotationUtils.targetRotation ?: return
      var strafe = event.strafe
      var forward = event.forward
      val friction = event.friction

      var f = strafe * strafe + forward * forward

      if (f >= 1.0E-4F) {
          f = MathHelper.sqrt_float(f)

          if (f < 1.0F) {
              f = 1.0F
          }

          f = friction / f
          strafe *= f
          forward *= f

          val yawSin = MathHelper.sin((yaw * Math.PI / 180F).toFloat())
          val yawCos = MathHelper.cos((yaw * Math.PI / 180F).toFloat())

          mc.thePlayer.motionX += strafe * yawCos - forward * yawSin
          mc.thePlayer.motionZ += forward * yawCos + strafe * yawSin
      }
      event.cancelEvent()
    }
}
