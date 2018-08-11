package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
    private Optional<FixedItem> lastCollision;

    private BallAgent(final BallBuilder builder) {
        super(builder);
        this.lastCollision = Optional.empty();
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
        this.lastCollision = this.checkIntersecate(this.lastCollision);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void updateAngle(final SquareAgent rect) {
        final Side side;
        switch (rect.getShape().getBounds2D().outcode(this.getPos().getX() + WIDTH / 2,
                this.getPos().getY() + HEIGHT / 2)) {
        case Rectangle2D.OUT_LEFT:
            side = Side.LEFT;
            break;
        case Rectangle2D.OUT_RIGHT:
            side = Side.RIGHT;
            break;
        case Rectangle2D.OUT_BOTTOM:
            side = Side.BELOW;
            break;
        case Rectangle2D.OUT_TOP:
            side = Side.ABOVE;
            break;
        default:
            side = Side.CORNER;
        }
        this.setAngle(side == Side.CORNER ? this.getAngle() + 180
                : side == Side.ABOVE || side == Side.BELOW ? -this.getAngle() : 180 - this.getAngle());
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
