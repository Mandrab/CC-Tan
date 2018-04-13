package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.geometry.Side;
import javafx.geometry.Point2D;

/**
 * Represent a ball outgoing from the shuttle. They can also be considered like the shots of the shuttle.
 * Every time a ball hits a square, the square's hit points will be decreased by 1.
 */
public class BallAgent extends MovableItem {

    private static final double WIDTH = 0.05;
    private static final double HEIGHT = 0.05;
    private static final double DEFAULT_SPEED = 0.001;

    private Optional<SquareAgent> lastCollision;

    /**
     * Create a new ball in the desired starting position (outgoing from the shuttle).
     * @param model
     *          the model of the application
     * @param angle
     *          the angle indicating the direction of the ball
     * @param startingPos
     *          the starting position
     */
    public BallAgent(final Model model, final Point2D startingPos, final double angle) {
        super(model, startingPos);
        this.setAngle(angle);
        this.setSpeed(DEFAULT_SPEED);
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

    private void checkIntersecate() {
        synchronized (this.getModel().getSquareAgents()) {
            final List<SquareAgent> squares = new ArrayList<>(this.getModel().getSquareAgents());
            squares.remove(this.lastCollision.orElse(null));
            for (final SquareAgent squareAg : squares) {
                synchronized (squareAg) {
                    final Area ballArea = new Area(
                            new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY() - HEIGHT, WIDTH, HEIGHT));
                    ballArea.intersect(new Area(new Rectangle2D.Double(squareAg.getPos().getX(),
                            squareAg.getPos().getY() - squareAg.getHeight(), squareAg.getWidth(),
                            squareAg.getHeight())));
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
        if (ballCenterX > square.getPos().getX() + square.getWidth()) {
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

}
