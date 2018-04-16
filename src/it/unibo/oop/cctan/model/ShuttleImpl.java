package it.unibo.oop.cctan.model;

import java.awt.geom.Area;

import javafx.geometry.Point2D;


public class ShuttleImpl extends FixedItem implements Shuttle {

    public static final double WIDTH = 0.05;
    public static final double HEIGHT = 0.05;
    private double angle;

    public ShuttleImpl(Model model) {
        super(model, new Point2D(0, 0));
    }

    /** 
     * {@inheritDoc}
     */
    public double getAngle() {
        return this.angle;
    }

    /** 
     * {@inheritDoc}
     */
    public void setAngle(final double angle) {
        this.angle = angle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Area getImpactArea() {
        return null;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Point2D getTop() {
        // TODO Auto-generated method stub
        return null;
    }
}
