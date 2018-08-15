package it.unibo.oop.cctan.model;

public abstract class PausableThread extends Thread implements Commands {

    public enum ActionOrder { DO_AND_WAIT, WAIT_AND_DO }
    
    private volatile boolean pause;
    private volatile boolean stop;
    private int amount;
    private long remaining;
    private ActionOrder action;

    public PausableThread(final int millis, ActionOrder action) {
        super();
        this.pause = false;
        this.stop = false;
        this.amount = millis;
        this.remaining = millis;
        this.action = action;
    }

    @Override
    public void run() {
        while (!this.stop) {
            if (this.action == ActionOrder.DO_AND_WAIT) {
                this.operation();
            }
            //System.out.println("Valore di sleep: " + this.amount);
            this.timeout();
            if (this.action == ActionOrder.WAIT_AND_DO) {
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
                    if (pause) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void operation();

    @Override
    public synchronized void pause() {
        if (!this.pause) {
            this.pause = true;
            notifyAll();
        }
    }

    @Override
    public synchronized void resumeGame() {
        if (this.pause) {
            this.pause = false;
            notifyAll();
        }
    }

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
    
    public void setSleepTime(int millis) {
        this.amount = millis;
    }
}
