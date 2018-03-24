package it.unibo.oop.cctan.model;

import java.awt.geom.Area;

public class ShuttleImpl extends FixedItem implements Shuttle {

    private static final double WIDTH = 0.05;
    private static final double HEIGHT = 0.05;
    private double angle;

    public ShuttleImpl(Model model) {
        super(model);
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    @Override
    public Area getImpactArea() {
        return null;
    }
}
