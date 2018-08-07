package it.unibo.oop.cctan.model;

import java.util.Random;
import javafx.geometry.Point2D;

/**
 * {@inheritDoc}.
 */
public class SquareGeneratorImpl extends ItemGeneratorImpl {

    /**
     * Create a new thread that generates squares.
     * 
     * @param model
     *            it's the model of the application
     */
    public SquareGeneratorImpl(final Model model) {
        super(model, new SquareRatio());
    }

    @Override
    protected synchronized void createNewItem() {
        final Point2D randomPos = randomPosition();
        final SquareAgent square = (SquareAgent) new SquareAgent.SquareBuilder()
                .hitPoints(new Random().nextInt(SquareRatio.DEFAULT_POINTS) + ((SquareRatio) this.getRatio()).getPoints())
                .angle(Math.toDegrees(Math.atan2(-randomPos.getY(), -randomPos.getX())))
                .speed(this.getRatio().getSpeed())
                .position(randomPos)
                .model(this.getModel())
                .build();
        this.addItemToList(square);
        new Thread(square).start();
    }

    /*
     * 0 is up ---> x is random and y is +1.2,
     * 1 is down ---> x is random and y is -1.2,
     * 2 is left ---> x is -1.2 and y is random,
     * 3 is right ---> x is +1.2 and y is random.
     */
    private Point2D randomPosition() {
        final Random rnd = new Random();
        // 4 is the number of sides
        final int side = new Random().nextInt(4);
        if (side == 0 || side == 1) {
            return (new Point2D(
                    rnd.nextDouble() * Math.abs(this.getModel().getBounds().getX0() - this.getModel().getBounds().getX1())
                            - this.getModel().getBounds().getX1(),
                    side == 0 ? this.getModel().getBounds().getY1() : this.getModel().getBounds().getY0())); // 1.2 : -1.2
        } else {
            return (new Point2D(side == 2 ? this.getModel().getBounds().getX0() : this.getModel().getBounds().getX1(),
                    rnd.nextDouble() * Math.abs(this.getModel().getBounds().getY0() - this.getModel().getBounds().getY1())
                            - this.getModel().getBounds().getY1())); // -1.2 : 1.2
        }
    }

}
