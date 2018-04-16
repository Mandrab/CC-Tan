package it.unibo.oop.cctan.model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import java.awt.MouseInfo;
import java.awt.Point;

public class BallGeneratorImpl extends Thread implements BallGenerator {

    private static final int GENERATION_RATIO = 200;
    private List<BallAgent> balls;
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
        Point mouse;
        while (true) {
            mouse = MouseInfo.getPointerInfo().getLocation();
            /*SwingUtilities.convertPointFromScreen(mouse, VisualiserFrame.getPanel());
            this.createNewBall(Math.atan2(getRelativeY(mouse.getY(), BallAgent.HEIGHT)-BallAgent.SPAWN.getY(),
                            getRelativeX(mouse.getX(), BallAgent.WIDTH)-BallAgent.SPAWN.getX()));*/
            this.createNewBall(0.1);
            try {
                Thread.sleep(GENERATION_RATIO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void createNewBall(final double angle) {
        BallAgent ball = new BallAgent(this.model, angle);
        this.balls.add(ball);
        ball.run();
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
}
