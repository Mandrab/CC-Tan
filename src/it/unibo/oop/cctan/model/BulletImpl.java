package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.model.BallAgent.BallBuilder;
import javafx.geometry.Point2D;

public abstract class BulletImpl extends MovableItemImpl implements Bullet {

    protected BulletImpl(BulletBuilder builder) {
        super(builder);
    }

    protected Optional<SquareAgent> checkIntersecate(final Optional<SquareAgent> lastCollision) {
        synchronized (this.getModel().getSquareAgents()) {
            final List<SquareAgent> squares = new ArrayList<>(this.getModel().getSquareAgents());
            squares.remove(lastCollision.orElse(null));
            for (final SquareAgent squareAg : squares) {
                synchronized (squareAg) {
                    final Area bulletArea = new Area(this.getShape());
                    bulletArea.intersect(new Area(squareAg.getShape()));
                    if (!bulletArea.isEmpty()) {
                        squareAg.hit();
                        this.updateAngle(squareAg);
                        return Optional.of(squareAg);
                    }
                }
            }
            return Optional.empty();
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
//            synchronized (this.getModel()) {
//                this.getModel().removeBullet(this);
//            }
        }
    }
   
    protected abstract void updateAngle(final SquareAgent rect);
    
    /**
     * A basic builder for BallAgent class.
     */
    public abstract static class BulletBuilder extends MovableItemImpl.AbstractBuilderMI<BulletBuilder> {

    }
}
