package it.unibo.oop.cctan.model;

/**
 * This abstract class is a kind of timer that executes the operationRatio method 
 * at the end of every minute. 
 */
public abstract class TimerRatio extends Thread {

    /**
     * This value is expressed in milliseconds. Indicates that in a minute there are 
     * 60000 milliseconds. This value is used inside the sleep for this thread.
     */
    private static final int ONE_MINUTE = 60000;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (true) {
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
    public abstract void operationRatio();
}
