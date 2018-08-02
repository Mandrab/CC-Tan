package it.unibo.oop.cctan.model;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;


public class ShuttleImpl extends FixedItem implements Shuttle {

    private static final int INTERVALS = 1; //6; //number of intervals in which split out the two dimensions
    private static final int HEIGHT = 1; //height of the rectangle containing the triangle, in terms of interval-unit
    private static final int WIDTH = 1; //width of the rectangle, in terms of interval-unit

    private final double width;
    private final double height;
    private final Point2D startingPos;

    public ShuttleImpl(Model model) {
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
        //controllare se rendere tutto lo shuttle (triangolo) come area di impatto o solo una parte, come ora
        final AffineTransform tx = new AffineTransform();
        final double factor = 0.5;
        tx.translate(this.width * (1 - factor) / 2, this.height * (1 - factor) / 2);
        tx.scale(factor, factor);
        tx.rotate(90 - this.getAngle());
        final Rectangle2D shuttleBoxing = new Rectangle2D.Double(this.startingPos.getX(), this.startingPos.getY(),
                this.width, this.height);
        final Area internalArea = new Area(tx.createTransformedShape(shuttleBoxing));
        final Path2D.Double path = new Path2D.Double();
        final List<Point2D> points = this.getShape();
        path.moveTo(points.get(0).getX(), points.get(0).getY());
        points.stream().filter(p -> points.indexOf(p) > 1).forEach(pt -> path.moveTo(pt.getX(), pt.getY()));
        path.closePath();
        internalArea.intersect(new Area(path));

        return internalArea;
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
        final double angleRad = Math.toRadians(angle - 90);

        final double[] pt = {this.startingPos.getX(), this.startingPos.getY()};
        AffineTransform.getRotateInstance(angleRad, centerX, centerY)
          .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
        this.setPos(new Point2D(pt[0], pt[1]));

//        this.setPos(new Point2D(
//                centerX + (this.startingPos.getX() - centerX) * Math.cos(angleRad)
//                        + (this.startingPos.getY() - centerY) * Math.sin(angleRad),
//                centerY - (this.startingPos.getX() - centerX) * Math.sin(angleRad)
//                        + (this.startingPos.getY() - centerY) * Math.cos(angleRad)));
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
