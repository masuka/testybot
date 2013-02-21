package masuka.robocode.utils.geometry;

/**
 * MyClass - a class by (your name here)
 */
public class Geometry {

    //
    // getDistance
    public static double getDistance(Gpoint p1, Gpoint p2) {
        return getDistance(p1.x, p1.y, p2.x, p2.y);
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double getDistance(Gpoint p, Gline l) {
        return Math.abs(p.x * l.getA() + p.y * l.getB() + l.getC());
    }

    public static double getDistance(Gline l, Gpoint p) {
        return getDistance(p, l);
    }

    //
    // getVectorHeading
    public static double getVectorHeading(Gvector v) {
        return getVectorHeading(v.vx, v.vy);
    }

    public static double getVectorHeading(double x, double y) {
        double absoluteBearing, atan;

        if (y != 0) {
            atan = Math.toDegrees(Math.atan(Math.abs(x / y)));
        } else {
            atan = 90;
        }

        if (x > 0) {
            if (y > 0) {
                absoluteBearing = atan;
            } else {
                absoluteBearing = 180 - atan;
            }
        } else if (y > 0) {
            absoluteBearing = 360 - atan;
        } else {
            absoluteBearing = 180 + atan;
        }

        return absoluteBearing;
    }

    //
    // getDotProduct
    public static double getDotProduct(double x1, double y1, double x2, double y2) {
        return x1*x2 + y1*y2;
    }

    public static double getDotProduct(Gvector v1, Gvector v2) {
        return getDotProduct(v1.vx, v1.vy, v2.vx, v2.vy);
    }

    public static double getDotProduct(Gpoint p1, Gpoint p2) {
        return getDotProduct(p1.x, p1.y, p2.x, p2.y);
    }

    //
    // getProection
    public static Gpoint getProection(Gpoint p, Gline l) {
        double alpha, a, b, c;
        a = l.getA();
        b = l.getB();
        c = l.getC();

        alpha = (a*p.x + b*p.y + c)/(a*a + b*b);

        return new Gpoint(p.x - alpha*a, p.y - alpha*b);
    }

    public static Gpoint getProection(Gline l, Gpoint p) {
        return getProection(p, l);
    }

    //
    // constructVector
    public static Gvector constructVector(Gvector dir, double length) {
        Gvector v = new Gvector(dir);
        v.normalize();
        v.multiply(length);
        return v;
    }
}
