package masuka.robocode.antigravity;

import java.awt.*;
import masuka.robocode.utils.geometry.Gvector;

public abstract class ForceSource {

    static final public Color ATTRACT_COLOR = new Color(154, 205, 50);
    static final public Color DITRACT_COLOR = new Color(255, 69, 0);
    static final public Color INACTIV_COLOR = Color.GRAY;
    static final public Color ACTIVE_COLOR = Color.ORANGE;
    static final public double CLOSE_DISTANCE = 20;

    protected double power = 1000;
    protected double declineOrder = 2;
    protected boolean isInactive = false;
    protected boolean isTerminated = false;
    protected Gvector forceVector = Gvector.getZeroVector();

    public Gvector forceInPoint(double a, double b) {
        return forceVector;
    }

    public void setFrocePower(double p) {
        power = p;
    }

    public void setForceDistanceDeclineOrder(double order) {
        declineOrder = order;
    }
    
    public double getForceDistanceDeclineOrder() {
        return declineOrder;
    }

    public void invertForcePower() {
        power *= -1;
    }
    
    public void setForceAttractive() {
        power = -1*Math.abs(power);
    }
    
    public void setDistractive() {
        power = Math.abs(power);
    }
    
    public double getForcePower() {
        return power;
    }

    public boolean isInactive() {
        return isInactive;
    }
    
    public void setInactive(boolean in) {
        isInactive = in;
    }
    
    public void setTerminated() {
        isTerminated = true;
    }
    
    public boolean isTerminated() {
        return isTerminated;
    }
    
    public void update() {
        
    }
    
    public void onPaint(Graphics2D g) {
        
    }
    
}
