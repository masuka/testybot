package masuka.robocode.antigravity;

import java.awt.*;
import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gvector;

public class ForcePoint extends ForceSource {

    protected Gpoint point;

    public ForcePoint(double x, double y, double p) {
        this(new Gpoint(x, y), p);
    }

    public ForcePoint(Gpoint pi, double pw) {
        setFrocePower(pw);
        point = pi;
    }
    
    public ForcePoint(Gpoint pi) {
        point = pi;
    }
    
    public ForcePoint() {
        point = Gpoint.getZeroPoint();
    }

    public Gpoint getPoint() {
        return point;
    }

    public void setX(double x) {
        point.setX(x);
    }

    public void setY(double y) {
        point.setY(y);
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public void setXY(double x, double y) {
        point.setXY(x, y);
    }

    public void incXY(Gvector v) {
        point.incXY(v);
    }

    public void incXY(double vx, double vy) {
        point.incXY(vx, vy);
    }
    
    private Gpoint p = Gpoint.getZeroPoint();
    
    @Override
    public Gvector forceInPoint(double a, double b) {
        
        if (isInactive()) {
            return Gvector.getZeroVector();
        }
        
        double distance = point.getDistance(a, b);
        double force;

        if (distance <= CLOSE_DISTANCE) {
            distance = 1;
        }
        
        force = Math.signum(power)*Math.abs(power/Math.pow(distance, declineOrder));

        p.setXY(a, b);
        forceVector.setVxVy(point, p);
        forceVector.setLenght(force);

        return forceVector;
        
    }

    @Override
    public void onPaint(Graphics2D g) {
        
        int x1 = (int)point.getX(), y1 = (int)point.getY(), radius = 10;
        //g.setColor(Color.BLACK);
        //g.fillOval(x1-14, y1-14, 28, 28);
        g.setColor(power > 0 ? DITRACT_COLOR : ATTRACT_COLOR);
        g.fillOval(x1 - radius, y1 - radius, 2*radius, 2*radius);
        
    }

}
