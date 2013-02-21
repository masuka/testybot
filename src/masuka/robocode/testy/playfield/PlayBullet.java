/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package masuka.robocode.testy.playfield;

import java.awt.Graphics2D;
import masuka.robocode.antigravity.ForcePoint;
import masuka.robocode.utils.geometry.Gline;
import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gvector;

/**
 *
 * @author tgergiya
 */
public class PlayBullet extends ForcePoint {

    private Gvector heading;
    private Gline headLine;
    private double velosity;
    private PlayField playField;
    private double sidesDistance = 50;
    private double aheadDisMultiplier = 16;

    public PlayBullet(PlayField pf, double x, double y, Gvector h, double energy) {
        
       super(x, y, 3000);
       playField = pf;
       heading = new Gvector(h);
       headLine = new Gline(new Gpoint(x, y), heading);
       velosity = 20 - 3*energy;
       heading.setLenght(velosity);
       incXY(heading);
       super.declineOrder = 1;
       
    }
    
    @Override
    public void update() {
        
        incXY(heading);
        
        setInactive(!isAbleToHitMyRobot());
        if (isOutOfField()) {
            setTerminated();
        }
        
    }
    
    public boolean isAbleToHitMyRobot() {
        
        Gpoint myRoboPoint = playField.getMyRobotPoint();
        if (heading.getDotProduct(new Gvector(point, myRoboPoint)) < 0) {
            return false;
        }
        
        Gpoint myRoboProj = headLine.getProectoin(myRoboPoint);
        double timeToHitMyRobot = myRoboPoint.getDistance(myRoboProj) - 18 / PlayField.MAX_ROBOT_VELOCITY;
        double timeToHitVbullet = point.getDistance(myRoboProj) - 18 / velosity;
        if (timeToHitMyRobot > timeToHitVbullet) {
            return false;
        }
        
        return true;
    }
    
    public boolean isOutOfField() {
        return getX() < 0 || getY() < 0 || getX() > playField.getFieldWidth() || getY() > playField.getFieldHight();
    }

    @Override
    public Gvector forceInPoint(double a, double b) {
        
        if (isInactive()) {
            return Gvector.getZeroVector();
        }
        
        double distance = headLine.getDistance(new Gpoint(a, b));
        Gpoint p = new Gpoint(a, b);
        Gpoint pp = headLine.getProectoin(p);
        if (point.getDistance(a, b) > aheadDisMultiplier * velosity || headLine.getDistance(a, b) > sidesDistance
                || heading.getDotProduct(new Gvector(point, p)) < 0) {
            return Gvector.getZeroVector();
        }
        
        if (distance <= CLOSE_DISTANCE) {
            distance = 1;
        }
        
        double force = Math.signum(power)*power/Math.pow(distance, declineOrder);
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
