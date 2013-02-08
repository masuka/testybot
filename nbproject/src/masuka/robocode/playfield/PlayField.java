package masuka.robocode.playfield;

import java.util.ArrayList;
import java.util.HashMap;
import robocode.AdvancedRobot;

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
    
    public void addRobot() {
        
    }
    
}
