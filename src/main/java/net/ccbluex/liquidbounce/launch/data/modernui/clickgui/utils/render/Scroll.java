/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.launch.data.modernui.clickgui.utils.render;

import lombok.Getter;
import lombok.Setter;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.utils.animations.Animation;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.utils.animations.Direction;
import net.ccbluex.liquidbounce.launch.data.modernui.clickgui.utils.animations.impl.SmoothStepAnimation;
import org.lwjgl.input.Mouse;

/**
 * @author cedo
 * @author Zywl
 */
public class Scroll {

    @Getter
    @Setter
    public float maxScroll = Float.MAX_VALUE, minScroll = 0, rawScroll;
    public float scroll;
    private Animation scrollAnimation = new SmoothStepAnimation(0, 0, Direction.BACKWARDS);

    public void onScroll(int ms) {
        scroll = (float) (rawScroll - scrollAnimation.getOutput());
        rawScroll += Mouse.getDWheel() / 4f;
        rawScroll = Math.max(Math.min(minScroll, rawScroll), -maxScroll);
        scrollAnimation = new SmoothStepAnimation(ms, rawScroll - scroll, Direction.BACKWARDS);
    }

    public boolean isScrollAnimationDone() {
        return scrollAnimation.isDone();
    }

    public float getScroll() {
        scroll = (float) (rawScroll - scrollAnimation.getOutput());
        return scroll;
    }

}
