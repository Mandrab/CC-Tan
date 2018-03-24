package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import it.unibo.oop.cctan.geometry.P2D;

public class SquareAgent extends MovableItem {

    private static final double WIDTH = 0.18;
    private static final double HEIGHT = 0.18;
    private int hitPoints;

    public SquareAgent(Model model, final int hitPoints) {
        super(model);
        this.hitPoints = hitPoints;
        final Random rnd = new Random();
        final int lato = new Random().nextInt(4);
        if (lato == 0 || lato == 1) {
            this.setPos(new P2D(
                    rnd.nextDouble() * Math.abs(model.getBounds().getX0() - model.getBounds().getX1())
                            - model.getBounds().getX1(), lato == 0 ? model.getBounds().getY1() : model.getBounds().getY0())); //1.2 : -1.2
        } else {
            this.setPos(new P2D(lato == 2 ? model.getBounds().getX0() : model.getBounds().getX1(),
                    rnd.nextDouble() * Math.abs(model.getBounds().getY0() - model.getBounds().getY1())
                            - model.getBounds().getY1())); // -1.2 : 1.2
        }
        this.setAngle(Math.atan2(-this.getPos().getY(), -this.getPos().getX())); //Da perfezionare!
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
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
