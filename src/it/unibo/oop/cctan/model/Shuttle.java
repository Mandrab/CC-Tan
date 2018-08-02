package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * Represent the Shuttle: the place where balls will get out from.
 */
public interface Shuttle extends Item {

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
     * Create on runtime a new Shape representing the Shuttle, that will be
     * used by the View to draw it.
     * @return
     *          a new Shape representing the Shuttle
     */
    List<Point2D> getShape();

    /**
     * Set the inclination of the shuttle between its and x axis.
     * @param angle
     *                  the new angle between item and x-axis
     */
    void setAngle(double angle);
}
