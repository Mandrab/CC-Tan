package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.P2D;

public abstract class FixedItem implements Item {

    private final Model model;
    private P2D pos;

    protected FixedItem(final Model model) {
        this.model = model;
    }

    @Override
    public synchronized P2D getPos() {
        return new P2D(this.pos.getX(), this.pos.getY());
    }

    public abstract double getWidth();

    public abstract double getHeight();

    protected Model getModel() {
        return this.model;
    }

    protected synchronized void setPos(final P2D pos) {
        this.pos = pos;
    }
}
