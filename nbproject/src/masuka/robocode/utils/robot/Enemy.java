package masuka.robocode.utils.robot;

import java.util.ArrayList;
import robocode.*;
import masuka.robocode.utils.geometry.*;
import masuka.robocode.antigravity.*;

public class Enemy extends ForcePoint {

    private String name;
    private AdvancedRobot robot;
    private double energy, prevEnergy;
    private boolean bulletFired = false;
    private ArrayList<Gvector> vArr = new ArrayList<Gvector>();
    private ArrayList<Gpoint> pArr = new ArrayList<Gpoint>();

    public Enemy(AdvancedRobot r, String n, double p) {
        super(0, 0, p);
        robot = r;
        name = n;
    }

    public void update(ScannedRobotEvent e) {
        
        double absBearing = robot.getHeading() + e.getBearing();
        setX(robot.getX() + Math.sin(Math.toRadians(absBearing))*e.getDistance());
        setY(robot.getY() + Math.cos(Math.toRadians(absBearing))*e.getDistance());
        double energyDrop = energy - e.getEnergy();
        if (energyDrop < 3.5 && energyDrop > 0)
            bulletFired = true;
        else
            bulletFired = false;
        prevEnergy = energy;
        energy = e.getEnergy();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public double getEnergyDrop() {
        return prevEnergy - energy;
    }

    public boolean fired() {
        return bulletFired;
    }

}
