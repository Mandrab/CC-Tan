package it.unibo.oop.cctan.model.generator;

/**
 * The task of this class is to increase the frequency with which the power-ups are generated 
 * and also increase the points needed to take the power-up.
 */
public class PowerUpRatio extends AbstractHittableRatio {

    private static final int DEFAULT_RATIO = 60000;
    private static final int DECREASE_RATIO = 3000;
    private static final double DEFAULT_SPEED = 0;
    private static final int INCREASE_POINTS = 3;
    private static final int DEFAULT_POINTS = 5;
    private static final int MAX_RATIO = 30000;
    private static final int MAX_POINTS = 30;

    /**
     * Set default values for the ratio, speed and points power-up fields.
     */
    public PowerUpRatio() {
        super(DEFAULT_SPEED, DEFAULT_RATIO, DEFAULT_POINTS);
    }

    /**
     * Increases the frequency with which power-ups are generated and increases 
     * the points necessary to get the powerUp.
     */
    @Override
    public void operation() {
        if (this.getRatio() >= MAX_RATIO + DECREASE_RATIO) {
            this.setRatio(this.getRatio() - DECREASE_RATIO);
        }
        if (this.getPoints() <= MAX_POINTS - INCREASE_POINTS) {
            this.setPoints(this.getPoints() + INCREASE_POINTS);
        }
    }
}
