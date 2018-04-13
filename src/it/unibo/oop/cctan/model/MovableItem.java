package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represents any object capable of moving in the game area (i.e. ball, square)
 *
 */
public abstract class MovableItem extends FixedItem implements Runnable {

    private static final int REFRESH_RATIO = 20;

    private double angle;
    private double speed;
    private boolean stop;

    /**
     * Put a new movable item in the current position.
     * @param model
     *          the model of the application
     * @param startingPos
     *          the starting position of the item
     */
    protected MovableItem(final Model model, final Point2D startingPos) {
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
     * Stop the item activity, terminating its thread execution.
     */
    public void terminate() {
        this.stop = true;
    }

    /**
     * Get the movement speed of the item express in units per refresh. In this
     * implementation a unit is a percentage of the game area (in coordinate between -1
     * and 1 for both directions). For example, 0.1 units means the item will be
     * moved of 1/10 the window dimension every refresh.
     * @return
     *          the current speed of the item
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Set the speed of the item, express in units per refresh.
     * @see #getSpeed()
     * @param speed
     *          the new speed
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Get the movement angle of the object. The angle is the portion of area between x-axis
     * and item trajectory.
     * @return
     *          the movement angle
     */
    protected synchronized double getAngle() {
        return this.angle;
    }

    /**
     * Set the movement angle of the item.
     * @see #getAngle()
     * @param angle
     *          the new movement angle
     */
    protected synchronized void setAngle(final double angle) {
        this.angle = angle;
    }

    /**
     * Update the current position according to movement speed and angle.
     */
    protected void updatePos() {
        this.setPos(new Point2D(this.getPos().getX() + this.speed * Math.cos(this.angle),
                this.getPos().getY() + this.speed * Math.sin(this.angle)));
        applyConstraints();
    }

    /**
     * After moving the item, this method will be used to check whether the current item should
     * be destroyed.
     */
    protected abstract void applyConstraints();

}
