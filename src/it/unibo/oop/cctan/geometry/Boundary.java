package it.unibo.oop.cctan.geometry;


/**
 * Represent the map area boundaries.
 */
public class Boundary {

    private final double x0;
    private final double y0;
    private final double x1;
    private final double y1;

    /**
     * @param x0
     *      left bound
     * @param y0
     *      lower bound
     * @param x1
     *      right bound
     * @param y1
     *      upper bound
     */
    public Boundary(final double x0, final double y0, final double x1, final double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    /**
     * @return
     *      the value of the left bound
     */
    public double getX0() {
        return this.x0;
    }

    /**
     * @return
     *      the value of the right bound
     */
    public double getX1() {
        return this.x1;
    }

    /**
     * @return
     *      the value of the lower bound
     */
    public double getY0() {
        return this.y0;
    }

    /**
     * @return
     *      the value of the upper bound
     */
    public double getY1() {
        return this.y1;
    }
}
