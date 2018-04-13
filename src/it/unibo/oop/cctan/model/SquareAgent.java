package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import javafx.geometry.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Represent a square block in the map area. Every square has got different hit points, that are the number
 * of hits the square must receive to be destroyed.
 */
public class SquareAgent extends MovableItem  {

    private static final double WIDTH = 0.18;
    private static final double HEIGHT = 0.18;
    private static final double DEFAULT_SPEED = 0.0005;

    private int hitPoints;

    //PATTERN BUILDER al posto di 2/3 set consecutivi?
    /**
     * Create a new square with the specified hit points in the desired starting position. 
     * @param model
     *          the model of the application
     * @param startingPos
     *          the starting position of the square's left-upper corner
     * @param hitPoints
     *          the starting hit points of the square
     */
    public SquareAgent(final Model model, final Point2D startingPos, final int hitPoints) {
        super(model, startingPos);
        this.hitPoints = hitPoints;

// ---------- !! DA METTERE IN SQUARE GENERATOR (e controllare le coordinate...) !! -----------
        final Random rnd = new Random();
        final int side = new Random().nextInt(4);
        if (side == 0 || side == 1) {
            this.setPos(new Point2D(
                    rnd.nextDouble() * Math.abs(model.getBounds().getX0() - model.getBounds().getX1())
                            - model.getBounds().getX1(), side == 0 ? model.getBounds().getY1() : model.getBounds().getY0())); //1.2 : -1.2
        } else {
            this.setPos(new Point2D(side == 2 ? model.getBounds().getX0() : model.getBounds().getX1(),
                    rnd.nextDouble() * Math.abs(model.getBounds().getY0() - model.getBounds().getY1())
                            - model.getBounds().getY1())); // -1.2 : 1.2
        }
// -------------------------------------------------------------------------------------------

        this.setAngle(Math.atan2(-this.getPos().getY(), -this.getPos().getX())); //Da perfezionare!
        this.setSpeed(DEFAULT_SPEED);
    }

    /**
     * Specify the current square has been hit and decrease its hit points by 1.
     * If the new hit points value is 0 the square will be destroyed.
     */
    public synchronized void hit() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            synchronized (this.getModel()) {
                this.getModel().removeSquare(this);
            }
        }
    }

    /**
     * Get the remaining hit points of the square.
     * @return
     *          the remaining hit points of the square
     */
    public synchronized int getHP() {
        return this.hitPoints;
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
    protected void applyConstraints() {
        synchronized (this.getModel()) {
            final Area sqArea = new Area(new Rectangle2D.Double(this.getPos().getX(),
                    this.getPos().getY() - HEIGHT, WIDTH, HEIGHT));
            sqArea.intersect(this.getModel().getShuttle().getImpactArea());
            if (!sqArea.isEmpty()) {
                //JOptionPane.showMessageDialog(PANEL, "GAME OVER!");
                this.getModel().removeSquare(this);
                //System.exit(0);
            }
        }
    }
}
