package it.unibo.oop.cctan.model;

import java.awt.Shape;

import javafx.geometry.Point2D;

/**
 * Represents a generic item in the game area, such as Balls, Squares, Shuttle...
 */
public interface FixedItem {

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
     * Get the item width.
     * @return
     *          the item width
     */
    double getWidth();

    /**
     * get the item height.
     * @return
     *          the item height
     */
    double getHeight();

    /**
     * Get the angle of the item: the angle between its axis and the x-axis.
     * @return
     *          the angle between item axis and x-axis.
     */
    double getAngle();

    /**
     * Get the shape representing the item.
     * @return
     *          a new Shape object representing the item
     */
    Shape getShape();

    /**
     * Set the angle of the item axis.
     * @see #getAngle()
     * @param angle
     *          the new movement angle
     */
    void setAngle(double angle);
}
