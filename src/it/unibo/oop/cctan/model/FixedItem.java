package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represent an abstract item that maintains its position during his life.
 */
public abstract class FixedItem implements Item {

    private final Model model;
    private Point2D pos;

    /**
     * Put a new fixed item in the specified position.
     * @param model
     *          the model of the application
     * @param startingPos
     *          the starting position of the item
     */
    protected FixedItem(final Model model, final Point2D startingPos) {
        this.model = model;
        this.pos = startingPos;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized Point2D getPos() {
        return new Point2D(this.pos.getX(), this.pos.getY());
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Model getModel() {
        return this.model;
    }

    /** 
     * {@inheritDoc}
     */
    public abstract double getWidth();

    /** 
     * {@inheritDoc}
     */
    public abstract double getHeight();

    /**
     * Set the position of the item.
     * @param pos
     *          the new position
     */
    protected synchronized void setPos(final Point2D pos) { //Movable items can change their position
        this.pos = pos;
    }
}
