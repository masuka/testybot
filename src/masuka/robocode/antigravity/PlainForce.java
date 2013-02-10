package masuka.robocode.antigravity;

import masuka.robocode.utils.geometry.Gvector;
import java.awt.*;

public class PlainForce extends ForceSource {

    private Gvector vector;

    public PlainForce(Gvector v, double p) {
        setPower(p);
        vector = v;
    }

    public Gvector forceInPoint(double a, double b) {
        return new Gvector(vector);
    }

    public void setVx(double newvx) {
        vector.setVx(newvx);
        vector.setLenght(power);
    }

    public void setVy(double newvy) {
        vector.setVy(newvy);
        vector.setLenght(power);
    }

    public void setVxVy(double newvx, double newvy) {
        vector.setVx(newvx);
        vector.setVy(newvy);
        vector.setLenght(power);
    }

    public void onPaint(Graphics2D g) {
    }

}
