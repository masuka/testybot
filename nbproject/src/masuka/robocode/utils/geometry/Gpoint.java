package masuka.robocode.utils.geometry;

/**
 * MyClass - a class by (your name here)
 */
public class Gpoint extends Gobject {

    protected double x, y;

    public Gpoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Gpoint(Gpoint p) {
        this(p.getX(), p.getY());
    }

    public double getDistance(Gline l) {
        return Geometry.getDistance(l, this);
    }

    public double getDistance(Gpoint p) {
        return Geometry.getDistance(p, this);
    }

    public double getDistance(double a, double b) {
        return Geometry.getDistance(x, y, a, b);
    }
    
    public Gpoint getProection(Gline l) {
        return Geometry.getProection(this, l);
    }

    public void incX(double a) {
        x+=a;
    }
    
    public void incY(double b) {
        y+=b;
    }
    
    public void incXY(double a, double b) {
        incX(a);
        incY(b);
    }

    public void incXY(Gvector vec) {
        incX(vec.getVx());
        incY(vec.getVy());
    }

    public void setXY(Gpoint p) {
        setXY(p.x, p.y);
    }

    public void setXY(double a, double b) {
        setX(a);
        setY(b);
    }

    public void setX(double a) {
        x = a;
    }

    public void setY(double b) {
        y = b;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
