/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.utils.math;

import java.math.BigDecimal;
public final class MathUtils {
    public static double round(final double value, final double inc) {
        if (inc == 0.0) return value;
        else if (inc == 1.0) return Math.round(value);
        else {
            final double halfOfInc = inc / 2.0;
            final double floored = Math.floor(value / inc) * inc;

            if (value >= floored + halfOfInc)
                return new BigDecimal(Math.ceil(value / inc) * inc)
                        .doubleValue();
            else return new BigDecimal(floored)
                    .doubleValue();
        }
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).floatValue();
    }
    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
    public static float clampValue(final float value, final float floor, final float cap) {
        if (value < floor) {
            return floor;
        }
        return Math.min(value, cap);
    }
    public static Double interpolate(double oldValue, double newValue, double interpolationValue){
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }
    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static double incValue(double val, double inc) {
        double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
}
