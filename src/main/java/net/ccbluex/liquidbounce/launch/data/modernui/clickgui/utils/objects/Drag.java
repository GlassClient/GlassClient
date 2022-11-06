/*
 * GlassClient PVP Client
 * A free open-source mixin-based PVP client based on liquidbounce with all cheats removed.
 * https://github.com/GlassClient/GlassClient
 */
package net.ccbluex.liquidbounce.launch.data.modernui.clickgui.utils.objects;

public class Drag {

    private float xPos, yPos;
    private float startX, startY;
    private boolean dragging;

    public Drag(float initialXVal, float initialYVal) {
        this.xPos = initialXVal;
        this.yPos = initialYVal;
    }

    public float getX() {
        return xPos;
    }

    public void setX(float x) {
        this.xPos = x;
    }

    public float getY() {
        return yPos;
    }

    public void setY(float y) {
        this.yPos = y;
    }

    public final void onDraw(int mouseX, int mouseY) {
        if (dragging) {
            xPos = (mouseX - startX);
            yPos = (mouseY - startY);
        }
    }

    public final void onDrawNegX(int mouseX, int mouseY) {
        if (dragging) {
            xPos = -(mouseX - startX);
            yPos = (mouseY - startY);
        }
    }

    public final void onClick(int mouseX, int mouseY, int button, boolean canDrag) {
        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onClickAddX(int mouseX, int mouseY, int button, boolean canDrag) {
        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX + xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }

}
