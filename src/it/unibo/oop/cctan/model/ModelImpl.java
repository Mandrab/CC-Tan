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
    private final BallGenerator ballGenerator;
    private final SquareGenerator squareGenerator;

    /**
     * Instance a new Model, creating the default game area boundaries, a new
     * Shuttle and balls and squares generators.
     */
    public ModelImpl() {
        this.bound = new Boundary(-1, -1, 1, 1);
        this.shuttle = new ShuttleImpl(this);
        this.squareGenerator = new SquareGeneratorImpl(this);
        this.ballGenerator = new BallGeneratorImpl(this);
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
    public void removeSquare(final SquareAgent square) {
        this.squareGenerator.removeSquare(square);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBall(final BallAgent ball) {
        this.ballGenerator.removeBall(ball);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SquareAgent> getSquareAgents() {
        return this.squareGenerator.getSquareAgents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<BallAgent> getBallAgents() {
        return this.ballGenerator.getBallAgents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shuttle getShuttle() {
        return this.shuttle;
    }
}
