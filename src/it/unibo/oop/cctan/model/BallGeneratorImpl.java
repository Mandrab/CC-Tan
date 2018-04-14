package it.unibo.oop.cctan.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.oop.cctan.model.BallAgent;

public class BallGeneratorImpl extends Thread implements BallGenerator {

    private static final int GENERATION_RATIO = 200;
    private final List<BallAgent> balls;
    private final Model model;

    public BallGeneratorImpl(final Model model) {
        this.model = model;
        this.balls = new ArrayList<>();
    }

    @Override
    public void start() {
        super.run();
    }

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

    @Override
    public synchronized void removeBall(final BallAgent ball) {
        if (!this.balls.isEmpty() && ball != null) {
            ball.terminate();
            this.balls.remove(ball);
        }
    }

    @Override
    public List<BallAgent> getBallAgents() {
        return Collections.unmodifiableList(this.balls);
    }

    private synchronized void createNewBall() {
        final BallAgent ball = (BallAgent) new BallAgent.BallBuilder()            //TO DO!
                                                  .angle(0)
                                                  .position(null)
                                                  .model(this.model)
                                                  .build();
        this.balls.add(ball);
        ball.run();
    }
}
