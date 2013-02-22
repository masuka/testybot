package masuka.robocode.antigravity;

import java.awt.*;
import java.util.*;
import masuka.robocode.utils.geometry.Gpoint;
import masuka.robocode.utils.geometry.Gvector;

public class ForceField extends ForceSource {

    private HashMap<String, ForceSource> sourcesOnField = new HashMap<String, ForceSource>();
    protected int fieldWidth, fieldHight;
    private String defName = "Source";
    private int sourceCount = 0;
    
    public ForceField() {
        this(0, 0);
    }
    
    public ForceField(double w, double h) {
        fieldWidth = (int) w;
        fieldHight = (int) h;
    }

    public ForceSource getSource(String name) {
        return sourcesOnField.get(name);
    }

    public int getFieldHight() {
        return fieldHight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public Collection<ForceSource> getAllForceSources() {
        return sourcesOnField.values();
    }

    public void addForceSource(String name, ForceSource fs) {
        sourcesOnField.put(name, fs);
    }

    public void addForceSource(ForceSource fs) {
        sourcesOnField.put(defName + sourceCount++, fs);
    }

    public void addForcePoint(String name, double x, double y, double power) {
        sourcesOnField.put(name, new ForcePoint(x, y, power));
    }

    public void addForcePoint(String name, Gpoint gp, double power) {
        sourcesOnField.put(name, new ForcePoint(gp, power));
    }
    
    public void addForcePoint(String name, Gpoint gp) {
        sourcesOnField.put(name, new ForcePoint(gp));
    }

    public void addForceLine(String name, double x1, double y1,
            double x2, double y2, double power) {
        sourcesOnField.put(name, new ForceLine(x1, y1, x2, y2, power));
    }

    @Override
    public Gvector forceInPoint(double a, double b) {
       
        forceVector.setVxVy(0, 0);
        ForceSource fs;
        for (String s : sourcesOnField.keySet()) {
            fs = sourcesOnField.get(s);
            forceVector.add(fs.forceInPoint(a, b));
        }

        return forceVector;
    }

    protected ArrayList<String> terminatedNames = new ArrayList<String>();

    @Override
    public void update() {
        
        terminatedNames.clear();
        for (String key:sourcesOnField.keySet()) {
           ForceSource fs = sourcesOnField.get(key);
            fs.update();
            if (fs.isTerminated()) {
               terminatedNames.add(key);
            }
        }

        for (String key:terminatedNames) {
           sourcesOnField.remove(key);
        }
     
    }

    @Override
    public void onPaint(Graphics2D g) {
       
        int x, y;
        Gvector v;
        ForceSource fs;
        int step = Math.min(fieldHight, fieldWidth) / 30;
        
        for (x = step/2; x < fieldWidth - step/2; x += step) {
            for (y = step/2; y < fieldHight - step/2; y += step) {
                v = forceInPoint(x, y);
                if (v.getLength() > 1) {
                    g.setColor(ACTIVE_COLOR);
                } else {
                     g.setColor(INACTIV_COLOR);
                }
                
                v.setLenght(step/2);    
                g.drawLine(x, y, x + (int)v.getVx(), y + (int)v.getVy());
            }
        }

        for (String s:sourcesOnField.keySet()) {
            sourcesOnField.get(s).onPaint(g);
        }
        
    }

}
