package masuka.robocode.antigravity;

import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gvector;
import java.util.*;
import java.awt.*;

public class ForceField extends ForceSource {

    private HashMap<String, ForceSource> sources = new HashMap<String, ForceSource>();
    private int width, height;
    private String defName = "Source";
    private int sourceCount = 0;

    public ForceField(double w, double h) {
        width = (int) w;
        height = (int) h;
    }

    public ForceSource getSource(String name) {
        return sources.get(name);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Collection<ForceSource> getAllSources() {
        return sources.values();
    }

    public void addSource(String name, ForceSource fs) {
        sources.put(name, fs);
    }

    public void addSource(ForceSource fs) {
        sources.put(defName + sourceCount++, fs);
    }

    public void addPoint(String name, double x, double y, double power) {
        sources.put(name, new ForcePoint(x, y, power));
    }

    public void addPoint(String name, Gpoint gp, double power) {
        sources.put(name, new ForcePoint(gp, power));
    }

    public void addLine(String name, double x1, double y1,
            double x2, double y2, double power) {
        sources.put(name, new ForceLine(x1, y1, x2, y2, power));
    }

    public Gvector forceInPoint(double a, double b) {
        Gvector fv = new Gvector(0, 0);
        ForceSource fs;
        for (String s : sources.keySet()) {
            fs = sources.get(s);
            fv.add(fs.forceInPoint(a, b));
        }

        return fv;
    }

    protected ArrayList<String> removeNames = new ArrayList<String>();

    public void updateTick() {
        tick++;
        ForceSource fs;
        removeNames.clear();
        for (String s:sources.keySet()) {
            fs = sources.get(s);
            fs.updateTick();
            if (fs.isInactive())
                removeNames.add(s);
        }

        for (String s:removeNames) {
           sources.remove(s);
        }
     
    }

    public void onPaint(Graphics2D g) {
       
        int step = 20, x, y;
        Gvector v;
        ForceSource fs;

        for (x = 10; x < width - 10; x += step) {
            for (y = 10; y < height - 10; y += step) {
//                if (x > getX() - 25 && x < getX() + 25 && y > getY() - 25 && y < getY() + 25
//                        || enemy != null && x > enemy.getX() - 25 && x < enemy.getX() + 25 && y > enemy.getY() - 25 && y < enemy.getY() + 25) {
//                    continue;
//                }
                v = forceInPoint(x, y);
                v.setLenght(10);
                g.setColor(INACTIV_COLOR);

                for (String s:sources.keySet()) {
                   fs = sources.get(s);
                   if (fs.forceInPoint(x, y).getLength() > 1) {
                       g.setColor(Color.ORANGE);
                       break;
                   }
                }

                g.drawLine(x, y, x + (int)v.getVx(), y + (int)v.getVy());
            }
        }

        for (String s:sources.keySet())
            sources.get(s).onPaint(g);
        
    }

}
