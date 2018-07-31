package it.unibo.oop.cctan.model;

/**
 * The task of this class is to increase the difficulty of play by acting on the squares that are 
 * present in the application. In particular, after every minute that passes, the frequency with 
 * which the squares are generated is increased, the speed with which the squares move is increased 
 * and the initial life of each square is also increased.
 */
public class SquareRatio extends TimerRatio {

    /**
     * Indicates the life of squares when the application is started.
     */
    public static final int DEFAULT_POINTS = 41;
    private static final int DEFAULT_RATIO = 2000;
    private static final double DEFAULT_SPEED = 0.001;

    private static final int MAX_RATIO = 50;
    private static final int DECREASE_RATIO = 2;
    private static final double MAX_SPEED = 0.1;
    private static final int INCREASE_POINTS = 2;
    private static final double INCREASE_SPEED = 0.02;

    private int ratio;
    private int points;
    private double speed;

    /**
     * Set default values for the points, ratio and speed fields.
     */
    public SquareRatio() {
        super();
        this.points = DEFAULT_POINTS;
        this.ratio = DEFAULT_RATIO;
        this.speed = DEFAULT_SPEED;
    }

    /**
     * Increases the speed at which squares are generated and also increases the speed of movement 
     * of the squares. Furthermore, the life of each square is also increased.
     */
    @Override
    public void operationRatio() {
        if (this.ratio > MAX_RATIO) {
            this.ratio = this.ratio - DECREASE_RATIO;
        }
        if (this.speed < MAX_SPEED) {
            this.speed = this.speed + INCREASE_SPEED;
        }
        this.points = this.points + INCREASE_POINTS;
    }

    /**
     * @return
     *          ratio field
     */
    public int getRatio() {
        return this.ratio;
    }

    /**
     * @return
     *          points field
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @return
     *          speed field
     */
    public double getSpeed() {
        return this.speed;
    }

}