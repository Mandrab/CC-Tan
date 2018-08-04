package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javafx.geometry.Point2D;

public class LaserAgent extends BulletImpl implements Bullet {

    private static final double DEFAULT_SPEED = 0.005;
    private double width;
    private double height;


    protected LaserAgent(LaserBuilder builder) {
        super(builder);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Shape getShape() {
        //to implement... 
        return new Line2D.Double();
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    protected void updatePos() {
        super.updatePos();
        Rectangle2D shape = this.getShape().getBounds2D();
        this.width = shape.getWidth();
        this.height = shape.getHeight();
    }

    @Override
    protected double getDefaultSpeed() {
        return DEFAULT_SPEED;
    }

    @Override
    protected void updateAngle(SquareAgent rect) {
        //nothing to do here
    }

    public static class LaserBuilder extends BulletImpl.BulletBuilder {

        /** 
         * {@inheritDoc}
         */
        @Override
        public LaserAgent build() {
            super.validate();
            return new LaserAgent(this);
        }
    }
}
