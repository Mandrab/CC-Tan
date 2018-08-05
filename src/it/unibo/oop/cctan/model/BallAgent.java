package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Optional;
import it.unibo.oop.cctan.geometry.Side;

/**
 * Represent a ball outgoing from the shuttle. They can also be considered like the shots of the shuttle.
 * Every time a ball hits a square, the square's hit points will be decreased by 1.
 */
public final class BallAgent extends BulletImpl implements Bullet {

    /**
     * The width of the ball.
     */
    public static final double WIDTH = 0.05;

    /**
     * The height of the ball.
     */
    public static final double HEIGHT = 0.05;

    private static final double DEFAULT_SPEED = 0.001;
    private Optional<SquareAgent> lastCollision;


    private BallAgent(final BallBuilder builder) {
        super(builder);
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
    public Color getColor() {
        return Color.WHITE;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY(), WIDTH, HEIGHT);
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
    protected void updatePos() {
        super.updatePos();
        this.lastCollision = checkIntersecate(this.lastCollision);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void updateAngle(final SquareAgent rect) {
        final Side side = this.collisionSide(rect);
        this.setAngle(side == Side.ABOVE || side == Side.BELOW ? -this.getAngle() : Math.PI - this.getAngle());
    }

    private Side collisionSide(final SquareAgent square) {
        final double ballCenterX = this.getPos().getX() + WIDTH / 2;
        if (ballCenterX > square.getPos().getX() + SquareAgent.WIDTH) {
            return Side.RIGHT;
        }
        if (ballCenterX < square.getPos().getX()) {
            return Side.LEFT;
        }
        final double ballCenterY = this.getPos().getY() - HEIGHT / 2;
        if (ballCenterY > square.getPos().getY()) {
            return Side.ABOVE;
        }
        return Side.BELOW;
    }

    /**
     * A basic builder for BallAgent class.
     */
    public static class BallBuilder extends BulletImpl.BulletBuilder {

        /** 
         * {@inheritDoc}
         */
        @Override
        public BallAgent build() {
            super.validate();
            return new BallAgent(this);
        }
    }
}
