/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/SkidderMC/FDPClient
 */
package net.ccbluex.liquidbounce.value

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.utils.ClientUtils

abstract class Value<T>(val name: String, var value: T) {
    val default = value
    var textHovered: Boolean = false

    private var displayableFunc: () -> Boolean = { true }

    fun displayable(func: () -> Boolean): Value<T> {
        displayableFunc = func
        return this
    }

    val displayable: Boolean
        get() = displayableFunc()

    fun set(newValue: T) {
        if (newValue == value) return

        val oldValue = get()

        try {
            onChange(oldValue, newValue)
            changeValue(newValue)
            onChanged(oldValue, newValue)
            LiquidBounce.configManager.smartSave()
        } catch (e: Exception) {
            ClientUtils.logError("[ValueSystem ($name)]: ${e.javaClass.name} (${e.message}) [$oldValue >> $newValue]")
        }
    }



    fun get() = value

    fun setDefault() {
        value = default
    }

    open fun changeValue(value: T) {
        this.value = value
    }

    abstract fun toJson(): JsonElement?
    abstract fun fromJson(element: JsonElement)

    protected open fun onChange(oldValue: T, newValue: T) {}
    protected open fun onChanged(oldValue: T, newValue: T) {}

    // this is better api for ListValue and TextValue

    open class ColorValue(name: String, value: Int, canDisplay: () -> Boolean) : Value<Int>(name, value) {
        val minimum: Int = -10000000
        val maximum: Int = 1000000
        fun set(newValue: Number) {
            set(newValue.toInt())
        }
        override fun toJson() = JsonPrimitive(value)
        override fun fromJson(element: JsonElement) {
            if (element.isJsonPrimitive)
                value = element.asInt
        }
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (value is String && other is String) {
            return (value as String).equals(other, true)
        }
        return value?.equals(other) ?: false
    }

    fun contains(text: String/*, ignoreCase: Boolean*/): Boolean {
        return if (value is String) {
            (value as String).contains(text, true)
        } else {
            false
        }
    }
}
