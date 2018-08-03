package it.unibo.oop.cctan.model;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * Represent the shuttle in the space, where balls get out from. When a square collide with it the game will ends.
 * The shuttle will rotate with mouse position.
 */
public class ShuttleImpl extends FixedItem implements Shuttle {

    private static final int INTERVALS = 1; //6; //number of intervals in which split out the two dimensions. Use 1 in test
    private static final int HEIGHT = 1; //height of the rectangle containing the triangle, in terms of interval-unit
    private static final int WIDTH = 1; //width of the rectangle, in terms of interval-unit

    private final double width;
    private final double height;
    private final Point2D startingPos;

    /**
     * Create a new shuttle in the center of the game area.
     * @param model
     *                  the model of MVC pattern
     */
    public ShuttleImpl(final Model model) {
        super(model, new Point2D(0, 0));
        final double unitX =  (this.getModel().getBounds().getX1() / INTERVALS);
        final double unitY = (this.getModel().getBounds().getY1() / INTERVALS);
        this.width = WIDTH * unitX;
        this.height = HEIGHT * unitY;
        this.startingPos = new Point2D(-this.width / 2, this.height / 2);
        this.setPos(this.startingPos);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Area getImpactArea() {
        final Path2D.Double path = new Path2D.Double();
        final List<Point2D> points = this.getShape();
        points.forEach(p -> {
            if (points.indexOf(p) == 0) {
                path.moveTo(p.getX(), p.getY());
            } else {
                path.lineTo(p.getX(), p.getY());
            }
        });
        path.closePath();
        return new Area(path);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Point2D getTop() {
        final double angle = Math.toRadians(90 - this.getAngle());
        return new Point2D(this.getPos().getX() + (this.width / 2) * Math.cos(angle),
                this.getPos().getY() - (this.width / 2) * Math.sin(angle));
    }

    /** 
     * {@inheritDoc}
     * In more, rotate the shuttle.
     */
    @Override
    public synchronized void setAngle(final double angle) {
        super.setAngle(angle);
        final double centerX = this.startingPos.getX() + this.width / 2;
        final double centerY = this.startingPos.getY() - this.height / 2;
        // note: use -(90 - angle) = (angle - 90) because we consider positive angles like rotation clockwise
        final double angleRad = Math.toRadians(angle - 90);
        final double[] pt = { this.startingPos.getX(), this.startingPos.getY() };
        AffineTransform.getRotateInstance(angleRad, centerX, centerY).transform(pt, 0, pt, 0, 1);
        this.setPos(new Point2D(pt[0], pt[1]));
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public List<Point2D> getShape() {
        final double angle = Math.toRadians(this.getAngle());
        final Point2D leftBase = new Point2D(this.getPos().getX() - this.height * Math.cos(angle),
                this.getPos().getY() - this.height * Math.sin(angle));
        final Point2D rightBase = new Point2D(leftBase.getX() + this.width * Math.sin(angle),
                leftBase.getY() - this.width * Math.cos(angle));
        return Arrays.asList(this.getTop(), leftBase, rightBase);
    }
}
