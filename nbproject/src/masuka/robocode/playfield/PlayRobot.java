
package masuka.robocode.playfield;

import java.util.ArrayList;
import robocode.ScannedRobotEvent;
import robocode.AdvancedRobot;

public class PlayRobot {
    
    private static int HISTORY_LENGHT = 50;
    
    private PlayField playField;
    private ScannedRobotEvent lastScanEvent;
    private ArrayList<ScannedRobotEvent> scanEventHistory;
    private String name;
    
    public PlayRobot(PlayField pf, ScannedRobotEvent initialScanEvent) {
        
         scanEventHistory = new ArrayList<ScannedRobotEvent>();
         updateWithScanEvent(initialScanEvent);
         name = initialScanEvent.getName();
         playField = pf;
         
    }
    
    public void updateWithScanEvent(ScannedRobotEvent scanEvent) {
        
        if (scanEventHistory.size() > HISTORY_LENGHT) {
            scanEventHistory.remove(0);
        }
        scanEventHistory.add(scanEvent);
    }
    
    public ScannedRobotEvent getLastScanEvent() {
        return scanEventHistory.get(scanEventHistory.size() - 1);
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
    
}
