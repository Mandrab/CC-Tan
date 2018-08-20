package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import javafx.geometry.Point2D;

/**
 * Represent the Shuttle: the place where balls will get out from.
 */
public interface Shuttle extends FixedItem {

    /**
     * Get the position of the upper vertex, where balls will go out from.
     * @return
     *          the position of the upper vertex od the triangle
     */
    Point2D getTop();

    /**
     * Get the area in which, when a square impact in, the game will get over.
     * @return
     *          the game over area
     */
    Area getImpactArea();

    /**
     * Create, on runtime, a new list of points, composing the triangle representing the Shuttle,
     * that will be used by the View to draw it.
     * @return
     *          a new list of points representing the triangle (Shuttle).
     *          The 3 points are returned in this order: the top, the left base vertex and the right base vertex.
     */
    List<Point2D> getShapePoints();

    /**
     * Get the list of all active powerups at the moment.
     * @return
     *          the list of all powerup current executions
     */
    List<PowerUpExecution> getActivePowerUps();

    /**
     * Active a new powerup execution. If there's already one actived
     * for the specified powerup block, increment its timer countdown
     * by default powerup's timeout.
     * @param powerUpExecution
     *          a pair that represent the current powerup execution
     *          for the powerup block
     */
    void activePowerUp(Pair<PowerUpBlock, PowerUpExecution> powerUpExecution);

    /**
     * Remove a powerup execution from the shuttle's active powerup list.
     * @param powerUpExecution
     *          the powerup execution to be removed from list
     */
    void removePowerUp(Pair<PowerUpBlock, PowerUpExecution> powerUpExecution);

    /**
     * Represent the execution of a single powerup. It consist of a timer
     * that simply when it expires, execute an operation implemented
     * by subclasses.
     */
    abstract class PowerUpExecution extends Thread implements Commands {

        private boolean pause;
        private boolean stop;
        private final int amount;
        private long remaining;

        public PowerUpExecution(final int millis) {
            super();
            this.pause = false;
            this.stop = false;
            this.amount = millis;
            this.remaining = this.amount;
        }

        @Override
        public void run() {
            long time;
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
            this.operation();
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
    }
}
