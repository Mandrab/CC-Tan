package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import javafx.geometry.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SquareAgent extends MovableItem  {

    private static final double WIDTH = 0.18;
    private static final double HEIGHT = 0.18;
    private static final double DEFAULT_SPEED = 0.0005;

    private int hitPoints;

    public SquareAgent(Model model, final int hitPoints) {
        super(model);
        this.hitPoints = hitPoints;
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
        this.setAngle(Math.atan2(-this.getPos().getY(), -this.getPos().getX())); //Da perfezionare!
        this.setSpeed(DEFAULT_SPEED);
    }

    public synchronized void hit() {
        if (--this.hitPoints <= 0) {
            synchronized (this.getModel()) {
                //this.getModel().removeSquare(this);
            }
        }
    }

    public synchronized int getHP() {
        return this.hitPoints;
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    @Override
    protected void applyConstraints() {
        synchronized (this.getModel()) {
            final Area sqArea = new Area(new Rectangle2D.Double(this.getPos().getX(),
                    this.getPos().getY() - HEIGHT, WIDTH, HEIGHT));
            sqArea.intersect(this.getModel().getShuttle().getImpactArea());
            if (!sqArea.isEmpty()) {
                //JOptionPane.showMessageDialog(PANEL, "GAME OVER!");
                System.exit(0);
                //this.getModel().removeSquare(this);
            }
        }
    }
}
