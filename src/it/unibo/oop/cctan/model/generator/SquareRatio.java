package it.unibo.oop.cctan.model.generator;

/**
 * The task of this class is to increase the difficulty of play by acting on the squares that are 
 * present in the application. In particular, after every minute that passes, the frequency with 
 * which the squares are generated is increased, the speed with which the squares move is increased 
 * and the initial life of each square is also increased.
 */
public class SquareRatio extends HittableRatio {

    /**
     * Indicates the life of squares when the application is started.
     */
    public static final int DEFAULT_POINTS = 5;
    private static final int DEFAULT_RATIO = 3000;
    private static final double DEFAULT_SPEED = 0.0005;

    private static final int MAX_RATIO = 800;
    private static final int DECREASE_RATIO = 60;
    private static final double MAX_SPEED = 0.0007;
    private static final int INCREASE_POINTS = 6;
    private static final double INCREASE_SPEED = 0.00008;

    /**
     * Set default values for the points, ratio and speed fields.
     */
    public SquareRatio() {
        super(DEFAULT_SPEED, DEFAULT_RATIO, DEFAULT_POINTS);
    }

    /**
     * Increases the speed at which squares are generated and also increases the speed of movement 
     * of the squares. Furthermore, the life of each square is also increased.
     */
    @Override
    public void operationRatio() {
        if (this.getRatio() >= MAX_RATIO + DECREASE_RATIO) {
            this.setRatio(this.getRatio() - DECREASE_RATIO);
        }
        if (this.getSpeed() <= MAX_SPEED - INCREASE_SPEED) {
            this.setSpeed(this.getSpeed() + INCREASE_SPEED);
        }
        this.setPoints(this.getPoints() + INCREASE_POINTS);
    }

}
