package masuka.robocode.utils.geometry;

/**
 * MyClass - a class by (your name here)
 */
public class Gvector extends Gobject {
    
    public static Gvector getZeroVector() {
        return new Gvector(0, 0);
    }
    
    protected double vx, vy;

    public Gvector(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public Gvector(Gvector v) {
        vx = v.vx;
        vy = v.vy;
    }

    public Gvector(Gpoint p1, Gpoint p2) {
        vx = p2.x - p1.x;
        vy = p2.y - p1.y;
    }

    public double getLength() {
        return Geometry.getDistance(0, 0, vx, vy);
    }

    public double getHeading() {
        return Geometry.getVectorHeading(this);
    }

    public double getDotProduct(Gvector v) {
        return Geometry.getDotProduct(this, v);
    }

    public void normalize() {
        double norm = Math.sqrt(vx * vx + vy * vy);
        if (norm == 0) {
            return;
        }
        vx = vx / norm;
        vy = vy / norm;
    }

    public void multiply(double m) {
        vx *= m;
        vy *= m;
    }

    public void add(Gvector v) {
        vx += v.vx;
        vy += v.vy;
    }

    public void setLenght(double l) {
        normalize();
        multiply(l);
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(double newVx) {
        vx = newVx;
    }

    public void setVy(double newVy) {
        vy = newVy;
    }
    
    public void setVxVy(Gpoint p1, Gpoint p2) {
        vx = p2.x - p1.x;
        vy = p2.y - p1.y;
    }
    
    public void setVxVy(double newVx, double newVy) {
        vx = newVx;
        vy = newVy;
    }
    
}
