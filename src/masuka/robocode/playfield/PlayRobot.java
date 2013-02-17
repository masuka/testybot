
package masuka.robocode.playfield;

import java.util.ArrayList;
import masuka.robocode.antigravity.ForceField;
import masuka.robocode.utils.geometry.Gpoint;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayRobot {
    
    private static int HISTORY_LENGHT = 50;
    private static double BULLET_FIRED_ENERGY = 3.6;
    
    private PlayField playField;
    private ForceField forceField;
    private AdvancedRobot playerRobot;
    private ArrayList<ScannedRobotEvent> scanEventHistory;
    private String name;
    private Gpoint point;
    
    public PlayRobot(PlayField pfield, ScannedRobotEvent initialScanEvent) {
        
         scanEventHistory = new ArrayList<ScannedRobotEvent>();
         name = initialScanEvent.getName();
         playField = pfield;
         playerRobot = pfield.getPlayerRobot();
         forceField = pfield.getForceField();
         point = new Gpoint(0, 0);
         updateWithScanEvent(initialScanEvent);
    }
    
    public final void updateWithScanEvent(ScannedRobotEvent scanEvent) {
        
        if (scanEventHistory.size() > HISTORY_LENGHT) {
            scanEventHistory.remove(0);
        }
        scanEventHistory.add(scanEvent);
        
        updatePoint();
       
    }
    
    public ScannedRobotEvent getLastScanEvent() {
        return getScanEvent(0);
    }
    
    private void updatePoint() {
       
        double absBearing = playerRobot.getHeading() + getBearing();
        point.setX(playerRobot.getX() + Math.sin(Math.toRadians(absBearing))*getDistance());
        point.setY(playerRobot.getY() + Math.cos(Math.toRadians(absBearing))*getDistance());
        
    }
    
    public double getX() {
        return point.getX();        
    }
    
    public double getY() {
        return point.getY();
    }
    
    public Gpoint getPoint() {
        return point;
    }
    
    public ScannedRobotEvent getScanEvent(int k) {
        
        if (k > scanEventHistory.size() - 1) {
            k = scanEventHistory.size() - 1;
        }
        return scanEventHistory.get(scanEventHistory.size() - k - 1);
        
    }
    
    public double getEnergy() {
        return getLastScanEvent().getEnergy();
    }
    
    public double getBearing() {
        return getLastScanEvent().getBearing();
    }
    
    public double getDistance() {
        return getLastScanEvent().getDistance();
    }
    
    public double getHeading() {
        return getLastScanEvent().getHeading();
    }
    
    public String getName() {
        return name;
    }
    
    public double getEnergyChange() {
        return getEnergy() - getScanEvent(1).getEnergy();
    }
    
    public double getEnergyDrop() {
        return -1 * getEnergyChange();
    }
    
    public boolean hasFired() {
        
        double energyChange = getEnergyDrop();
        return energyChange < BULLET_FIRED_ENERGY && energyChange > 0;
    
    }
    
}
