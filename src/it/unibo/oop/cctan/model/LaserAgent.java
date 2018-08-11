package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import javafx.geometry.Point2D;

public class LaserAgent extends BulletImpl implements Bullet {

    private static final double DEFAULT_SPEED = 0.005;
    private static final double DEFAULT_LENGTH = 0.125;
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
        return Color.RED;
    }
    
    @Override
    protected boolean intersectsWith(final FixedItem item) {
        return this.getShape().intersects(item.getShape().getBounds2D());
    }
    
    @Override
    public Shape getShape() {
        final double angle = Math.toRadians(this.getAngle());
//        Path2D.Double path = new Path2D.Double();
//        path.moveTo(this.getPos().getX(), this.getPos().getY());
//        path.lineTo(this.getPos().getX() + DEFAULT_LENGTH * Math.cos(angle),
//                this.getPos().getY() + DEFAULT_LENGTH * Math.sin(angle));
//        path.closePath();
//        return path;
        return new Line2D.Double(this.getPos().getX(), this.getPos().getY(),
                this.getPos().getX() + DEFAULT_LENGTH * Math.cos(angle),
                this.getPos().getY() + DEFAULT_LENGTH * Math.sin(angle));
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
        checkIntersecate(Optional.empty());
        final Rectangle2D shape = this.getShape().getBounds2D();
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
