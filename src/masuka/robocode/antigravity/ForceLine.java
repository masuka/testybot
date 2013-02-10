package masuka.robocode.antigravity;

import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gline;
import masuka.robocode.utils.geometry.Gvector;
import java.awt.*;

public class ForceLine extends ForceSource {

    private Gline line;

    public ForceLine(Gline l, double p) {
        setPower(p);
        line = l;
    }

    public ForceLine(double x1, double y1, double x2, double y2, double p) {
        this(new Gline(x1, y1, x2, y2), p);
    }

    public Gvector forceInPoint(double a, double b) {
        double distance = line.getDistance(new Gpoint(a, b));
        if (distance <= 20) {
            distance = 0.5;
        }
        double force;

        force = Math.signum(power)*power/Math.pow(distance, order);
        Gpoint p = new Gpoint(a, b);
        Gpoint pp = line.getProectoin(p);
        Gvector v = new Gvector(pp, p);
        v.setLenght(force);

        return v;
    }

    public void onPaint(Graphics2D g) {
        int x1 = (int) line.getP1().getX(), y1 = (int) line.getP1().getY();
        int x2 = (int) line.getP2().getX(), y2 = (int) line.getP2().getY();
        g.setColor(power > 0 ? DITRACT_COLOR : ATTRACT_COLOR);
        g.drawLine(x1, y1, x2, y2);
    }
    
}
