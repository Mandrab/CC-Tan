package it.unibo.oop.cctan.model;

/**
 * The implementation of Hittable interface to manage hittable items.
 */
public abstract class HittableImpl implements Hittable {

    private int hitPoints;

    /**
     * Create the new empty hittable item, with 0 hit points.
     */
    protected HittableImpl() { 
        this.hitPoints = 0;
    }

    /**
     * Create the new hittable item with the specified hit points.
     * @param hitPoints
     *                  the item's hit points (life), i.e. total damage
     *                  it has to receive to be destroyed
     */
    protected HittableImpl(final int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getHP() {
        return this.hitPoints;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void hit(final int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints <= 0) {
            this.hitPoints = 0;
            this.destroyed();
        }
    }

    /**
     * Specify what will happen as soon as item hit points go under 0.
     */
    protected abstract void destroyed();
}
