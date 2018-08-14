package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.model.Shuttle.PowerUpExecution;
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

    List<PowerUpExecution> getActivePowerUps();
    
    void activePowerUp(Pair<PowerUpBlock,PowerUpExecution> powerUpExecution);
    
    void removePowerUp(Pair<PowerUpBlock,PowerUpExecution> powerUpExecution);
    
    abstract class PowerUpExecution extends Thread {

        private boolean pause;
        private boolean stop;
        private final int amount;
        private long remaining;

        public PowerUpExecution(final int millis) {
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

        public synchronized void pause() {
            if (!this.pause) {
                this.pause = true;
                notifyAll();
            }
        }

        public synchronized void resumeRun() {
            if (this.pause) {
                this.pause = false;
                notifyAll();
            }
        }

        public synchronized void terminate() {
            if (!this.stop) {
                this.stop = true;
                notifyAll();
            }
        }

        public synchronized void increaseTimer(final int amount) {
            this.remaining += amount;
        }

        public synchronized int getTimer() {
            return this.amount;
        }
    }
}
