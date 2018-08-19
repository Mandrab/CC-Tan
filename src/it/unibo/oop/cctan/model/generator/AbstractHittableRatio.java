package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.geometry.RandomUtility;

/**
 * This abstract class is a kind of timer that executes the operationRatio method 
 * at the end of every minute. The operationRatio method is used to change the speed, 
 * the frequency and the points over time, with which the objects (which implement this class) are generated.
 */
public abstract class AbstractHittableRatio extends AbstractTimerRatio {

    private static final int DELTA = 10;
    private final int defaultPoints;
    private int points;

    /**
     * Create a new HittableRatio thread.
     * @param speed
     *          It's the initial speed of the item.
     * @param ratio
     *          It's the initial frequency with which the object is generated.
     * @param defaultPoints
     *          It's the points that the hittable objects have when they are generated at the beginning.
     */
    public AbstractHittableRatio(final double speed, final int ratio, final int defaultPoints) {
        super(speed, ratio);
        this.points = defaultPoints + DELTA;
        this.defaultPoints = defaultPoints;
    }

    /**
     * Set the points of the item.
     * @param points
     *          The new value of points.
     */
    protected void setPoints(final int points) {
        this.points = points;
    }

    /**
     * @return
     *          Return the current points of the item.
     */
    protected int getPoints() {
        return this.points;
    }

    /**
     * @return
     *          Return a new integer random between defaultPoints and current points.
     */
    protected int getHP() {
        return RandomUtility.intInRange(this.defaultPoints, this.points);
    }
}
