package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.geometry.Side;

/**
 * Represent a ball outgoing from the shuttle. They can also be considered like the shots of the shuttle.
 * Every time a ball hits a square, the square's hit points will be decreased by 1.
 */
public final class BallAgent extends MovableItem {

    public static final double WIDTH = 0.05;
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
    protected void updatePos() {
        super.updatePos();
        checkIntersecate();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void applyConstraints() {
        final Boundary bounds = this.getModel().getBounds();
        if (this.getPos().getX() + WIDTH < bounds.getX0() || this.getPos().getX() > bounds.getX1()
                || this.getPos().getY() < bounds.getY0() || this.getPos().getY() - HEIGHT > bounds.getY1()) {
            synchronized (this.getModel()) {
                this.getModel().removeBall(this);
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

    private void checkIntersecate() {
        synchronized (this.getModel().getSquareAgents()) {
            final List<SquareAgent> squares = new ArrayList<>(this.getModel().getSquareAgents());
            squares.remove(this.lastCollision.orElse(null));
            for (final SquareAgent squareAg : squares) {
                synchronized (squareAg) {
                    final Area ballArea = new Area(
                            new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY() - HEIGHT, WIDTH, HEIGHT));
                    ballArea.intersect(new Area(new Rectangle2D.Double(squareAg.getPos().getX(),
                            squareAg.getPos().getY() - SquareAgent.HEIGHT, SquareAgent.WIDTH,
                            SquareAgent.HEIGHT)));
                    if (!ballArea.isEmpty()) {
                        squareAg.hit();
                        this.lastCollision = Optional.of(squareAg);
                        this.updateAngle(squareAg);
                    }
                }
            }
        }
    }

    private void updateAngle(final SquareAgent rect) {
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
    public static class BallBuilder extends MovableItem.AbstractBuilder {

        /** 
         * {@inheritDoc}
         */
        @Override
        public MovableItem build() {
            super.validate();
            return new BallAgent(this);
        }
    }
}
