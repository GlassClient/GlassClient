/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.utils.misc

import java.util.*

object RandomUtils {
    private val random = Random()

    fun nextBoolean(): Boolean {
        return Random().nextBoolean()
    }

    fun nextInt(startInclusive: Int, endExclusive: Int): Int {
        return if (endExclusive - startInclusive <= 0) {
            startInclusive
        } else {
            startInclusive + random.nextInt(endExclusive - startInclusive)
        }
    }

    fun nextFloat(startInclusive: Float, endInclusive: Float): Float {
        return if (startInclusive == endInclusive || endInclusive - startInclusive <= 0f) {
            startInclusive
        } else {
            (startInclusive + (endInclusive - startInclusive) * Math.random()).toFloat()
        }
    }

    fun nextDouble(startInclusive: Double, endInclusive: Double): Double {
        return if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0) startInclusive else startInclusive + (endInclusive - startInclusive) * Math.random()
    }

    fun random(length: Int, chars: String): String? {
        return random(length, chars.toCharArray())
    }

    fun random(length: Int, chars: CharArray): String? {
        val stringBuilder = java.lang.StringBuilder()
        for (i in 0 until length) stringBuilder.append(chars[Random().nextInt(chars.size)])
        return stringBuilder.toString()
    }

    fun randomNumber(length: Int): String {
        return randomString(length, "123456789")
    }

    fun randomString(length: Int): String {
        return randomString(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
    }

    fun randomString(length: Int, chars: String): String {
        return randomString(length, chars.toCharArray())
    }

    fun randomString(length: Int, chars: CharArray): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until length) stringBuilder.append(chars[random.nextInt(chars.size)])
        return stringBuilder.toString()
    }
}