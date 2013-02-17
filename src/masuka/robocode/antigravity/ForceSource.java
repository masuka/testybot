package masuka.robocode.antigravity;

import masuka.robocode.utils.geometry.Gvector;
import java.awt.*;

public abstract class ForceSource {

    static final public Color ATTRACT_COLOR = new Color(154, 205, 50);
    static final public Color DITRACT_COLOR = new Color(255, 69, 0);
    static final public Color INACTIV_COLOR = Color.GRAY;

    protected double power = 1000;
    protected double order = 2;
    protected long tick = 0;
    protected boolean isInactive = false;

    public abstract Gvector forceInPoint(double a, double b);

    public void setPower(double p) {
        power = p;
    }

    public void setOrder(double o) {
        order = o;
    }

    public void invertPower() {
        power *= -1;
    }
    
    public void setAttractive() {
        power = -1*Math.abs(power);
    }
    
    public void setDistractive() {
        power = Math.abs(power);
    }
    
    public double getPower() {
        return power;
    }

    public double getOrder() {
        return order;
    }

    public double getTick() {
        return tick;
    }

    public void updateTick() {
        tick++;
    }

    public boolean isInactive() {
        return isInactive;
    }

    public abstract void onPaint(Graphics2D g);
    
}
