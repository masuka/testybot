/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package masuka.robocode.utils.robot;

import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gline;
import masuka.robocode.utils.geometry.Gvector;
import masuka.robocode.antigravity.ForcePoint;
import masuka.robocode.antigravity.ForceField;
import java.awt.Graphics2D;

/**
 *
 * @author tgergiya
 */
public class VirtualBullet extends ForcePoint {

    private double energy;
    private Gvector heading;
    private Gline headLine;
    private double velosity;
    private ForceField field;
    private double radius = 40, aheadL = 200;

    public VirtualBullet(ForceField ff, double x, double y, Gvector h, double e) {
       super(x, y, 3000);
       field = ff;
       heading = new Gvector(h);
       headLine = new Gline(new Gpoint(x, y), heading);
       energy = e;
       velosity = 20 - 3*e;// 20 - 3*energy;
       heading.setLenght(velosity);
       incXY(heading);
       super.order = 1;
    }

    @Override
    public void updateTick() {
        super.updateTick();
        incXY(heading);
    }

    @Override
    public boolean isInactive() {
        return (getX() < 0 || getY() < 0 || getX() > field.getWidth() || getY() > field.getHeight());
    }

    @Override
    public Gvector forceInPoint(double a, double b) {

        double distance = headLine.getDistance(new Gpoint(a, b));
        Gpoint p = new Gpoint(a, b);
        if (point.getDistance(a, b) > aheadL || headLine.getDistance(a, b) > radius || heading.getDotProduct(new Gvector(point, p)) < 0) {
            return new Gvector(0, 0);
        }

        //if (distance <= 20)
        //    distance = 0.5;

        double force = Math.signum(power)*power/Math.pow(distance, order);
        Gpoint pp = headLine.getProectoin(p);
        Gvector v = new Gvector(pp, p);
        v.setLenght(force);

        return v;
    }

    @Override
    public void onPaint(Graphics2D g) {
        int x1 = (int)point.getX(), y1 = (int)point.getY(), r = 5;
        g.setColor(power > 0 ? DITRACT_COLOR : ATTRACT_COLOR);
        g.fillOval(x1 - r, y1 - r, 2*r, 2*r);
    }

}
