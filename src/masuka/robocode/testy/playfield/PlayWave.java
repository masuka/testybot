/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masuka.robocode.testy.playfield;

import java.awt.Graphics2D;
import masuka.robocode.antigravity.ForceSource;
import masuka.robocode.utils.geometry.Gpoint;
import robocode.Rules;

/**
 *
 * @author tgergiya
 */
public class PlayWave extends ForceSource {
    
    private double velocity = 0;
    private double radius = 0;
    private PlayField playField;
    private Gpoint center = Gpoint.getZeroPoint();
    
    public PlayWave(PlayField pf, double x, double y, double e) {
        
        playField = pf;
        center.setXY(x, y);
        velocity = Rules.getBulletSpeed(e);
        radius += velocity;
        
    }
    
    @Override
    public void update() {
        
        radius += velocity;
        
        if (radius > center.getDistance(playField.getMyRobotPoint())) {
            setTerminated();
        }
        
    }
    
    @Override
    public void onPaint(Graphics2D g) {
        
        int x1 = (int)center.getX(), y1 = (int)center.getY();
        //g.setColor(Color.BLACK);
        //g.fillOval(x1-14, y1-14, 28, 28);
        g.setColor(INACTIV_COLOR);
        g.drawOval(x1 - (int)radius, y1 - (int)radius, 2*(int)radius, 2*(int)radius);
        
    }
    
}
