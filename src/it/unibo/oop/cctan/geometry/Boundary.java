package it.unibo.oop.cctan.geometry;

/**
 * Represent the map area boundaries.
 */
public class Boundary {

    private double x0;
    private double y0;
    private double x1;
    private double y1;

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

    public void setBoundary(final double x0, final double x1, final double y0, final double y1) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }

    /**
     * Return the value of the left bound.
     * @return
     *      the value of the left bound
     */
    public double getX0() {
        return this.x0;
    }

    /**
     * Return the value of the right bound.
     * @return
     *      the value of the right bound
     */
    public double getX1() {
        return this.x1;
    }

    /**
     * Return the value of the lower bound.
     * @return
     *      the value of the lower bound
     */
    public double getY0() {
        return this.y0;
    }

    /**
     * Return the value of the upper bound.
     * @return
     *      the value of the upper bound
     */
    public double getY1() {
        return this.y1;
    }

    public double width() {
        return Math.abs(this.x1 - this.x0);
    }

    public double height() {
        return Math.abs(this.y1 - this.y0);
    }
}
