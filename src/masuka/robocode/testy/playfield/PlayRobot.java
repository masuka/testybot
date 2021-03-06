
package masuka.robocode.testy.playfield;

import java.util.ArrayList;
import masuka.robocode.antigravity.ForcePoint;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayRobot extends ForcePoint {
    
    private static final int HISTORY_LENGHT = 50;
    private static final double BULLET_FIRED_ENERGY = 3.6;
    private static final double DEFAULT_POWER = 3000;
    
    private AdvancedRobot playerRobot;
    private PlayField playField;
    private ArrayList<ScannedRobotEvent> scanEventHistory;
    private String name;
    private double gunHeat = 0;
    
    public PlayRobot(PlayField pfield, ScannedRobotEvent initialScanEvent) {
        
        scanEventHistory = new ArrayList<ScannedRobotEvent>();
        name = initialScanEvent.getName();
        playerRobot = pfield.getMyRobot();
        updateWithScanEvent(initialScanEvent);
        power = DEFAULT_POWER;
        playField = pfield;
        
    }
    
    public final void updateWithScanEvent(ScannedRobotEvent scanEvent) {
        
        if (scanEventHistory.size() > HISTORY_LENGHT) {
            scanEventHistory.remove(0);
        }
        scanEventHistory.add(scanEvent);
        
        updateCoordinates();
       
    }
    
    public ScannedRobotEvent getLastScanEvent() {
        return getScanEvent(0);
    }
    
    private void updateCoordinates() {
       
        double absBearing = playerRobot.getHeading() + getBearing();
        point.setX(playerRobot.getX() + Math.sin(Math.toRadians(absBearing))*getDistance());
        point.setY(playerRobot.getY() + Math.cos(Math.toRadians(absBearing))*getDistance());
        
    }
    
    @Override
    public void update() {
        
        if (hasGunHeat()) {
            decreaseGunHeat();
        }
        
        if (getDistance() > 400) {
            setForceAttractive();
        } else if (getDistance() < 350) {
            setDistractive();
        }
        
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
        
        if (hasGunHeat()) {
            return false;
        }
        
        double energyChange = getEnergyDrop();
        boolean fired = energyChange < BULLET_FIRED_ENERGY && energyChange > 0;
        
        return fired;
        
    }
    
    public boolean hasGunHeat() {
        return gunHeat > 0;
    }
    
    private void decreaseGunHeat() {
        
        if (gunHeat > 0) {
            gunHeat -= playerRobot.getGunCoolingRate();
        }
        
        if (gunHeat < 0) {
            gunHeat = 0;
        }
        
    }
    
}
