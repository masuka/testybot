package masuka.robocode.antigravity;

import java.awt.*;
import masuka.robocode.utils.geometry.*;

public class ForceLine extends ForceSource {

    private Gline line;

    public ForceLine(Gline l, double p) {
        setFrocePower(p);
        line = l;
    }

    public ForceLine(double x1, double y1, double x2, double y2, double p) {
        this(new Gline(x1, y1, x2, y2), p);
    }
    
    private Gpoint p = Gpoint.getZeroPoint();
    private Gpoint pp = Gpoint.getZeroPoint();
    
    @Override
    public Gvector forceInPoint(double a, double b) {
        
        p.setXY(a, b);
        double distance = line.getDistance(p);
        if (distance <= CLOSE_DISTANCE) {
            distance = 1;
        }
        double force;

        force = Math.signum(power)*power/Math.pow(distance, declineOrder);
        Geometry.calculateProection(p, line, pp);
        forceVector.setVxVy(pp, p);
        forceVector.setLenght(force);

        return forceVector;
    }

    @Override
    public void onPaint(Graphics2D g) {
        int x1 = (int) line.getP1().getX(), y1 = (int) line.getP1().getY();
        int x2 = (int) line.getP2().getX(), y2 = (int) line.getP2().getY();
        g.setColor(power > 0 ? DITRACT_COLOR : ATTRACT_COLOR);
        g.drawLine(x1, y1, x2, y2);
    }
    
}
