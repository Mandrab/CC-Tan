package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.model.geometry.Side;
import javafx.geometry.Point2D;

/**
 * Represent a ball outgoing from the shuttle. They can also be considered like the shots of the shuttle.
 * Every time a ball hits a square, the square's hit points will be decreased by 1.
 */
public class BallAgent extends BulletImpl implements Bullet {

    /**
     * The width of the ball.
     */
    public static final double WIDTH = 0.05; // 1 / 40.0;

    /**
     * The height of the ball.
     */
    public static final double HEIGHT = 0.05;

    private static final double DEFAULT_SPEED = 0.001;
    private static final int DAMAGE = 1;

    /**
     * Create a new BallAgent using values contained in the specified builder.
     * @param builder
     *                  the builder to construct the ball agent
     */
    protected BallAgent(final BallBuilder builder) {
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
        return new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY(),
                this.getWidth(), this.getHeight());
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
        this.checkIntersecate(this.getLastCollision(), DAMAGE);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void updateAngle(final SquareAgent rect) {
        Side side = getImpactSide(rect);
        if (side != Side.CORNER) {
            this.setAngle(side == Side.ABOVE || side == Side.BELOW ? -this.getAngle() : 180 - this.getAngle());
        } else {
            final List<Double> distances = this
                    .getDistancesFromPoint(
                            new Point2D(this.getPos().getX() + this.getWidth() / 2,
                                    this.getPos().getY() + this.getHeight() / 2),
                            rect.getShape().getPathIterator(null));

            final List<Pair<Side, Double>> vertexDistances =  new ArrayList<>(Arrays.asList(
                    new ImmutablePair<>(Side.RIGHT_BOTTOM_CORNER, distances.get(0)),
                    new ImmutablePair<>(Side.RIGHT_TOP_CORNER, distances.get(1)),
                    new ImmutablePair<>(Side.LEFT_TOP_CORNER, distances.get(2)),
                    new ImmutablePair<>(Side.LEFT_BOTTOM_CORNER, distances.get(3))));
            side = Collections.min(vertexDistances, (v1, v2) -> Double.compare(v1.getRight(), v2.getRight())).getLeft();
            this.setAngle(side == Side.LEFT_TOP_CORNER || side == Side.RIGHT_BOTTOM_CORNER ? 90 - this.getAngle()
                    : -(90 + this.getAngle()));
        }
    }

    private Side getImpactSide(final SquareAgent rect) {
        switch (rect.getShape().getBounds2D().outcode(this.getPos().getX() + this.getWidth() / 2,
                this.getPos().getY() + this.getHeight() / 2)) {
        case Rectangle2D.OUT_LEFT:
            return Side.LEFT;
        case Rectangle2D.OUT_RIGHT:
            return Side.RIGHT;
        case Rectangle2D.OUT_BOTTOM:
            return Side.BELOW;
        case Rectangle2D.OUT_TOP:
            return Side.ABOVE;
        default:
            return Side.CORNER;
        }
    }

    private List<Double> getDistancesFromPoint(final Point2D point, final PathIterator pathIterator) {
        final int length = 6;
        final double[] coordinates = new double[length];
        final List<Double> points = new ArrayList<>();
        while (!pathIterator.isDone()) {
            pathIterator.next();
            if (pathIterator.currentSegment(coordinates) != PathIterator.SEG_CLOSE) {
                points.add(point.distance(new Point2D(coordinates[0], coordinates[1])));
            } else {
                break;
            }
        }
        return points;
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
