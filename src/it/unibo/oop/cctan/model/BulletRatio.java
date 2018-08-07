package it.unibo.oop.cctan.model;

/**
 * The task of this class is to increase the difficulty of play by acting on the balls that are 
 * present in the application. This class allows you to increase the number of balls, within the 
 * application, in proportion to the increase in playing time. In addition, it also allows you to 
 * increase the speed at which balls move from one point to another.
 */
public class BulletRatio extends TimerRatio {

    /**
     * This value is expressed in milliseconds. Indicates the initial sleep time 
     * that is used in the BallGeneratorImpl thread at the start of the game
     */
    private static final int DEFAULT_RATIO = 300;
    /**
     * This value is expressed in milliseconds. Indicates the initial speed 
     * at which the balls are moved at the start of the game
     */
    private static final double DEFAULT_SPEED = 0.01;

    /**
     * Indicates the maximum frequency with which the balls are generated.
     */
    private static final int MAX_RATIO = 100;
    /**
     * Fixed increase in the frequency with which the balls are generated.
     */
    private static final int DECREASE_RATIO = 5;
    /**
     * Indicates the maximum speed of movement that can reach the balls. 
     * The balls in addition to this speed can not go.
     */
    private static final double MAX_SPEED = 0.08;
    /**
     * Fixed increment of the speed with which the balls move within the application.
     * With this value, after 8 minutes will be reached the maximum speed
     */
    private static final double INCREASE_SPEED = 0.001;

    /**
     * Set default values for the ratio and speed fields.
     */
    public BulletRatio() {
        super(DEFAULT_SPEED, DEFAULT_RATIO);
    }

    /**
     * Increases the speed at which balls are generated and also increases 
     * the speed of movement of the balls.
     */
    @Override
    public void operationRatio() {
        if (this.getRatio() > MAX_RATIO) {
            this.setRatio(this.getRatio() - DECREASE_RATIO);
        }
        if (this.getSpeed() < MAX_SPEED) {
            this.setSpeed(this.getSpeed() + INCREASE_SPEED);
        }
    }
}
