package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represents any object capable of moving in the game area (i.e. ball, square)
 *
 */
public abstract class MovableItemImpl extends FixedItemImpl implements MovableItem, Runnable {

    private static final int REFRESH_RATIO = 20;

    private double speed;
    private boolean stop;

    /**
     * Put a new movable item respecting the value specified inside the builder object.
     * If speed is not set, will be used the default speed for the current item.
     * @param builder
     *          the builder containing the desired parameters
     */
    protected MovableItemImpl(final AbstractBuilderMI<?> builder) {
        super(builder);
        this.speed = builder.speedValue == 0 ? this.getDefaultSpeed() : builder.speedValue;
        this.stop = false;
    }

    /**
     * Put a new movable item in the current position.
     * @param model
     *          the model of the application
     * @param startingPos
     *          the starting position of the item
     */
    protected MovableItemImpl(final Model model, final Point2D startingPos) {
        super(model, startingPos);
        this.stop = false;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            while (!stop) {
                updatePos();
                Thread.sleep(REFRESH_RATIO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** 
     * {@inheritDoc}
     */
    public void terminate() {
        this.stop = true;
    }

    /** 
     * {@inheritDoc}
     */
    public double getSpeed() {
        return this.speed;
    }

    /** 
     * {@inheritDoc}
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Update the current position according to movement speed and angle.
     */
    protected void updatePos() {
        double angle = Math.toRadians(this.getAngle());
        this.setPos(new Point2D(this.getPos().getX() + this.speed * Math.cos(angle),
                this.getPos().getY() + this.speed * Math.sin(angle)));
        applyConstraints();
    }

    /**
     * After moving the item, this method will be used to check whether the current item should
     * be destroyed.
     */
    protected abstract void applyConstraints();

    /**
     * Get the default speed of the current item.
     * @return
     *          the default speed of the item
     */
    protected abstract double getDefaultSpeed();

    /**
     * A basic abstract builder for (classes who extends from) MovableItem abstract class.
     * @param <T>
     *                  the current builder type
     */
    @SuppressWarnings("unchecked")
    public abstract static class AbstractBuilderMI<T extends AbstractBuilderMI<T>>
            extends FixedItemImpl.AbstractBuilderFI<AbstractBuilderMI<T>> {

        private double speedValue;

        /**
         * Set the speed of the item in the direction of movement.
         * @param speed
         *              the speed of the item
         * @return
         *              the current abstract builder for movable items
         */
        public T speed(final double speed) {
            this.speedValue = speed;
            return (T) this;
        }

        /**
         * Builds the desired MovableItem object with the specified parameters.
         * @return
         *              the MovableItem object as wanted
         */
        public abstract MovableItemImpl build();
    }
}