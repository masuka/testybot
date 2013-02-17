package masuka.robocode.testybot;

import java.awt.*;
import java.util.ArrayList;
import masuka.robocode.antigravity.*;
import masuka.robocode.playfield.*;
import masuka.robocode.utils.geometry.*;
import masuka.robocode.utils.robot.*;
import robocode.*;
import robocode.util.*;

public class Testy extends AdvancedRobot {

    protected ForceField forceField;
    protected PlayField playField;
    protected ForcePoint midPoint;

    protected int w, h;
    protected int radius = 220;
    protected int tick = 0;

    private ArrayList<Gpoint> movementHistory = new ArrayList<Gpoint>();
    
    private void setUpForceField() {
        
        h = (int)getBattleFieldHeight();
        w = (int)getBattleFieldWidth();

        forceField = new ForceField(w, h);
        midPoint = new ForcePoint(w/2, radius + h/2, -2000);
        forceField.addSource("middle point", midPoint);

        forceField.addLine("ul", 0, 4*h/5, h/5, h, 5000);
        forceField.addLine("ll", 0, h/5, h/5, 0, 5000);
        forceField.addLine("ur", w, 4*h/5, w - h/5, h, 5000);
        forceField.addLine("lr", w, h/5, w - h/5, 0, 5000);

        forceField.addLine("leftw", 5, 0, 5, h, 5000);
        forceField.addLine("rigthw", w - 5, 0, w - 5, h, 5000);
        forceField.addLine("loww", 0, 5, w, 5, 5000);
        forceField.addLine("upw", 0, h - 5, w, h - 5, 5000);
    
    }
    
    private void setUpPlayField() {
        
        playField = new PlayField(this);
        playField.setForceField(forceField);
    
    }
    
    @Override
    public void run() {

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        
        setUpForceField();
        setUpPlayField();
        
        while (true) {    
            doAdditionalActions();
            setMoveAlongForceFiled();
            execute();
        }

    }

    @Override
    public void onScannedRobot(ScannedRobotEvent scanEvent) {
        
        playField.registerScannedRobotEvent(scanEvent);
        
        PlayRobot enemy = playField.getNearestRobot();
        Gvector vecBul;
        /*if (enemy == null) {
            enemy = new Enemy(this, scanEvent.getName(), 3000);
            forceField.addSource("enemy", enemy);
        }
        enemy.update(scanEvent);*/
        
        if (enemy.hasFired()) {
           vecBul = new Gvector(enemy.getPoint(), new Gpoint(getX(), getY()));
           forceField.addSource(new VirtualBullet(forceField, enemy.getX(), enemy.getY(), vecBul, enemy.getEnergyDrop()));
        }

        if (enemy.getDistance() > 400) {
            forceField.getSource(enemy.getName()).setAttractive();
        } else if (enemy.getDistance() < 350) {
            forceField.getSource(enemy.getName()).setDistractive();
        }
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
    }

    private void doAdditionalActions() {
        
        tick++;
        forceField.updateTick();
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

    private void setMoveAlongForceFiled() {
        Gvector f = forceField.forceInPoint(getX(), getY());
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
        forceField.onPaint(g);
    }
}
