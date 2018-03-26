package it.unibo.oop.cctan.geometry;

/**
 *  Identify a two-dimensional point in the map area.
 */
public class Point2D {

    private final double x;
    private final double y;

    /**
     * @param x
     *      the x coordinate of the point
     * @param y
     *      the y coordinate of the point
     */
    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the x coordinate of the point in a xy axis.
     * @return
     *      the x coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y coordinate of the point in a xy axis.
     * @return
     *      the y coordinate of the point
     */
    public double getY() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "P2D [x=" + x + ", y=" + y + "]";
    }
}
