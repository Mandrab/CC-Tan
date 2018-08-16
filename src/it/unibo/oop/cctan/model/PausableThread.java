package it.unibo.oop.cctan.model;

/**
 * An abstract class to work with Thread. This class allows to run a thread
 * that executes a specific operation each timeout interval. It is possible
 * to choose whether running operation after timer elapses or before.
 * In more, the thread can go in pause, then resume and finally terminate.
 * An interesting fact is that when thread goes in pause mode, also the
 * timer goes (i.e. the sleep stops). Then, when the thread resume its execution,
 * timer continue counting starting from the point it has been interrupted,
 * till the end.
 */
public abstract class PausableThread extends Thread implements Commands {

    /**
     * A simply enumeration to identify the operation order, i.e. run operation,
     * then wait for timer timeout or wait for timeout event and the execute operation.
     */
    public enum ActionOrder { DO_AND_WAIT, WAIT_AND_DO }

    private volatile boolean suspend;
    private volatile boolean stop;
    private int amount;
    private long remaining;
    private final ActionOrder order;

    /**
     * Create a new thread that will execute the specified operation after
     * or before a timeout elapse event. If you want to run the operation
     * only once, remember to conclude operation override method with a
     * call to terminate function.
     * @param millis
     *          amount of time, in milliseconds, to specify the interval
     *          between two operation executions
     * @param actionOrder
     *          specify weather executing operation before or after time elapse
     */
    protected PausableThread(final int millis, final ActionOrder actionOrder) {
        super();
        this.suspend = false;
        this.stop = false;
        this.amount = millis;
        this.remaining = millis;
        this.order = actionOrder;
    }

    /**
     * The operation that will be executed by current thread.
     */
    @Override
    public void run() {
        this.action();
    }

    /**
     * The action that will be executed in the current thread
     * (i.e. the body of run method). It has been inserted in a 
     * separate function to be called without warning in subclasses
     * or other classes in this package.
     */
    protected void action() {
        while (!this.stop) {
            if (this.order == ActionOrder.DO_AND_WAIT) {
                this.operation();
            }
            //System.out.println("Valore di sleep: " + this.amount);
            this.timeout();
            if (this.order == ActionOrder.WAIT_AND_DO) {
                this.operation();
            }
        }
    }

    private void timeout() {
        long time;
        this.remaining = this.amount;
        while (!stop && remaining > 0) {
            try {
                synchronized (this) {
                    time = System.currentTimeMillis();
                    wait(remaining);
                    remaining = remaining - (System.currentTimeMillis() - time);
                    if (suspend) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The operation that will be executed on each timeout event.
     */
    protected abstract void operation();

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void pause() {
        if (!this.suspend) {
            this.suspend = true;
            notifyAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void resumeGame() {
        if (this.suspend) {
            this.suspend = false;
            notifyAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        if (!this.stop) {
            this.stop = true;
            notifyAll();
        }
    }

    /**
     * Increase timer by the specified value.
     * @param amount
     *              the value to be added at the current remaining time
     */
    public synchronized void increaseTimer(final int amount) {
        this.remaining += amount;
    }

    /**
     * Set the new timer duration.
     * @param millis
     *          the new timer duration
     */
    public void setSleepTime(final int millis) {
        this.amount = millis;
    }
}
