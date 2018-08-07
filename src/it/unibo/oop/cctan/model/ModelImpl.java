package it.unibo.oop.cctan.model;

import java.util.List;
import it.unibo.oop.cctan.geometry.Boundary;

/**
 * The implementation of Model interface, with operations to work with balls and
 * squares.
 */
public class ModelImpl implements Model {

    private final Score score;
    private final Boundary bound;
    private final Shuttle shuttle;
    private final ItemGenerator bulletGenerator;
    private final ItemGenerator squareGenerator;

    /**
     * Instance a new Model, creating the default game area boundaries, a new
     * Shuttle and balls and squares generators.
     */
    public ModelImpl() {
        this.score = Score.getScore();
        this.bound = new Boundary(-1, -1, 1, 1);
        this.squareGenerator = new SquareGeneratorImpl(this);
        this.bulletGenerator = new BulletGeneratorImpl(this);
        this.shuttle = new ShuttleImpl(this);
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
        this.bulletGenerator.launch();
    }

    @Override
    public void terminate() {
        this.squareGenerator.terminate();
        this.bulletGenerator.terminate();
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

    public void removeBullet(final MovableItem bullet) {
        this.bulletGenerator.removeItem(bullet);
    }

    public List<MovableItem> getSquareAgents() {
        return this.squareGenerator.getItems();
    }

    public synchronized List<MovableItem> getBulletAgents() {
        return this.bulletGenerator.getItems();
    }

    @Override
    public void setSpaceshipAngle(final double angle) {
        this.shuttle.setAngle(angle);
    }

    @Override
    public Score getScore() {
        return this.score;
    }

    @Override
    public ItemGenerator getBulletGenerator() {
        return this.bulletGenerator;
    }
}
