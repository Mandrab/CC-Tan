package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

/**
 * The concrete implementation of the Laser power up, understood as the laser bullet.
 */
public class LaserAgent extends BulletImpl implements Bullet {

    private static final double DEFAULT_SPEED = 0.005;
    private static final double DEFAULT_LENGTH = 1 / 11.0; //rispetto a metà diagonale principale
    private static final int DAMAGE = 1;
    private double width;
    private double height;


    /**
     * Create a new laser bullet using values contained in the specified builder.
     * @param builder
     *                  the builder to construct the laser bullet
     */
    protected LaserAgent(final LaserBuilder builder) {
        super(builder);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected boolean intersectsWith(final FixedItem item) {
        return this.getShape().intersects(item.getShape().getBounds2D());
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        final double angle = Math.toRadians(this.getAngle());
        return new Line2D.Double(this.getPos().getX(), this.getPos().getY(),
                this.getPos().getX() + DEFAULT_LENGTH * Math.hypot(this.getModel().getBounds().width() / 2,
                        this.getModel().getBounds().height() / 2) * Math.cos(angle),
                this.getPos().getY() + DEFAULT_LENGTH * Math.hypot(this.getModel().getBounds().width() / 2,
                        this.getModel().getBounds().height() / 2) * Math.sin(angle));
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getWidth() {
        return this.width;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getHeight() {
        return this.height;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void updatePos() {
        super.updatePos();
        this.checkIntersecate(Optional.empty(), DAMAGE);
        final Rectangle2D shape = this.getShape().getBounds2D();
        this.width = shape.getWidth();
        this.height = shape.getHeight();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected double getDefaultSpeed() {
        return DEFAULT_SPEED;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void updateAngle(final SquareAgent rect) {
        //nothing to do here
    }
    
    /**
     * A basic builder for LaserAgent class.
     */
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
