package masuka.robocode.playfield;

import java.util.HashMap;
import masuka.robocode.antigravity.ForceField;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayField { 
    
    private int fieldHight;
    private int fieldWidth;
    
    private HashMap<String, PlayRobot> robotsOnField;
    private AdvancedRobot playerRobot;
    private ForceField forceField = null;
    
    public PlayField(AdvancedRobot player) {
        
        playerRobot = player;
        fieldHight = (int) player.getBattleFieldHeight();
        fieldWidth = (int) player.getBattleFieldWidth();
        robotsOnField = new HashMap<String, PlayRobot>();
        
    }
    
    public void setForceField(ForceField field) {
        forceField = field;
    }
    
    public ForceField getForceField() {
        return forceField;
    } 
    
    public boolean containsRobot(String robotName) {
        return robotsOnField.containsKey(robotName);
    }
    
    public PlayRobot getRobot(String robotName) {
        return robotsOnField.get(robotName);
    }
    
    public int getRobotsCount() {
        return robotsOnField.size();
    }
    
    public PlayRobot getNearestRobot() {
        
        double minDist = Double.POSITIVE_INFINITY;
        PlayRobot minDistRobot = null;
        for (PlayRobot robot : robotsOnField.values()) {
            if (robot.getDistance() < minDist) {
                minDist = robot.getDistance();
                minDistRobot = robot;
            }
        }
        
        return minDistRobot;
        
    }
    
    public AdvancedRobot getPlayerRobot() {
        return playerRobot;
    }
    
    public void registerScannedRobotEvent(ScannedRobotEvent scanEvent) {
        
        String robotName = scanEvent.getName();
        
        if (!this.containsRobot(robotName)) {
            PlayRobot robot = new PlayRobot(this, scanEvent);
            robotsOnField.put(robotName, robot);
            if (forceField != null) {
                forceField.addPoint(robotName, robot.getPoint(), 3000);
            }
        }
        
        robotsOnField.get(robotName).updateWithScanEvent(scanEvent);
        
    }
    
}
