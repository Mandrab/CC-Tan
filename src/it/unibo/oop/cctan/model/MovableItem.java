package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.P2D;

/**
 * Represents any object capable of moving in the game area (i.e. ball, square)
 *
 */
public abstract class MovableItem extends FixedItem {

    private static final int REFRESH_RATIO = 20;
    private static final double DEFAULT_SPEED = 0.0005;

    private double angle;
    private double speed;
    private boolean stop;

    protected MovableItem(final Model model) {
        super(model);
        this.speed = DEFAULT_SPEED;
        this.stop = false;
    }

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

    private void updatePos() {
        this.setPos(new P2D(this.getPos().getX() + this.speed * Math.cos(this.angle),
                this.getPos().getY() + this.speed * Math.sin(this.angle)));
        applyConstraints();
    }

    protected synchronized void setAngle(final double angle) {
        this.angle = angle;
    }

    protected abstract void applyConstraints();
}
