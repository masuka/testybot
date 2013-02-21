package masuka.robocode.testy.playfield;

import java.util.HashMap;
import masuka.robocode.antigravity.ForceField;
import masuka.robocode.utils.geometry.Gpoint;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayField extends ForceField { 
    
    public static final int MAX_ROBOT_VELOCITY = 8;
    
    protected HashMap<String, PlayRobot> robotsOnField = new HashMap<String, PlayRobot>();
    protected AdvancedRobot myRobot;
    protected Gpoint myRobotPoint = Gpoint.getZeroPoint();
    protected long time = 0;
    
    public PlayField(AdvancedRobot player) {
        
        myRobot = player;
        fieldHight = (int) player.getBattleFieldHeight();
        fieldWidth = (int) player.getBattleFieldWidth();
 
    }
    
    public boolean containsRobot(String robotName) {
        return robotsOnField.containsKey(robotName);
    }
    
    public long getTime() {
        return time;
    }
    
    @Override
    public void update() {
        myRobotPoint.setXY(myRobot.getX(), myRobot.getY());
        super.update();
        time++;
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
    
    public AdvancedRobot getMyRobot() {
        return myRobot;
    }
    
    public Gpoint getMyRobotPoint() {
        return myRobotPoint;
    }
    
    public void registerScannedRobotEvent(ScannedRobotEvent scanEvent) {
        
        String robotName = scanEvent.getName();
        
        if (!this.containsRobot(robotName)) {
            PlayRobot robot = new PlayRobot(this, scanEvent);
            robotsOnField.put(robotName, robot);
            
            addForceSource(robotName, robot);
            
        }
        
        robotsOnField.get(robotName).updateWithScanEvent(scanEvent);
        
    }
    
}
