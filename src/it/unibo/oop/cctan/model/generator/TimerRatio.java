package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.Commands;

/**
 * This abstract class is a kind of timer that executes the operationRatio method 
 * at the end of every minute. The operationRatio method is used to change the speed 
 * and/or the frequency with which the objects (which implement this class) are generated.
 */
public abstract class TimerRatio extends Thread implements Commands {

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

    private boolean stop;
    private boolean suspend;
    private final Object pauseLock;
    private final int startingRatio;
    private final double startingSpeed;

    /**
     * Create a new TimerRatio thread.
     * @param speed
     *          It's the initial speed of the item.
     * @param ratio
     *          It's the initial frequency with which the object is generated.
     */
    public TimerRatio(final double speed, final int ratio) {
        super();
        this.stop = false;
        this.speed = speed;
        this.ratio = ratio;
        this.suspend = false;
        this.startingSpeed = speed;
        this.startingRatio = ratio;
        this.pauseLock = new Object();
    }

    /**
     * It's responsible for executing the operationRatio method at the end of every minute.
     */
    @Override
    public void run() {
        while (!this.stop) {
            synchronized (pauseLock) {
                if (this.stop) {
                    break;
                }
                if (this.suspend) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (this.stop) {
                        break;
                    }
                }
            }
            operationRatio();
            try {
                Thread.sleep(ONE_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Increase or decrease different values, to increase the number of squares and balls 
     *  that are generated within the application. It is also used to increase the speed of 
     *  movement of the various objects, or to increase the initial life of the squares.
     */
    protected abstract void operationRatio();

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        if (this.suspend) {
            this.suspend = false;
        }
        this.stop = true;
        this.ratio = this.startingRatio;
        this.speed = this.startingSpeed;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void pause() {
        this.suspend = true;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void resumeGame() {
        synchronized (this.pauseLock) {
            this.suspend = false;
            this.pauseLock.notifyAll();
        }
    }

    /**
     * @return
     *          ratio field
     */
    protected int getRatio() {
        return this.ratio;
    }

    /**
     * @return
     *          speed field
     */
    protected double getSpeed() {
        return this.speed;
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
     * @param ratio
     *          it's the new ratio of the item.
     */
    protected void setRatio(final int ratio) {
        this.ratio = ratio;
    }

}
