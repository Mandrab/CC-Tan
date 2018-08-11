package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Represent a square block in the map area. Every square has got different hit points, that are the number
 * of hits the square must receive to be destroyed.
 */
public final class SquareAgent extends MovableItemImpl implements Hittable {

    /**
     * The width of the ball.
     */
    public static final double WIDTH = 0.18; //0.18

    /**
     * The height of the ball.
     */
    public static final double HEIGHT = 0.18;

    private static final double DEFAULT_SPEED = 0.0005;
    private static final int BLU_LIMIT = 10;
    private static final int GREEN_LIMIT = 25;
    private static final int YELLOW_LIMIT = 40;
    private static final int ORANGE_LIMIT = 50;
    private static final int RED_LIMIT = 70;
    private static final int MAGENTA_LIMIT = 90;
    private final Hittable hittableItem;

    private SquareAgent(final SquareBuilder builder) {
        super(builder);
        this.hittableItem = new HittableImpl(builder.hp) {

            @Override
            protected void destroyed() {
                getModel().getScore().increment();
                SquareAgent.this.terminate();
                getModel().removeSquare(SquareAgent.this);
            }
        };
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getWidth() {
        return WIDTH;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getHeight() {
        return HEIGHT;
    }

    /** 
     * {@inheritDoc}
     * In this case, after removing the current square, will not be
     * executed other operation.
     */
    public synchronized void hit() {
        this.hittableItem.hit();
    }

    /** 
     * {@inheritDoc}
     */
    public synchronized int getHP() {
        return this.hittableItem.getHP();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        return new Rectangle2D.Double(this.getPos().getX(), this.getPos().getY(), WIDTH, HEIGHT);
    }

    @Override
    public Color getColor() {
        final int hp = this.hittableItem.getHP();
        if (hp >= 0 && hp < BLU_LIMIT) {
            return Color.BLUE;
        } else if (hp < GREEN_LIMIT) {
            return Color.GREEN;
        } else if (hp < YELLOW_LIMIT) {
            return Color.YELLOW;
        } else if (hp < ORANGE_LIMIT) {
            return Color.ORANGE;
        } else if (hp < RED_LIMIT) {
            return Color.RED;
        } else if (hp < MAGENTA_LIMIT) {
            return Color.MAGENTA;
        }
        final int h = 287, s = 86, b = 70; // HSB components
        return Color.getHSBColor(h, s, b); // purple
    }
    
    /** 
     * {@inheritDoc}
     */
    @Override
    protected void applyConstraints() {
        synchronized (this.getModel()) {
            final Area sqArea = new Area(this.getShape());
            sqArea.intersect(this.getModel().getShuttle().getImpactArea());
            if (!sqArea.isEmpty()) {
                this.getModel().removeSquare(this);
                this.terminate();
//                this.getModel().getSquareAgents().forEach(sa -> this.getModel().removeSquare(sa));
//                this.getModel().getBallAgents().forEach(ba -> this.getModel().removeBall(ba)); 
                System.out.println("Game-over!");
            }
        }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected double getDefaultSpeed() {
        return DEFAULT_SPEED;
    }
    
    /**
     * A basic builder for SquareAgent class.
     */
    public static class SquareBuilder extends MovableItemImpl.AbstractBuilderMI<SquareBuilder> {

        private int hp;

        /**
         * Set the starting hit points of the square.
         * @param hitPoints
         *              the starting value of hit points
         * @return
         *              the current square builder
         */
        public SquareBuilder hitPoints(final int hitPoints) {
            this.hp = hitPoints;
            return this;
        }

        /** 
         * {@inheritDoc}
         */
        @Override
        public SquareAgent build() {
            super.validate();
            if (this.hp <= 0) {
                throw new IllegalArgumentException();
            }
            return new SquareAgent(this);
        }
    }
}
