package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represents a generic item in the game area, such as Balls, Squares, Shuttle...
 */
public interface Item {

    /**
     * Get the model of the MVC application.
     * @return
     *          the model
     */
    Model getModel();

    /**
     * Get the current left-upper bound position of the item.
     * @return
     *          the current position of the item
     */
    Point2D getPos();

    /**
     * Get the angle of the item: the angle between its axis and the x-axis.
     * @return
     *          the angle between item axis and x-axis.
     */
    double getAngle();
}
