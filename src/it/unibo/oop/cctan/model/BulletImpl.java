package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import it.unibo.oop.cctan.geometry.Boundary;

public abstract class BulletImpl extends MovableItemImpl implements Bullet {

    private Optional<SquareAgent> lastCollision;
    
    protected BulletImpl(BulletBuilder builder) {
        super(builder);
        this.lastCollision = Optional.empty();
    }

    protected Optional<FixedItem> checkIntersecate(final Optional<FixedItem> lastCollision) {
        synchronized (this.getModel().getSquareAgents()) {

            final List<FixedItem> items = new ArrayList<>(this.getModel().getSquareAgents());
            items.addAll(this.getModel().getPowerUpBlocks());
            items.remove(lastCollision.orElse(null));
            for (final FixedItem it : items) {
                synchronized (it) {
                    if (this.intersectsWith(it)) {
                        if (it instanceof Hittable) {
                            ((Hittable) it).hit();
                        }
                        if (it instanceof SquareAgent) {
                            this.updateAngle((SquareAgent) it);
                        }
                        return Optional.of(it);
                    }
                }
            }
            return lastCollision;
       }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void applyConstraints() {
        final Boundary bounds = this.getModel().getBounds();
        if (this.getPos().getX() + this.getWidth() < bounds.getX0() || this.getPos().getX() > bounds.getX1()
                || this.getPos().getY() < bounds.getY0() || this.getPos().getY() - this.getHeight() > bounds.getY1()) {
            /*
             * Prendo la mutua esclusione sul model, in modo che quando elimino un oggetto, nessun'altro
             * può accedere al model in modo da evitare eventuali conflitti. 
             */
            synchronized (this.getModel()) {
                this.terminate();
                this.getModel().removeBullet(this);
            }
        }
    }

    protected Optional<SquareAgent> getLastCollision(){
        return this.lastCollision;
    }
    
    protected boolean intersectsWith(final FixedItem item) {
        final Area bulletArea = new Area(this.getShape());
        bulletArea.intersect(new Area(item.getShape()));
        return !bulletArea.isEmpty();
    }

    protected abstract void updateAngle(final SquareAgent item);
    
    /**
     * A basic builder for BallAgent class.
     */
    public abstract static class BulletBuilder extends MovableItemImpl.AbstractBuilderMI<BulletBuilder> {

    }
}
