package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.cctan.geometry.Boundary;

/**
 * An abstract bullet interface implementation, to manage generic bullet behavior
 * in the game area.
 */
public abstract class BulletImpl extends MovableItemImpl implements Bullet {

    private Optional<FixedItem> lastCollision;

    /**
     * Create a new bullet using values contained in the specified builder.
     * @param builder
     *                  the builder to construct the bullet
     */
    protected BulletImpl(final BulletBuilder builder) {
        super(builder);
        this.lastCollision = Optional.empty();
    }

    /**
     * Check if the bullet collides with other hittable items in the game area. In this case
     * call their {@link Hittable#hit(int) hit} methods. In more, if the hit item is of
     * {@link SquareAgent SquareAgent} type, call the abstract method
     * {@link BulletImpl#updateAngle(SquareAgent) updateAngle} to eventually edit bullet's angle.
     * @param lastCollision
     *                  last item been hit by the bullet
     * @param damage
     *                  damage amount to be subtracted to the hit item
     */
    protected void checkIntersecate(final Optional<FixedItem> lastCollision, final int damage) {
        synchronized (this.getModel().getSquareAgents()) {

            final List<FixedItem> items = new ArrayList<>(this.getModel().getSquareAgents());
            items.addAll(this.getModel().getPowerUpBlocks());
            items.remove(lastCollision.orElse(null));
            for (final FixedItem it : items) {
                synchronized (it) {
                    if (this.intersectsWith(it)) {
                        if (it instanceof Hittable) { // always true but for be sure...
                            ((Hittable) it).hit(damage);
                        }
                        if (it instanceof SquareAgent) { // possible bounce only on squares
                            this.updateAngle((SquareAgent) it);
                        }
                        this.lastCollision = Optional.of(it);
                    }
                }
            }
       }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void applyConstraints() {
        final Boundary bounds = this.getModel().getBounds();
        if (this.getPos().getX() + this.getWidth() < bounds.getX0() || this.getPos().getX() > bounds.getX1()
                || this.getPos().getY() + this.getHeight() < bounds.getY0() || this.getPos().getY() - this.getHeight() > bounds.getY1()) {
            synchronized (this.getModel().getBulletAgents()) {
                this.terminate();
                this.getModel().removeBullet(this);
            }
        }
    }

    /**
     * Get the last item the bullet collide with. If bullet has never collided
     * with something, return an empty optional.
     * @return
     *          a FixedItem Optional representing the last item the bullet
     *          collided with
     */
    protected Optional<FixedItem> getLastCollision() {
        return this.lastCollision;
    }

    /**
     * Check if current bullet collides with the specified item.
     * @param item
     *          the item (like squares, power up blocks...) to test if bullet
     *          collides with
     * @return
     *          true if current bullet and item collides, false otherwise
     */
    protected boolean intersectsWith(final FixedItem item) {
        final Area bulletArea = new Area(this.getShape());
        bulletArea.intersect(new Area(item.getShape()));
        return !bulletArea.isEmpty();
    }

    /**
     * When a bullet collides with some square, bullet's angle may change,
     * due to the bounce in the square edge. This method will be used
     * by subclasses to decide whether and how change the angle.
     * @param square
                 the square the bullet collided with
     */
    protected abstract void updateAngle(SquareAgent square);

    /**
     * A basic abstract builder for BulletImpl class.
     */
    public abstract static class BulletBuilder extends MovableItemImpl.AbstractBuilderMI<BulletBuilder> {

    }
}
