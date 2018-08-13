package it.unibo.oop.cctan.model;

import java.util.List;
import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl;
import it.unibo.oop.cctan.model.generator.ItemGenerator;
import it.unibo.oop.cctan.model.generator.PowerUpGeneratorImpl;
import it.unibo.oop.cctan.model.generator.SquareGeneratorImpl;

/**
 * The implementation of Model interface, with operations to work with balls and
 * squares.
 */
public class ModelImpl implements Model {

    private final ItemGenerator<SquareAgent> squareGenerator;
    private final ItemGenerator<PowerUpBlock> powerupGenerator;
    private final ItemGenerator<Bullet> bulletGenerator;
    private final Shuttle shuttle;
    private final Boundary bound;
    private final Score score;
    private GameStatus gameStatus;

    /**
     * Instance a new Model, creating the default game area boundaries, a new
     * Shuttle and balls and squares generators.
     */
    public ModelImpl() {
        this.score = Score.getScore();
        this.bound = new Boundary(-1, -1, 1, 1);
        this.squareGenerator = new SquareGeneratorImpl(this);
        this.bulletGenerator = new BulletGeneratorImpl(this);
        this.powerupGenerator = new PowerUpGeneratorImpl(this);
        this.shuttle = new ShuttleImpl(this);
        this.gameStatus = GameStatus.ENDED;
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
        if (gameStatus.equals(GameStatus.ENDED)) {
            squareGenerator.launch();
            bulletGenerator.launch();
            powerupGenerator.launch();
            gameStatus = GameStatus.RUNNING;
        }
    }

    @Override
    public void terminate() {
        if (!gameStatus.equals(GameStatus.ENDED)) {
            squareGenerator.terminate();
            bulletGenerator.terminate();
            powerupGenerator.terminate();
            gameStatus = GameStatus.ENDED;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shuttle getShuttle() {
        return this.shuttle;
    }

    public void removeSquare(final SquareAgent square) {
        this.squareGenerator.removeItem(square);
    }

    public void removeBullet(final Bullet bullet) {
        this.bulletGenerator.removeItem(bullet);
    }

    public List<SquareAgent> getSquareAgents() {
        return this.squareGenerator.getItems();
    }

    public synchronized List<Bullet> getBulletAgents() {
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
    public ItemGenerator<Bullet> getBulletGenerator() {
        return this.bulletGenerator;
    }

    @Override
    public void removePowerUp(PowerUpBlock powerup) {
        this.powerupGenerator.removeItem(powerup);
    }

    @Override
    public List<PowerUpBlock> getPowerUpBlocks() {
        return this.powerupGenerator.getItems();
    }

    @Override
    public void pause() {
        if (gameStatus.equals(GameStatus.RUNNING)) {
            bulletGenerator.getItems().forEach(b->b.pause());
            squareGenerator.getItems().forEach(s->s.pause());
            //mettere in pausa i singoli generatori...
            gameStatus = GameStatus.PAUSED;
        }
    }

    @Override
    public void resume() {
        if (gameStatus.equals(GameStatus.PAUSED)) {
            bulletGenerator.getItems().forEach(b->b.resume());
            squareGenerator.getItems().forEach(s->s.resume());
            //riprendere l'esecuzione dei singoli generatori...
            gameStatus = GameStatus.RUNNING;
        }
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
