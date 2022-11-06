/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.features.module.modules.mods

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.banable.AntiCheat
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.extensions.ping
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils.*
import net.ccbluex.liquidbounce.value.*
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.math.roundToInt

@ModuleInfo(name = "BetterNameTags", category = ModuleCategory.MODS)
class BetterNameTags : Module() {
    private val modeValue = ListValue("Mode", arrayOf("Simple", "Liquid", "Jello"), "Liquid")
    private val healthValue = BoolValue("Health", true)
    private val pingValue = BoolValue("Ping", true)
    private val healthBarValue = BoolValue("Bar", true)
    private val distanceValue = BoolValue("Distance", false)
    private val armorValue = BoolValue("Armor", true)
    private val potionValue = BoolValue("Potions", true)
    private val clearNamesValue = BoolValue("ClearNames", true)
    private val fontValue = FontValue("Font", Fonts.font40)
    private val borderValue = BoolValue("Border", true)
    private val fontShadowValue = BoolValue("Shadow", true)
    private val hackerValue = BoolValue("Hacker", true)
    private val jelloColorValue = BoolValue("JelloHPColor", true).displayable { modeValue.equals("Jello") }
    private val jelloAlphaValue = IntegerValue("JelloAlpha", 170, 10, 255).displayable { modeValue.equals("Jello") }
    private val scaleValue = FloatValue("Scale", 2F, 2F, 3F)
    private val translateY = FloatValue("TanslateY", 0.55F, -2F, 2F)
    private val backgroundColorRedValue = IntegerValue("Background-R", 0, 0, 255)
    private val backgroundColorGreenValue = IntegerValue("Background-G", 0, 0, 255)
    private val backgroundColorBlueValue = IntegerValue("Background-B", 0, 0, 255)
    private val backgroundColorAlphaValue = IntegerValue("Background-Alpha", 0, 0, 255)
    private val borderColorRedValue = IntegerValue("Border-R", 0, 0, 255)
    private val borderColorGreenValue = IntegerValue("Border-G", 0, 0, 255)
    private val borderColorBlueValue = IntegerValue("Border-B", 0, 0, 255)
    private val borderColorAlphaValue = IntegerValue("Border-Alpha", 0, 0, 255)

    private var targetTicks = 0
    private var entityKeep = "yes zywl"

