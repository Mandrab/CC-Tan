package it.unibo.oop.cctan.model;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public abstract class PowerUpBlock extends FixedItemImpl implements PowerUp {

    private final Hittable hittableItem;

    protected PowerUpBlock(final PowerUpBlockBuilder<?> builder) {
        super(builder);
        this.hittableItem = new HittableImpl(builder.hits) {

            @Override
            protected void destroyed() {
                use();
            }
        };
    }

    @Override
    public int getHP() {
        return this.hittableItem.getHP();
    }

    @Override
    public void hit() {
        this.hittableItem.hit();
    }

    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight());
    }

    /**
     * A basic builder for BallAgent class.
     */
    @SuppressWarnings("unchecked")
    public abstract static class PowerUpBlockBuilder<T extends PowerUpBlockBuilder<T>>
            extends FixedItemImpl.AbstractBuilderFI<PowerUpBlockBuilder<T>> {

        private int hits;

        public T hitPoints(int hitPoints) {
            this.hits = hitPoints;
            return (T) this;
        }
    }
}
