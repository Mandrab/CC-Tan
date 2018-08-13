package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.Commands;

/**
 * This abstract class is a kind of timer that executes the operationRatio method 
 * at the end of every minute. 
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

    /**
     * Create a new TimerRatio thread.
     * @param speed
     *          it's the initial speed of the item.
     * @param ratio
     *          it's the initial frequency with which the object is generated.
     */
    public TimerRatio(final double speed, final int ratio) {
        super();
        this.stop = false;
        this.speed = speed;
        this.ratio = ratio;
        this.suspend = false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                synchronized (this) {
                    if (this.suspend) {
                        wait();
                    }
                    if (!this.stop || !this.suspend) {
                        operationRatio();
                    }
                }
                Thread.sleep(ONE_MINUTE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *  Increase or decrease different values, to increase the number of squares and balls 
     *  that are generated within the application. It is also used to increase the speed of 
     *  movement of the various objects, or to increase the initial life of the squares.
     */
    public abstract void operationRatio();

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        if (this.suspend) {
            this.suspend = false;
        }
        this.stop = true;
        notifyAll();
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
        this.suspend = false;
        notifyAll();
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
