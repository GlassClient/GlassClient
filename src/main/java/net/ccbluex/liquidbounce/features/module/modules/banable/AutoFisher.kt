/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.banable

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MathUtils.inRange
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.minecraft.network.play.server.S29PacketSoundEffect

@ModuleInfo(name = "AutoFisher", category = ModuleCategory.BANABLE)
object AutoFisher : Module() {

    private val detectionValue = ListValue("Detection", arrayOf("Motion", "Sound"), "Sound")
    private val recastValue = BoolValue("Recast", true)
    private val recastDelayValue = IntegerValue("RecastDelay", 1, 0, 1000)

    private var stage = Stage.NOTHING
    private val recastTimer = MSTimer()

    override fun onDisable() {
        stage = Stage.NOTHING
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (stage == Stage.RECOVERING) {
            mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(mc.thePlayer.heldItem))
            stage = if (recastValue.get()) {
                recastTimer.reset()
                Stage.RECASTING
            } else {
                Stage.NOTHING
            }
            return
        } else if (stage == Stage.RECASTING) {
            if (recastTimer.hasTimePassed(recastDelayValue.get().toLong())) {
                mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(mc.thePlayer.heldItem))
                stage = Stage.NOTHING
            }
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet
        if (detectionValue.get() == "Sound" && packet is S29PacketSoundEffect && mc.thePlayer?.fishEntity != null
            && packet.soundName == "random.splash" && packet.x.inRange(mc.thePlayer.fishEntity.posX, 1.5) && packet.z.inRange(mc.thePlayer.fishEntity.posZ, 1.5)) {
            recoverFishRod()
        } else if (detectionValue.get() == "Motion" && packet is S12PacketEntityVelocity && mc.thePlayer?.fishEntity != null
            && packet.entityID == mc.thePlayer.fishEntity.entityId && packet.motionX == 0 && packet.motionY != 0 && packet.motionZ == 0) {
            recoverFishRod()
        }
    }

    private fun recoverFishRod() {
        if (stage != Stage.NOTHING) {
            return
        }

        stage = Stage.RECOVERING
    }

    private enum class Stage {
        NOTHING,
        RECOVERING,
        RECASTING
    }
}
