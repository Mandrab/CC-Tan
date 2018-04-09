package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.Point2D;

/**
 * Represents any object capable of moving in the game area (i.e. ball, square)
 *
 */
public abstract class MovableItem extends FixedItem implements Runnable {

    private static final int REFRESH_RATIO = 20;

    private double angle;
    private double speed;
    private boolean stop;

    protected MovableItem(final Model model) {
        super(model);
        this.stop = false;
    }

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

    public void terminate() {
        this.stop = true;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    protected void updatePos() {
        this.setPos(new Point2D(this.getPos().getX() + this.speed * Math.cos(this.angle),
                this.getPos().getY() + this.speed * Math.sin(this.angle)));
        applyConstraints();
    }

    protected synchronized double getAngle() {
        return this.angle;
    }

    protected synchronized void setAngle(final double angle) {
        this.angle = angle;
    }

    protected abstract void applyConstraints();

}
