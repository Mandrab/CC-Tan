package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.geometry.RandomUtility;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.SquareAgent;
import javafx.geometry.Point2D;
import java.util.Random;

/**
 * It represents a generator of squares.
 */
public class SquareGenerator extends AbstractItemGenerator<SquareAgent> {

    /**
     * Create a new thread that generates {@link SquareAgent SquareAgent} over time.
     * 
     * @param model
     *            it's the model of the application
     */
    public SquareGenerator(final Model model) {
        super(model, new SquareRatio());
    }

    /**
     * This method is used to create new object of {@link SquareAgent SquareAgent} type.
     */
    @Override
    protected synchronized void createNewItem() {
        final Point2D randomPos = randomPosition();
        final SquareAgent square = (SquareAgent) new SquareAgent.SquareBuilder()
                .hitPoints(((SquareRatio) this.getRatio()).getHP())
                .angle(Math.toDegrees(Math.atan2(-randomPos.getY(), -randomPos.getX())))
                .speed(((SquareRatio) this.getRatio()).getSpeed())
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
        // 4 is the number of sides
        final int side = new Random().nextInt(4);
        if (side == 0 || side == 1) {
            return (new Point2D(
                    RandomUtility.doubleInRange(this.getModel().getBounds().getX0() - SquareAgent.WIDTH,
                            this.getModel().getBounds().getX1()),
                    side == 0 ? this.getModel().getBounds().getY1() : this.getModel().getBounds().getY0() - SquareAgent.HEIGHT));
        } else {
            return (new Point2D(side == 2 ? this.getModel().getBounds().getX0() - SquareAgent.WIDTH : this.getModel().getBounds().getX1(),
                    RandomUtility.doubleInRange(this.getModel().getBounds().getY0() - SquareAgent.HEIGHT,
                            this.getModel().getBounds().getY1()))); // -1.2 : 1.2
        }
    }

}
