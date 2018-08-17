package it.unibo.oop.cctan.interpackage_comunication;

import java.awt.Color;
import java.awt.Shape;

/**
 * A class that implements MappableData.
 */
public class MappableDataImpl implements MappableData {

    private final String text;
    private final Color color;
    private final Shape shape;

    /**
     * Constructor of the class.
     * 
     * @param text
     *            The text of the object
     * @param color
     *            The color of the object
     * @param shape
     *            The shape of the object
     */
    public MappableDataImpl(final String text, final Color color, final Shape shape) {
        this.text = text;
        this.color = color;
        this.shape = shape;
    }

    @Override
    /** {@inheritDoc} */
    public String getText() {
        return text;
    }

    @Override
    /** {@inheritDoc} */
    public Color getColor() {
        return color;
    }

    @Override
    /** {@inheritDoc} */
    public Shape getShape() {
        return shape;
    }

}
