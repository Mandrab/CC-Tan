package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Represent a square block in the map area. Every square has got different hit points, that are the number
 * of hits the square must receive to be destroyed.
 */
public final class SquareAgent extends MovableItem  {

    private static final double WIDTH = 0.18;
    private static final double HEIGHT = 0.18;
    private static final double DEFAULT_SPEED = 0.0005;

    private int hitPoints;

    private SquareAgent(final SquareBuilder builder) {
        super(builder);
        this.hitPoints = builder.hp;
    }

    /**
     * Specify the current square has been hit and decrease its hit points by 1.
     * If the new hit points value is 0 the square will be destroyed.
     */
    public synchronized void hit() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            synchronized (this.getModel()) {
                this.getModel().removeSquare(this);
            }
        }
    }

    /**
     * Get the remaining hit points of the square.
     * @return
     *          the remaining hit points of the square
     */
    public synchronized int getHP() {
        return this.hitPoints;
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
     */
    @Override
    protected void applyConstraints() {
        synchronized (this.getModel()) {
            final Area sqArea = new Area(new Rectangle2D.Double(this.getPos().getX(),
                    this.getPos().getY() - HEIGHT, WIDTH, HEIGHT));
            sqArea.intersect(this.getModel().getShuttle().getImpactArea());
            if (!sqArea.isEmpty()) {
                //JOptionPane.showMessageDialog(PANEL, "GAME OVER!");
                this.getModel().removeSquare(this);
                //System.exit(0);
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
    public static class SquareBuilder extends MovableItem.AbstractBuilder {

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
        public MovableItem build() {
            super.validate();
            if (this.hp <= 0) {
                throw new IllegalArgumentException();
            }
            return new SquareAgent(this);
        }
    }
}
