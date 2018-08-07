package it.unibo.oop.cctan.model;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import it.unibo.oop.cctan.geometry.Boundary;

public abstract class BulletImpl extends MovableItemImpl implements Bullet {

    protected BulletImpl(BulletBuilder builder) {
        super(builder);
    }

    protected Optional<SquareAgent> checkIntersecate(final Optional<SquareAgent> lastCollision) {
        final List<MovableItem> squares = new ArrayList<>(this.getModel().getSquareAgents());
        squares.remove(lastCollision.orElse(null));
        for (final MovableItem squareAg : squares) {
            final Area bulletArea = new Area(this.getShape());
            bulletArea.intersect(new Area(squareAg.getShape()));
            if (!bulletArea.isEmpty()) {
                ((SquareAgent) squareAg).hit();
                this.updateAngle((SquareAgent) squareAg);
                return Optional.of((SquareAgent) squareAg);
            }
        }
        return lastCollision;
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
                this.getModel().removeBullet(this);
            }
        }
    }
   
    protected abstract void updateAngle(final SquareAgent rect);
    
    /**
     * A basic builder for BallAgent class.
     */
    public abstract static class BulletBuilder extends MovableItemImpl.AbstractBuilderMI<BulletBuilder> {

    }
}
