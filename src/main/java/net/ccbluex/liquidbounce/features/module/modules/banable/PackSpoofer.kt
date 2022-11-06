/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.banable

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.minecraft.network.play.client.C19PacketResourcePackStatus
import net.minecraft.network.play.server.S48PacketResourcePackSend
import java.io.File
import java.net.URI
import java.net.URISyntaxException

@ModuleInfo(name = "PackSpoofer", category = ModuleCategory.BANABLE)
class PackSpoofer : Module() {
    val notifyValue = BoolValue("Notify", true)

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet

        if (packet is S48PacketResourcePackSend) {
            val url = packet.url
            val hash = packet.hash
            try {
                val scheme = URI(url).scheme
                val isLevelProtocol = "level" == scheme

                if ("http" != scheme && "https" != scheme && !isLevelProtocol)
                    throw URISyntaxException(url, "Wrong protocol")

                if (isLevelProtocol && (url.contains("..") || !url.endsWith(".zip"))) {
                    val s2 = url.substring("level://".length)
                    val file1 = File(mc.mcDataDir, "saves")
                    val file2 = File(file1, s2)

                    if (!file2.isFile || url.contains("fdpclient", true)) {
                        if (notifyValue.get()) {
                            ClientUtils.displayChatMessage("§7[§b!§7] §b§lFDPCLIENT §c» §6Resourcepack exploit detected.")
                            ClientUtils.displayChatMessage("§7[§b!§7] §b§lFDPCLIENT §c» §7Exploit target directory: §r$url")

                            throw URISyntaxException(url, "Invalid levelstorage resourcepack path")
                        } else {
                            mc.netHandler.addToSendQueue(C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD))
                            event.cancelEvent()
                            return
                        }
                    }
                }
                mc.netHandler.addToSendQueue(C19PacketResourcePackStatus(packet.hash,
                    C19PacketResourcePackStatus.Action.ACCEPTED))
                mc.netHandler.addToSendQueue(C19PacketResourcePackStatus(packet.hash,
                    C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED))
            } catch (e: URISyntaxException) {
                alert("Failed to handle resource pack")
                mc.netHandler.addToSendQueue(C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD))
            }

            event.cancelEvent()
        }
    }

}