package it.unibo.oop.cctan.model;
import javafx.geometry.Point2D;

/**
 * Represent an abstract item that maintains its position during its life.
 */
public abstract class FixedItem implements Item {

    private final Model model;
    private Point2D pos;
    private double angle;

    /**
     * Put a new fixed item respecting the value specified inside the builder object.
     * @param builder
     *          the builder containing the desired parameters
     */
    protected FixedItem(final AbstractBuilderFI<?> builder) {
        this.model = builder.mod;
        this.pos = builder.pos;
        this.angle = builder.angleDir;
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
     * Get the angle of the object. The angle is the portion of area between x-axis
     * and item axis.
     * @return
     *          the movement angle
     */
    public synchronized double getAngle() {
        return this.angle;
    }

    /**
     * Set the angle of the item axis.
     * @see #getAngle()
     * @param angle
     *          the new movement angle
     */
    protected synchronized void setAngle(final double angle) {
        this.angle = angle;
    }

    /**
     * Set the position of the item.
     * @param pos
     *          the new position
     */
    protected synchronized void setPos(final Point2D pos) { // Movable items can change their position
        this.pos = pos;
    }

    /**
     * A basic abstract builder for FixedItem abstract class.
     * @param <T>
     *                  the current builder type
     */
    @SuppressWarnings("unchecked")
    public abstract static class AbstractBuilderFI<T extends AbstractBuilderFI<T>> {

        private Model mod;
        private Point2D pos;
        private double angleDir;

        /**
         * Set the model of the application based on MVC pattern.
         * @param model
         *              the model of the application
         * @return
         *              the current abstract builder for fixed items
         */
        public T model(final Model model) {
            this.mod = model;
            return (T) this;
        }

        /**
         * Set the starting position of the item.
         * @param startingPos
         *              the item starting position
         * @return
         *              the current abstract builder for fixed items
         */
        public T position(final Point2D startingPos) {
            this.pos = startingPos;
            return (T) this;
        }

        /**
         * Set the direction of movement, with the angle between direction and x-axis.
         * @param angle
         *              the angle of movement
         * @return
         *              the current abstract builder for movable items
         */
        public T angle(final double angle) {
            this.angleDir = angle;
            return (T) this;
        }

        /**
         * Builds the desired FixedItem object with the specified parameters.
         * @return
         *              the FixedItem object as wanted
         */
        public abstract FixedItem build();

        /**
         * Check that all fields are consistent to finally build the item.
         * @throws
         *              IllegalStateException if at least one parameter value is not correct to build the item.
         */
        protected void validate() {
            if (this.mod == null || this.pos == null) {
                throw new IllegalStateException();
            }
        }
    }
}