    private val inventoryBackground = ResourceLocation("textures/gui/container/inventory.png")

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        for (entity in mc.theWorld.loadedEntityList) {
            if (EntityUtils.isSelected(entity, false)) {
                renderNameTag(entity as EntityLivingBase,
                    if (hackerValue.get() && LiquidBounce.moduleManager[AntiCheat::class.java]!!.isHacker(entity)) { "§c" } else { "" } +
                            if (clearNamesValue.get()) { entity.name } else { entity.getDisplayName().unformattedText })
            }
        }
    }

    private fun getPlayerName(entity: EntityLivingBase): String {
        val name = entity.displayName.formattedText
        return name + "§c"
    }

    private fun renderNameTag(entity: EntityLivingBase, tag: String) {
        // Set fontrenderer local
        val fontRenderer = fontValue.get()

        // Push
        glPushMatrix()

        // Translate to player position
        val renderManager = mc.renderManager
        val timer = mc.timer

        glTranslated( // Translate to player position with render pos and interpolate it
            entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX,
            entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY + entity.eyeHeight.toDouble() + translateY.get().toDouble(),
            entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ
        )

        // Rotate view to player
        glRotatef(-mc.renderManager.playerViewY, 0F, 1F, 0F)
        glRotatef(mc.renderManager.playerViewX, 1F, 0F, 0F)

        // Scale
       // var distance = mc.thePlayer.getDistanceToEntity(entity) / 4F

       // if (distance < 1F) {
       //     distance = 1F
       // }

        val scale = (1F / 150F) * scaleValue.get()

        // Disable lightning and depth test
        disableGlCap(GL_LIGHTING, GL_DEPTH_TEST)

        // Enable blend
        enableGlCap(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        // Draw nametag
        if(!entity.isSneaking && !entity.isInvisible && mc.thePlayer.getDistanceToEntity(entity) < 64) {
        when (modeValue.get().lowercase()) {
            "simple" -> {
                val healthPercent = (entity.health / entity.maxHealth).coerceAtMost(1F)
                val width = fontRenderer.getStringWidth(tag).coerceAtLeast(30) / 2
                val maxWidth = width * 2 + 12F

                glScalef(-scale * 2, -scale * 2, scale * 2)
                drawRect(-width - 6F, -fontRenderer.FONT_HEIGHT * 1.7F, width + 6F, -2F, Color(0, 0, 0, jelloAlphaValue.get()))
                drawRect(-width - 6F, -2F, -width - 6F + (maxWidth * healthPercent), 0F, ColorUtils.healthColor(entity.health, entity.maxHealth, jelloAlphaValue.get()))
                drawRect(-width - 6F + (maxWidth * healthPercent), -2F, width + 6F, 0F, Color(0, 0, 0, jelloAlphaValue.get()))
                fontRenderer.drawString(tag, (-fontRenderer.getStringWidth(tag) * 0.5F).toInt(), (-fontRenderer.FONT_HEIGHT * 1.4F).toInt(), Color.WHITE.rgb)
            }

            "liquid" -> {
                // Modify tag
                val nameColor = "§7"
                val ping = entity.ping

                val distanceText = if (distanceValue.get()) "§7 [§a${mc.thePlayer.getDistanceToEntity(entity).roundToInt()}§7]" else ""
                val pingText = if (pingValue.get() && entity is EntityPlayer) " §7[" + (if (ping > 200) "§c" else if (ping > 100) "§e" else "§a") + ping + "ms§7]" else ""
                val healthText = if (healthValue.get()) "§7 [§f" + entity.health.toInt() + "§c❤§7]" else ""

                val text = "$distanceText$pingText$nameColor$tag$healthText"

                glScalef(-scale, -scale, scale)

                val width = fontRenderer.getStringWidth(text) * 0.5f

                val dist = width + 4F - (-width - 2F)

                glDisable(GL_TEXTURE_2D)
                glEnable(GL_BLEND)

                val bgColor = Color(backgroundColorRedValue.get(), backgroundColorGreenValue.get(), backgroundColorBlueValue.get(), backgroundColorAlphaValue.get())
                val borderColor = Color(borderColorRedValue.get(), borderColorGreenValue.get(), borderColorBlueValue.get(), borderColorAlphaValue.get())

                if (borderValue.get())
                    quickDrawBorderedRect(-width - 2F, -2F, width + 4F, fontRenderer.FONT_HEIGHT + 2F + if (healthBarValue.get()) 2F else 0F, 2F, borderColor.rgb, bgColor.rgb)
                else
                    quickDrawRect(-width - 2F, -2F, width + 4F, fontRenderer.FONT_HEIGHT + 2F + if (healthBarValue.get()) 2F else 0F, bgColor.rgb)

                if (healthBarValue.get()) {
                    quickDrawRect(-width - 2F, fontRenderer.FONT_HEIGHT + 3F, -width - 2F + dist, fontRenderer.FONT_HEIGHT + 4F, Color(10, 155, 10).rgb)
                    quickDrawRect(-width - 2F, fontRenderer.FONT_HEIGHT + 3F, -width - 2F + (dist * (entity.health.toFloat() / entity.maxHealth.toFloat()).coerceIn(0F, 1F)), fontRenderer.FONT_HEIGHT + 4F, Color(10, 255, 10).rgb)
                }

                glEnable(GL_TEXTURE_2D)

                fontRenderer.drawString(text, 1F + -width, if (fontRenderer == Fonts.minecraftFont) 1F else 1.5F,
                    0xFFFFFF, fontShadowValue.get())

                var foundPotion = false
                if (potionValue.get() && entity is EntityPlayer) {
                    val potions = (entity.getActivePotionEffects() as Collection<PotionEffect>).map { Potion.potionTypes[it.getPotionID()] }.filter { it.hasStatusIcon() }
                    if (!potions.isEmpty()) {
                        foundPotion = true

                        color(1.0F, 1.0F, 1.0F, 1.0F)
                        disableLighting()
                        enableTexture2D()

                        val minX = (potions.size * -20) / 2

                        var index = 0

                        glPushMatrix()
                        enableRescaleNormal()
                        for (potion in potions) {
                            color(1.0F, 1.0F, 1.0F, 1.0F)
                            mc.getTextureManager().bindTexture(inventoryBackground)
                            val i1 = potion.getStatusIconIndex()
                            drawTexturedModalRect(minX + index * 20, -22, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18, 0F)
                            index++
                        }
                        disableRescaleNormal()
                        glPopMatrix()

                        enableAlpha()
                        disableBlend()
                        enableTexture2D()
                    }
                }

                if (armorValue.get() && entity is EntityPlayer) {
                    for (index in 0..4) {
                        if (entity.getEquipmentInSlot(index) == null) {
                            continue
                        }

                        mc.renderItem.zLevel = -147F
                        mc.renderItem.renderItemAndEffectIntoGUI(entity.getEquipmentInSlot(index), -50 + index * 20, if (potionValue.get() && foundPotion) -42 else -22)
                    }

                    enableAlpha()
                    disableBlend()
                    enableTexture2D()
                }
            }

            "jello" -> {
                // colors
                var hpBarColor = Color(255, 255, 255, jelloAlphaValue.get())
                val name = entity.displayName.unformattedText + "    "
                if (jelloColorValue.get() && name.startsWith("§")) {
                    hpBarColor = ColorUtils.colorCode(name.substring(1, 2), jelloAlphaValue.get())
                }
                val bgColor = Color(50, 50, 50, jelloAlphaValue.get())
                val width = (fontRenderer.getStringWidth(tag) / 2) + 4
                val maxWidth = (width + 4F) - (-width - 4F)
                var healthPercent = entity.health / entity.maxHealth
                val jelloscale = 0.006666666667.toFloat()

                // render bg
                glScalef(-jelloscale * 2, -jelloscale * 2, jelloscale * 2)
                drawRect(-width - 4F, -fontRenderer.FONT_HEIGHT * 3F, width + 4F, -3F, bgColor)

                // render hp bar
                if (healthPercent> 1) {
                    healthPercent = 1F
                }

                drawRect(-width - 4F, -3F, (-width - 4F) + (maxWidth * healthPercent), 1F, hpBarColor)
                drawRect((-width - 4F) + (maxWidth * healthPercent), -3F, width + 4F, 1F, bgColor)

                // string
                fontRenderer.drawString(tag, -width, -fontRenderer.FONT_HEIGHT * 2 - 4, Color.WHITE.rgb)
                glScalef(0.5F, 0.5F, 0.5F)
                fontRenderer.drawString("Health: " + entity.health.toInt(), -width * 2, -fontRenderer.FONT_HEIGHT * 2, Color.WHITE.rgb)
            }
        }
        }
        // Reset caps
        resetCaps()

        // Reset color
        resetColor()
        glColor4f(1F, 1F, 1F, 1F)

        // Pop
        glPopMatrix()
    }
}
