package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.Commands;
import it.unibo.oop.cctan.model.PausableThread;

/**
 * This abstract class is a kind of timer that executes the operationRatio method 
 * at the end of every minute. The operationRatio method is used to change the speed 
 * and/or the frequency with which the objects (which implement this class) are generated.
 */
public abstract class AbstractTimerRatio extends PausableThread implements Commands {

    /**
     * This value is expressed in milliseconds. Indicates that in a minute there are 
     * 60000 milliseconds. This value is used inside the sleep for this thread.
     */
    private static final int ONE_MINUTE = 60000;
    /**
     * This value specifies the speed of movement of the object.
     */
    private double speed;
    /**
     * This value is expressed in milliseconds. Indicates the sleep time of the 
     * BallGeneratorImpl thread. Therefore, to increase the frequency with which the balls 
     * are generated, we must decrease this value to decrease the sleep time.
     */
    private int ratio;
    private final int startingRatio;
    private final double startingSpeed;

    /**
     * Create a new TimerRatio thread.
     * @param speed
     *          It's the initial speed of the item.
     * @param ratio
     *          It's the initial frequency with which the object is generated.
     */
    public AbstractTimerRatio(final double speed, final int ratio) {
        super(ONE_MINUTE, ActionOrder.WAIT_AND_DO);
        this.speed = speed;
        this.ratio = ratio;
        this.startingSpeed = speed;
        this.startingRatio = ratio;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        super.terminate();
        this.speed = this.startingSpeed;
        this.ratio = this.startingRatio;
    }
    /**
     * @return
     *          speed field
     */
    protected double getSpeed() {
        return this.speed;
    }

    /**
     * @return
     *          ratio field
     */
    protected int getRatio() {
        return this.ratio;
    }

    /**
     * @param ratio
     *          ratio field
     */
    protected void setRatio(final int ratio) {
        this.ratio = ratio;
    }

    /**
     * @return
     *          ratio field
     */
    protected int getFrequency() {
        return this.ratio;
    }

    /**
     * Set the new speed.
     * @param speed
     *          it's the new speed of the item.
     */
    protected void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Set the new ratio field.
     * @param frequency
     *          it's the new frequency of the item.
     */
    protected void setFrequency(final int frequency) {
        this.ratio = frequency;
    }

}
