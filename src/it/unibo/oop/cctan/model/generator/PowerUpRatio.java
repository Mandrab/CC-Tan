package it.unibo.oop.cctan.model.generator;

public class PowerUpRatio extends HittableRatio {

    private static final int DEFAULT_RATIO = 45000;
    private static final int DECREASE_RATIO = 3000;
    private static final double DEFAULT_SPEED = 0;
    private static final int INCREASE_POINTS = 3;
    private static final int DEFAULT_POINTS = 5;
    private static final int MAX_RATIO = 20000;
    private static final int MAX_POINTS = 30;

    public PowerUpRatio() {
        super(DEFAULT_SPEED, DEFAULT_RATIO, DEFAULT_POINTS);
    }

    @Override
    public void operationRatio() {
        if (this.getRatio() >= MAX_RATIO + DECREASE_RATIO) {
            this.setRatio(this.getRatio() - DECREASE_RATIO);
        }
        if (this.getPoints() <= MAX_POINTS - INCREASE_POINTS) {
            this.setPoints(this.getPoints() + INCREASE_POINTS);
        }
    }
}
