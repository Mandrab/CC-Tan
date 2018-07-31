package it.unibo.oop.cctan.model;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;


public class ShuttleImpl extends FixedItem implements Shuttle {

    private static final int INTERVALS = 13; //number of intervals in which split out the two dimensions
    private static final int HEIGHT = 1; //total height of the triangle (shuttle), in terms of interval-unit
    private static final int WIDTH = 1; //total width of the triangle, in terms of interval-unit
    private Rectangle2D.Double shuttleShpae;

    public ShuttleImpl(Model model) {
        super(model, new Point2D(-WIDTH / 2, HEIGHT / 2));
        this.shuttleShpae = new Rectangle2D.Double(this.getPos().getX(), this.getPos().getY(), WIDTH, HEIGHT);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Area getImpactArea() {
        return new Area(new Rectangle2D.Double(this.shuttleShpae.getX(),
                this.shuttleShpae.getY() / 2 + WIDTH / 2, WIDTH, WIDTH));
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Point2D getTop() {
      //da perfezionare con formule trigonometriche
        return new Point2D(this.getPos().getX() + Math.cos(WIDTH / 2), this.getPos().getY() + Math.sin(HEIGHT / 2));
    }

    /** 
     * {@inheritDoc}
     * In more, rotate the shuttle.
     */
    @Override
    protected synchronized void setAngle(final double angle) {
        super.setAngle(angle);
        this.shuttleShpae = (Rectangle2D.Double) AffineTransform.getRotateInstance(Math.toRadians(90 - angle),
                this.shuttleShpae.getCenterX(), this.shuttleShpae.getCenterY())
                .createTransformedShape(this.shuttleShpae);
//        top = new Point2D((HEIGHT - STARTING_HEIGHT_BELOW_X) * Math.cos(angle),
//                (HEIGHT - STARTING_HEIGHT_BELOW_X) * Math.sin(angle));
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public List<Point2D> getShape() {
        //da implementare correttamente per ogni angolo possibile (sfruttare la funzione getTop()...)
        return Arrays.asList(new Point2D(this.shuttleShpae.getX() + WIDTH / 2, this.shuttleShpae.getY()),
                new Point2D(this.shuttleShpae.getX(), this.shuttleShpae.getY() - HEIGHT),
                new Point2D(this.shuttleShpae.getX() + WIDTH, this.shuttleShpae.getY() - HEIGHT));
    }
}
