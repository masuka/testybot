package masuka.robocode.testybot;

import masuka.robocode.utils.robot.*;
import masuka.robocode.utils.geometry.*;
import masuka.robocode.antigravity.*;
import robocode.*;
import robocode.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Testy extends AdvancedRobot {

    protected ForceField field;
    protected Enemy enemy;
    protected ForcePoint midPoint;

    protected int w, h;
    protected int radius = 220;
    protected int tick = 0;

    private ArrayList<Gvector> vArr = new ArrayList<Gvector>();
    private ArrayList<Gpoint> movementHistory = new ArrayList<Gpoint>();

    @Override
    public void run() {

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        h = (int)getBattleFieldHeight();
        w = (int)getBattleFieldWidth();

        field = new ForceField(w, h);
        midPoint = new ForcePoint(w/2, radius + h/2, -2000);
        field.addSource("middle point", midPoint);

        field.addLine("ul", 0, 4*h/5, h/5, h, 5000);
        field.addLine("ll", 0, h/5, h/5, 0, 5000);
        field.addLine("ur", w, 4*h/5, w - h/5, h, 5000);
        field.addLine("lr", w, h/5, w - h/5, 0, 5000);

        field.addLine("leftw", 5, 0, 5, h, 5000);
        field.addLine("rigthw", w - 5, 0, w - 5, h, 5000);
        field.addLine("loww", 0, 5, w, 5, 5000);
        field.addLine("upw", 0, h - 5, w, h - 5, 5000);


        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        while (true) {
            moveAlongForceFiled(field);
            doStuff();
            execute();
        }

    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        
        Gvector vecBul;
        if (enemy == null) {
            enemy = new Enemy(this, event.getName(), 3000);
            field.addSource("enemy", enemy);
        }
        enemy.update(event);
        
        double velBul;
        if (enemy.fired()) {
           velBul = 20 - 3*enemy.getEnergyDrop();
           vecBul = new Gvector(enemy.getPoint(), movementHistory.get(movementHistory.size()-1));
           vecBul.setLenght(velBul);
           field.addSource(new VirtualBullet(field, enemy.getX(), enemy.getY(), vecBul, enemy.getEnergyDrop()));
        }

        if (event.getDistance() > 400) {
            enemy.setPower(-3000);
        } else if (event.getDistance() < 350) {
            enemy.setPower(3000);
        }
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
    }

    private void doStuff() {
        tick++;
        field.updateTick();
        vArr.add(getVectorHeading());
        midPoint.setXY(w/2 + radius*Math.cos(3*tick*Math.PI/180), h/2 + radius*Math.sin(3*tick*Math.PI/180));
        if (tick%40 == 0 && Math.random()*10<5) {
            midPoint.invertPower();
        }

        movementHistory.add(new Gpoint(getX(), getY()));
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
    }

    @Override
    public void onHitWall(HitWallEvent e) {
    }

    private void moveTo(double x, double y) {
        setTurnRight(getRelativeBearingTo(x, y));
        setAhead(getDistanceTo(x, y));
    }

    private void moveAlongForceFiled(ForceField ff) {
        Gvector f = ff.forceInPoint(getX(), getY());
        moveInDirection(f.getVx(), f.getVy(), Double.POSITIVE_INFINITY);
    }

    private void moveInDirection(double dx, double dy, double distance) {
        double relativeBearing = getRelativeBearingTo(getX() + dx, getY() + dy);
        if (relativeBearing > 90) {
            relativeBearing -= 180;
            distance *= -1;
        } else if (relativeBearing < -90) {
            relativeBearing += 180;
            distance *= -1;
        }

        setTurnRight(relativeBearing);
        setAhead(distance);
    
    }

    private Gvector getVectorHeading() {
        double angl = Math.toRadians(getHeading());
        double vel = getVelocity();
        return new Gvector(Math.sin(angl)*vel, Math.cos(angl)*vel);
    }

    private double getRelativeBearingTo(double x, double y) {
        return Utils.normalRelativeAngleDegrees(getAbsoluteBearingTo(x, y) - getHeading());
    }

    private double getAbsoluteBearingTo(double x, double y) {
        return Geometry.getVectorHeading(x - getX(), y - getY());
    }

    private double getDistanceTo(double x, double y) {
        return Geometry.getDistance(getX(), getY(), x, y);
    }

    private ArrayList<VirtualBullet> vbullets = new ArrayList<VirtualBullet>();

    @Override
    public void onPaint(Graphics2D g) {
        field.onPaint(g);
    }
}
