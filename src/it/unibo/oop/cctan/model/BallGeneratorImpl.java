package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}.
 */
public class BallGeneratorImpl extends Thread implements BallGenerator {

    private static final double DEFAULT_SPEED = 0.001;
    private static final int GENERATION_RATIO = 200;
    private final List<BallAgent> balls;
    private final Model model;

    /**
     * Put a new movable item respecting the value specified inside the builder object.
     * @param model
     *          it's the model of the application
     */
    public BallGeneratorImpl(final Model model) {
        super();
        this.model = model;
        this.balls = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        super.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (true) {
            this.createNewBall();
            try {
                Thread.sleep(GENERATION_RATIO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void removeBall(final BallAgent ball) {
        if (!this.balls.isEmpty() && ball != null) {
            ball.terminate();
            this.balls.remove(ball);
        }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public List<BallAgent> getBallAgents() {
        return Collections.unmodifiableList(this.balls);
    }

    private synchronized void createNewBall() {
        final BallAgent ball = (BallAgent) new BallAgent.BallBuilder()
                .angle(this.model.getShuttle().getAngle())
                .speed(DEFAULT_SPEED)
                .position(new Point2D(this.model.getShuttle().getTop().getX() - (BallAgent.WIDTH / 2),
                        this.model.getShuttle().getTop().getY() + (BallAgent.HEIGHT / 2)))
                .model(this.model)
                .build();
        this.balls.add(ball);
        ball.run();
    }

}
