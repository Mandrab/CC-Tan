package it.unibo.oop.cctan.model;

/**
 * The task of this class is to increase the difficulty of play by acting on the balls that are 
 * present in the application. This class allows you to increase the number of balls, within the 
 * application, in proportion to the increase in playing time. In addition, it also allows you to 
 * increase the speed at which balls move from one point to another.
 */
public class BallRatio extends TimerRatio {

    /**
     * This value is expressed in milliseconds. Indicates the initial sleep time 
     * that is used in the BallGeneratorImpl thread at the start of the game
     */
    private static final int DEFAULT_RATIO = 200;
    /**
     * This value is expressed in milliseconds. Indicates the initial speed 
     * at which the balls are moved at the start of the game
     */
    private static final double DEFAULT_SPEED = 0.001;

    /**
     * Indicates the maximum frequency with which the balls are generated.
     */
    private static final int MAX_RATIO = 50;
    /**
     * Fixed increase in the frequency with which the balls are generated.
     */
    private static final int DECREASE_RATIO = 2;
    /**
     * Indicates the maximum speed of movement that can reach the balls. 
     * The balls in addition to this speed can not go.
     */
    private static final double MAX_SPEED = 0.1;
    /**
     * Fixed increment of the speed with which the balls move within the application.
     */
    private static final double INCREASE_SPEED = 0.02;

    /*
     * The "ratio" value is expressed in milliseconds. Indicates the sleep time of the 
     * BallGeneratorImpl thread. Therefore, to increase the frequency with which the balls 
     * are generated, we must decrease this value to decrease the sleep time.
     */
    private int ratio;
    private double speed;

    /**
     * Set default values for the ratio and speed fields.
     */
    public BallRatio() {
        super();
        this.ratio = DEFAULT_RATIO;
        this.speed = DEFAULT_SPEED;
    }

    /**
     * Increases the speed at which balls are generated and also increases 
     * the speed of movement of the balls.
     */
    @Override
    public void operationRatio() {
        if (this.ratio > MAX_RATIO) {
            this.ratio = this.ratio - DECREASE_RATIO;
        }
        if (this.speed < MAX_SPEED) {
            this.speed = this.speed + INCREASE_SPEED;
        }
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
     *          speed field
     */
    public double getSpeed() {
        return this.speed;
    }
}
