package masuka.robocode.utils.geometry;

/**
 * MyClass - a class by (your name here)
 */
public class Gline extends Gobject {

    protected Gpoint p1, p2;
    protected double a, b, c;

    public Gline(Gpoint point1, Gpoint point2) {
        p1 = point1;
        p2 = point2;

        double dX, dY, norm;
        dX = p2.x - p1.x;
        dY = p2.y - p1.y;
        norm = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        a = -dY/norm;
        b = dX/norm;
        c = (p1.x*dY - p1.y*dX)/norm;
    }

    public Gline(double x1, double y1, double x2, double y2) {
        this(new Gpoint(x1, y1), new Gpoint(x2, y2));
    }

    public Gline(Gpoint p, Gvector h) {
        this(p, new Gpoint(p.getX() + h.getVx(), p.getY() + h.getVy()));
    }

    public Gline(Gline l) {
        this(new Gpoint(l.getP1()), new Gpoint(l.getP2()));
    }

    public double getA() {
        return a;
        //return p1.y - p2.y;
    }

    public double getB() {
        return b;
        //return p2.x - p1.x;
    }

    public double getC() {
        return c;
        //return p1.x*(p2.y - p1.y) + p1.y*(p1.x - p2.x);
    }

    public Gpoint getP1() {
        return p1;
    }

    public Gpoint getP2() {
        return p2;
    }

    public double getDistance(Gpoint p) {
        return Geometry.getDistance(p, this);
    }

    public double getDistance(double a, double b) {
        return Geometry.getDistance(new Gpoint(a, b), this);
    }
    
    public Gpoint getProectoin(Gpoint p) {
        return Geometry.getProection(p, this);
    }
}
