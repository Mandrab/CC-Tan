package it.unibo.oop.cctan.model;

import java.util.Optional;

import it.unibo.oop.cctan.geometry.Side;

public class BallAgent extends MovableItem {

    public static final double WIDTH = 0.05;
    public static final double HEIGHT = 0.05;
    private static final double DEFAULT_SPEED = 0.001;
    private static final int REFRESH_RATIO = 15;

    private Optional<SquareAgent> lastCollision;

    public BallAgent(Model model, final double angle) {
        super(model);
        this.setAngle(angle);
        this.setSpeed(DEFAULT_SPEED);
    }

    @Override
    public void run() {
        try {
            while (true/*!stop*/) {
                super.updatePos();
                //checkIntersecate();
                Thread.sleep(REFRESH_RATIO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void applyConstraints() {
        // TODO Auto-generated method stub

    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    private void updateAngle(final SquareAgent rect) {
        Side side = this.collisionSide(rect);
        this.setAngle(side == Side.ABOVE || side == Side.BELOW ? -this.getAngle() : Math.PI - this.getAngle());
    }

    private Side collisionSide(final SquareAgent square) {
        double ballCenterX = this.getPos().getX() + WIDTH / 2;
        double ballCenterY = this.getPos().getY() - HEIGHT / 2;
        if (ballCenterX > square.getPos().getX() + square.getWidth()) {
            return Side.RIGHT;
        }
        if (ballCenterX < square.getPos().getX()) {
            return Side.LEFT;
        }
        if (ballCenterY > square.getPos().getY()) {
            return Side.ABOVE;
        }
        return Side.BELOW;
    }

}
