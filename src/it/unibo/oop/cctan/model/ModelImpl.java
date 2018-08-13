package it.unibo.oop.cctan.model;

import java.util.Arrays;
import java.util.Collections;
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

    /*
     * Questa lista deve contenere tutti i Builder di tutti i powerUp che sono 
     * all'interno dell'applicazione in modo che vengano generati sempre dei power up diversi.
     */
    private static final List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> POWER_UP_TYPES = Arrays.asList(
            new LaserBlock.LaserBlockBuilder());
    private final ItemGenerator<SquareAgent> squareGenerator;
    private final ItemGenerator<PowerUpBlock> powerupGenerator;
    private final ItemGenerator<Bullet> bulletGenerator;
    private GameStatus gameStatus;
    private final Shuttle shuttle;
    private final Boundary bound;
    private final Score score;

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
    public void removePowerUp(final PowerUpBlock powerup) {
        this.powerupGenerator.removeItem(powerup);
    }

    @Override
    public List<PowerUpBlock> getPowerUpBlocks() {
        return this.powerupGenerator.getItems();
    }

    @Override
    public void terminate() {
        if (!gameStatus.equals(GameStatus.ENDED)) {
            this.bulletGenerator.getItems().forEach(b -> b.terminate());
            this.squareGenerator.getItems().forEach(s -> s.terminate());
            this.squareGenerator.terminate();
            this.bulletGenerator.terminate();
            this.powerupGenerator.terminate();
        }
        this.gameStatus = GameStatus.ENDED;
    }

    @Override
    public void pause() {
        if (gameStatus.equals(GameStatus.RUNNING)) {
            this.bulletGenerator.getItems().forEach(b -> b.pause());
            this.squareGenerator.getItems().forEach(s -> s.pause());
            this.bulletGenerator.pause();
            this.squareGenerator.pause();
            this.powerupGenerator.pause();
        }
        gameStatus = GameStatus.PAUSED;
    }

    @Override
    public void resumeGame() {
        if (gameStatus.equals(GameStatus.PAUSED)) {
            bulletGenerator.getItems().forEach(b -> b.resumeGame());
            squareGenerator.getItems().forEach(s -> s.resumeGame());
            this.bulletGenerator.resumeGame();
            this.squareGenerator.resumeGame();
            this.powerupGenerator.resumeGame();
        }
        gameStatus = GameStatus.RUNNING;
    }

    @Override
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    @Override
    public void setGameStatus(final GameStatus status) {
        this.gameStatus = status;
    }

    @Override
    public List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> getPowerUpBlockTypes() {
        return Collections.unmodifiableList(ModelImpl.POWER_UP_TYPES);
    }

    @Override
    public void setDisplayRatio(final double ratio) {
        // TODO Auto-generated method stub
        
    }
}
