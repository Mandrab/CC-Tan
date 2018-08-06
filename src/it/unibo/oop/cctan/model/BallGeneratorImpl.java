package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}.
 */
public class BallGeneratorImpl extends Thread implements BallGenerator {

    private final List<BallAgent> balls;
    private final BallRatio ratio;
    private final Model model;
    private boolean stop;

    /**
     * Create a new thread that generates balls.
     * @param model
     *          it's the model of the application
     */
    public BallGeneratorImpl(final Model model) {
        super();
        this.model = model;
        this.ratio = new BallRatio();
        this.balls = new ArrayList<>();
        this.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void launch() {
        this.ratio.start();
        this.start();
    }

    @Override
    public void terminate() {
        this.stop = true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (!stop) {
            this.createNewBall();
            try {
                Thread.sleep(this.ratio.getRatio());
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
    public synchronized List<BallAgent> getBallAgents() {
        return new ArrayList<>(this.balls);
    }

    private synchronized void createNewBall() {
        final BallAgent ball = (BallAgent) new BallAgent.BallBuilder()
                .angle(this.model.getShuttle().getAngle())
                .speed(this.ratio.getSpeed())
                .position(new Point2D(this.model.getShuttle().getTop().getX() /*-(BallAgent.WIDTH / 2) */,
                        this.model.getShuttle().getTop().getY() /*+ (BallAgent.HEIGHT / 2) */))
                .model(this.model)
                .build();
        this.balls.add(ball);
        new Thread(ball).start();
    }
}
