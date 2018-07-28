package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * Represent an abstract item that maintains its position during its life.
 */
public abstract class FixedItem implements Item {

    private final Model model;
    private Point2D pos;

    /**
     * Put a new fixed item respecting the value specified inside the builder object.
     * @param builder
     *          the builder containing the desired parameters
     */
    protected FixedItem(final AbstractBuilder builder) {
        this.model = builder.mod;
        this.pos = builder.pos;
    }

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
     * Set the position of the item.
     * @param pos
     *          the new position
     */
    protected synchronized void setPos(final Point2D pos) { //MovableItem class, can change their position
        this.pos = pos;
    }

    /**
     * A basic abstract builder for FixedItem abstract class.
     */
    public abstract static class AbstractBuilder {

        private Model mod;
        private Point2D pos;

        /**
         * Set the model of the application based on MVC pattern.
         * @param model
         *              the model of the application
         * @return
         *              the current abstract builder for fixed items
         */
        public AbstractBuilder model(final Model model) {
            this.mod = model;
            return this;
        }

        /**
         * Set the starting position of the item.
         * @param startingPos
         *              the item starting position
         * @return
         *              the current abstract builder for fixed items
         */
        public AbstractBuilder position(final Point2D startingPos) {
            this.pos = startingPos;
            return this;
        }

        /**
         * Builds the desired FixedItem object with the specified parameters.
         * @return
         *              the FixedItem object as wanted
         */
        public abstract FixedItem build();

        /**
         * Check that all fields are consistent to finally build the item.
         * @throws IllegalStateException if at least one parameter value is not correct to build the item.
         */
        protected void validate() {
            if (this.mod == null || this.pos == null) {
                throw new IllegalStateException();
            }
        }
    }
}
