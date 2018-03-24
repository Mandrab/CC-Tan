package it.unibo.oop.cctan.geometry;

/**
 *  Identify a two-dimensional point in the map area.
 */
public class P2D {

    private final double x;
    private final double y;

    /**
     * @param x
     *      the x coordinate
     * @param y
     *      the y coordinate
     */
    public P2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return
     *      the x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return
     *      the y coordinate
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
