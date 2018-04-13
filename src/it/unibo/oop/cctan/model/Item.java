package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represents a generic item in the game area, such as Balls, Squares, Shuttle...
 */
public interface Item {

    /**
     * Get the model of the MVC application.
     * @return the model
     */
    Model getModel();

    /**
     * Get the current left-upper bound position of the item.
     * @return the current position of the item
     */
    Point2D getPos();

    /**
     * Get the width of the item in proportion to the window width.
     * @return the width of the item
     */
    double getWidth();

    /**
     * Get the height of the item in proportion to the window height.
     * @return the height of the item
     */
    double getHeight();
}
