package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

public abstract class FixedItem implements Item {

    private final Model model;
    private Point2D pos;

    protected FixedItem(final Model model) {
        this.model = model;
    }

    @Override
    public synchronized Point2D getPos() {
        return new Point2D(this.pos.getX(), this.pos.getY());
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    public abstract double getWidth();

    public abstract double getHeight();

    protected synchronized void setPos(final Point2D pos) {
        this.pos = pos;
    }
}
