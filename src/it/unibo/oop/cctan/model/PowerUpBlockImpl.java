package it.unibo.oop.cctan.model;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * The abstract PowerUpBlock implementation, to represent a generic (fixed) power-up block
 * in the game area. When it will be destroyed, then will be activate its ability.
 */
public abstract class PowerUpBlockImpl extends FixedItemImpl implements PowerUpBlock {

    private final Hittable hittableItem;

    /**
     * Create a new PowerUpBlock using values contained in the specified builder.
     * @param builder
     *                  the builder to construct the power-up block
     */
    protected PowerUpBlockImpl(final PowerUpBlockBuilder<?> builder) {
        super(builder);
        this.hittableItem = new HittableImpl(builder.hits) {

            @Override
            protected void destroyed() {
                getModel().removePowerUp(PowerUpBlockImpl.this);
                use();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHP() {
        return this.hittableItem.getHP();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final int damage) {
        this.hittableItem.hit(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight());
    }

    /**
     * A basic builder for BallAgent class.
     * @param <T>
     *          the type of power-up block builder
     */
    @SuppressWarnings("unchecked")
    public abstract static class PowerUpBlockBuilder<T extends PowerUpBlockBuilder<T>>
            extends FixedItemImpl.AbstractBuilderFI<PowerUpBlockBuilder<T>> {

        private int hits;

        /**
         * Set the starting hit points of the power-up block.
         * @param hitPoints
         *              amount of total hit points for the power-up block
         * @return
         *              the current builder for power-up blocks
         */
        public T hitPoints(final int hitPoints) {
            this.hits = hitPoints;
            return (T) this;
        }
    }
}
