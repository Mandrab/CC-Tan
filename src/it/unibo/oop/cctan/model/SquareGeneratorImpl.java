package it.unibo.oop.cctan.model;

import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;

public class SquareGeneratorImpl extends Thread implements SquareGenerator {

    private static final int GENERATION_RATIO = 2000;
    private final Model model;
    private final List<SquareAgent> squares;

    public SquareGeneratorImpl(final Model model) {
        this.squares = new ArrayList<>();
        this.model = model;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            this.createNewSquare();
            try {
                Thread.sleep(GENERATION_RATIO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void removeSquare(final SquareAgent square) {
        if (!this.squares.isEmpty() && square != null) {
            square.terminate();
            this.squares.remove(square);
        }
    }

    public synchronized List<SquareAgent> getSquareAgents() {
        return Collections.unmodifiableList(this.squares);
    }

    private synchronized void createNewSquare() {
        final Point2D randomPos = randomPosition();
        final SquareAgent square = (SquareAgent) new SquareAgent.SquareBuilder()
                                                                .hitPoints(new Random().nextInt(41) + 40)
                                                                .angle(Math.atan2(-randomPos.getY(), -randomPos.getX())) //Da perfezionare!
                                                                .position(randomPosition())
                                                                .model(this.model)
                                                                .build();
        this.squares.add(square);
        square.run();
    }

    private Point2D randomPosition() {
        final Random rnd = new Random();
        final int side = new Random().nextInt(4);
        if (side == 0 || side == 1) {
            return (new Point2D(
                    rnd.nextDouble() * Math.abs(model.getBounds().getX0() - model.getBounds().getX1())
                            - model.getBounds().getX1(), side == 0 ? model.getBounds().getY1() : model.getBounds().getY0())); //1.2 : -1.2
        } else {
            return (new Point2D(side == 2 ? model.getBounds().getX0() : model.getBounds().getX1(),
                    rnd.nextDouble() * Math.abs(model.getBounds().getY0() - model.getBounds().getY1())
                            - model.getBounds().getY1())); // -1.2 : 1.2
        }
    }

}
