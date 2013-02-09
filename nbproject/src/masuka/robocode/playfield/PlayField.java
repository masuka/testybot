package masuka.robocode.playfield;

import java.util.ArrayList;
import java.util.HashMap;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayField { 
    
    private int fieldHight;
    private int fieldWidth;
    
    private HashMap<String, PlayRobot> robotsOnField;
    private AdvancedRobot playerRobot;
    
    public PlayField(AdvancedRobot player) {
        
        playerRobot = player;
        fieldHight = (int) player.getBattleFieldHeight();
        fieldWidth = (int) player.getBattleFieldWidth();
        robotsOnField = new HashMap<String, PlayRobot>();
        
    }
    
    public boolean containsRobot(String robotName) {
        return robotsOnField.containsKey(robotName);
    }
    
    public PlayRobot getRobot(String robotName) {
        return robotsOnField.get(robotName);
    }
    
    public void registerScannedRobotEvent(ScannedRobotEvent scanEvent) {
        
        String robotName = scanEvent.getName();
        
        if (!this.containsRobot(robotName)) {
            robotsOnField.put(robotName, new PlayRobot(this, scanEvent));
        }
        
        robotsOnField.get(robotName).updateWithScanEvent(scanEvent);
        
    }
    
}
