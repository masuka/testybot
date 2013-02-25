package masuka.robocode.testy.playfield;

import java.util.ArrayList;
import java.util.HashMap;
import masuka.robocode.antigravity.ForceField;
import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gvector;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class PlayField extends ForceField { 
    
    public static final int MAX_ROBOT_VELOCITY = 8;
    
    protected HashMap<String, PlayRobot> robotsOnField = new HashMap<String, PlayRobot>();
    protected long time = 0;
    
    protected ArrayList<Gvector> myRobotVelocityHistory = new ArrayList<Gvector>();
    protected AdvancedRobot myRobot;
    protected Gpoint myRobotPoint = Gpoint.getZeroPoint();
    protected Gvector myRobotVector = Gvector.getZeroVector();
    protected Gvector myRobotVelocity = Gvector.getZeroVector();
    protected Gvector myRobotAcceleration = Gvector.getZeroVector();
    
    public PlayField(AdvancedRobot player) {
        
        myRobot = player;
        fieldHight = (int) player.getBattleFieldHeight();
        fieldWidth = (int) player.getBattleFieldWidth();
        myRobotVelocityHistory.add(Gvector.getZeroVector());
        
    }
    
    public boolean containsRobot(String robotName) {
        return robotsOnField.containsKey(robotName);
    }
    
    public long getTime() {
        return time;
    }
    
    @Override
    public void update() {
        time++;
        updateMyRobotCoordinates();
        updateMyRobotVelocity();
        updateMyRobotAcceleration();
        System.out.println("Time " + getTime() + ": Vector " + myRobotVector + ", Velocity " + myRobotVelocity + ", Accel " + myRobotAcceleration);
        
        super.update();
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
    
    public Gvector getMyRobotVector() {
        return myRobotVector;
    }
    
    public Gvector getMyRobotVelocity() {
        return myRobotVelocity;
    }
    
    public Gvector gerMyRobotAcceleration() {
        return myRobotAcceleration;
    }
    
    public void updateMyRobotCoordinates() {
        myRobotPoint.setXY(myRobot.getX(), myRobot.getY());
        myRobotVector.setVxVy(myRobotPoint);   
    }
    
    public void updateMyRobotVelocity() {
        
        myRobotVelocityHistory.add(new Gvector(myRobotVelocity));
        double angl = Math.toRadians(myRobot.getHeading());
        myRobotVelocity.setVxVy(Math.sin(angl), Math.cos(angl));
        myRobotVelocity.setLenght(myRobot.getVelocity());
        
    }
    
    public void updateMyRobotAcceleration() {
        
        Gvector velocityDelta = new Gvector(myRobotVelocity);
        velocityDelta.subtract(myRobotVelocityHistory.get(myRobotVelocityHistory.size() - 1));
        myRobotAcceleration.setVxVy(velocityDelta);
        
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
