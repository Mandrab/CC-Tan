package it.unibo.oop.cctan.interpackage_comunication.data;

import java.awt.Color;
import java.awt.Shape;

/**
 * Interface that specifies which method must have a data to be able to be printed on the screen.
 */
public interface MappableData {

    /**
     * Return the text to be printed at the center of the object.
     * @return
     * The text of the object
     */
    String getText();

    /**
     * Return the color of the object.
     * @return
     * The color of the object
     */
    Color getColor();

    /**
     * Return the object shape.
     * @return
     * The shape of the object
     */
    Shape getShape();

}
