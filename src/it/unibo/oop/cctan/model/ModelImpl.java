package it.unibo.oop.cctan.model;

import java.util.List;
import it.unibo.oop.cctan.geometry.Boundary;

/**
 * The implementation of Model interface, with operations to work with balls and
 * squares.
 */
public class ModelImpl implements Model {

    private final Boundary bound;
    private final Shuttle shuttle;
    private final ItemGenerator ballGenerator;
    private final ItemGenerator squareGenerator;

    /**
     * Instance a new Model, creating the default game area boundaries, a new
     * Shuttle and balls and squares generators.
     */
    public ModelImpl() {
        this.bound = new Boundary(-1, -1, 1, 1);
        this.shuttle = new ShuttleImpl(this);
        this.squareGenerator = new SquareGeneratorImpl(this);
        this.ballGenerator = new BulletGeneratorImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boundary getBounds() {
        return this.bound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void launch() {
        this.squareGenerator.launch();
        this.ballGenerator.launch();
    }

    @Override
    public void terminate() {
        this.squareGenerator.terminate();
        this.ballGenerator.terminate();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shuttle getShuttle() {
        return this.shuttle;
    }

    public void removeSquare(final MovableItem square) {
        this.squareGenerator.removeItem(square);
    }

    public void removeBall(final MovableItem ball) {
        this.ballGenerator.removeItem(ball);
    }

    public List<MovableItem> getSquareAgents() {
        return this.squareGenerator.getItems();
    }

    public synchronized List<MovableItem> getBallAgents() {
        return this.ballGenerator.getItems();
    }

    @Override
    public void setSpaceshipAngle(double angle) {
        this.shuttle.setAngle(angle);
    }
}
