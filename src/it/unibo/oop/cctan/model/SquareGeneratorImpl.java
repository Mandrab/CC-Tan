package it.unibo.oop.cctan.model;

import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * {@inheritDoc}.
 */
public class SquareGeneratorImpl extends Thread implements SquareGenerator {

    private final Model model;
    private final SquareRatio ratio;
    private final List<SquareAgent> squares;
    private boolean stop = false;

    /**
     * Create a new thread that generates squares.
     * 
     * @param model
     *            it's the model of the application
     */
    public SquareGeneratorImpl(final Model model) {
        super();
        this.model = model;
        this.ratio = new SquareRatio();
        this.squares = new ArrayList<>();
        this.stop = false;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void launch() {
        this.ratio.start();
        super.start();
    }

    @Override
    public void terminate() {
        this.stop = true;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void run() {
        while (!stop) {
            this.createNewSquare();
            try {
                Thread.sleep(this.ratio.getRatio());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public synchronized void removeSquare(final SquareAgent square) {
        if (!this.squares.isEmpty() && square != null) {
            square.terminate();
            this.squares.remove(square);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public synchronized List<SquareAgent> getSquareAgents() {
        return new ArrayList<>(this.squares);
    }

    private synchronized void createNewSquare() {
        final Point2D randomPos = randomPosition();
        final SquareAgent square = (SquareAgent) new SquareAgent.SquareBuilder()
                .hitPoints(new Random().nextInt(SquareRatio.DEFAULT_POINTS) + this.ratio.getPoints())
                .angle(Math.toDegrees(Math.atan2(-randomPos.getY(), -randomPos.getX()))).speed(this.ratio.getSpeed())
                .position(randomPos).model(this.model).build();
        this.squares.add(square);
        new Thread(square).start();
    }

    /*
     * 0 è sopra e in questo caso la x è random e la y è +1.2, 1 è sotto e in questo
     * caso la x è random e la y è -1.2, 2 è sinistra e in questo caso la x è -1.2 e
     * la y è random, 3 è destra e in questo caso la x è +1.2 e la y è random.
     */
    private Point2D randomPosition() {
        final Random rnd = new Random();
        // 4 is the number of sides
        final int side = new Random().nextInt(4);
        if (side == 0 || side == 1) {
            return (new Point2D(
                    rnd.nextDouble() * Math.abs(model.getBounds().getX0() - model.getBounds().getX1())
                            - model.getBounds().getX1(),
                    side == 0 ? model.getBounds().getY1() : model.getBounds().getY0())); // 1.2 : -1.2
        } else {
            return (new Point2D(side == 2 ? model.getBounds().getX0() : model.getBounds().getX1(),
                    rnd.nextDouble() * Math.abs(model.getBounds().getY0() - model.getBounds().getY1())
                            - model.getBounds().getY1())); // -1.2 : 1.2
        }
    }
}
