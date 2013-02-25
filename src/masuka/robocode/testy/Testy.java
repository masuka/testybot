package masuka.robocode.testy;

import java.awt.*;
import java.util.ArrayList;
import masuka.robocode.antigravity.*;
import masuka.robocode.testy.playfield.PlayBullet;
import masuka.robocode.testy.playfield.PlayField;
import masuka.robocode.testy.playfield.PlayRobot;
import masuka.robocode.testy.playfield.PlayWave;
import masuka.robocode.utils.geometry.*;
import robocode.*;
import robocode.util.*;

public class Testy extends AdvancedRobot {

    protected PlayField playField;
    protected ForcePoint midPoint;

    protected int w, h;
    protected int radius = 220;
    
    private void setUpPlayField() {
        
        playField = new PlayField(this);
        
        h = playField.getFieldHight();
        w = playField.getFieldWidth();

        midPoint = new ForcePoint(w/2, radius + h/2, -3000);
        playField.addForceSource("middle point", midPoint);

        /*playField.addForceLine("ul", 0, 4*h/5, h/5, h, 2000);
        playField.addForceLine("ll", 0, h/5, h/5, 0, 2000);
        playField.addForceLine("ur", w, 4*h/5, w - h/5, h, 2000);
        playField.addForceLine("lr", w, h/5, w - h/5, 0, 2000);*/

        playField.addForceLine("leftw", 5, 0, 5, h, 3000);
        playField.addForceLine("rigthw", w - 5, 0, w - 5, h, 3000);
        playField.addForceLine("loww", 0, 5, w, 5, 3000);
        playField.addForceLine("upw", 0, h - 5, w, h - 5, 3000);
        
    }
    
    @Override
    public void run() {

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        
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
        
        if (enemy.hasFired()) {
           vecBul = new Gvector(enemy.getPoint(), new Gpoint(getX(), getY()));
           playField.addForceSource(new PlayBullet(playField, enemy.getX(), enemy.getY(), vecBul, enemy.getEnergyDrop()));
           playField.addForceSource(new PlayWave(playField, enemy.getX(), enemy.getY(), enemy.getEnergyDrop()));
        }
        
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
    }

    private void doAdditionalActions() {
        
        playField.update();
        
        long time = playField.getTime();
        midPoint.setXY(w/2 + radius*Math.cos(3*time*Math.PI/180), h/2 + radius*Math.sin(3*time*Math.PI/180));
        if (time%40 == 0 && Math.random()*10<5) {
            midPoint.invertForcePower();
        }
    
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
    }

    @Override
    public void onHitWall(HitWallEvent e) {
    }

    private void setMoveAlongForceFiled() {
        Gvector f = playField.forceInPoint(getX(), getY());
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

    public Gvector getVectorHeading() {
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

    private ArrayList<PlayBullet> vbullets = new ArrayList<PlayBullet>();

    @Override
    public void onPaint(Graphics2D g) {
        playField.onPaint(g);
    }
}
