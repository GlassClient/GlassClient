/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(C00Handshake.class)
public class MixinC00Handshake {

    @Shadow
    private int protocolVersion;

    @Shadow
    public int port;

    @Shadow
    private EnumConnectionState requestedState;

    @Shadow
    public String ip;

    /**
     * @author CCBlueX
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarIntToBuffer(this.protocolVersion);
        buf.writeString(this.ip + (AntiForge.INSTANCE.getEnabled() && AntiForge.INSTANCE.getBlockFML() && !Minecraft.getMinecraft().isIntegratedServerRunning() ? "" : "\0FML\0"));
        buf.writeShort(this.port);
        buf.writeVarIntToBuffer(this.requestedState.getId());
    }
}