package it.unibo.oop.cctan.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.model.generator.BulletGenerator;
import it.unibo.oop.cctan.model.generator.ItemGenerator;
import it.unibo.oop.cctan.model.generator.PowerUpGenerator;
import it.unibo.oop.cctan.model.generator.SquareGenerator;
import it.unibo.oop.cctan.model.geometry.Boundary;

/**
 * The implementation of Model interface, with operations to work with balls and
 * squares.
 */
public class ModelImpl implements Model {

    private static final List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> POWER_UP_TYPES = 
                Arrays.asList(new LaserBlock.LaserBlockBuilder());
    private ItemGenerator<SquareAgent> squareGenerator;
    private ItemGenerator<PowerUpBlock> powerupGenerator;
    private ItemGenerator<Bullet> bulletGenerator;
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
        this.gameStatus = GameStatus.ENDED;
        this.shuttle = new ShuttleImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void launch() {
        if (gameStatus.equals(GameStatus.ENDED)) {
            score.reset();
            this.generatorInstantiation();
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
    public void setDisplayRatio(final double ratio) {
        // fissiamo height = 2 (cioè y0 = -1 e y1 = 1) e settiamo la width di conseguenza
        // width / height = ratio ------> width = ratio * height
            // --> x0 = -ratio * height / 2 ----> x0 = -ratio
            // --> x1 = radio * height / 2 -----> x1 = ratio
        this.bound.setBoundary(-ratio, ratio, -1, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpaceshipAngle(final double angle) {
        ((FixedItemImpl) this.shuttle).setAngle(angle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameStatus(final GameStatus status) {
        this.gameStatus = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBullet(final Bullet bullet) {
        this.bulletGenerator.removeItem(bullet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSquare(final SquareAgent square) {
        this.squareGenerator.removeItem(square);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePowerUp(final PowerUpBlock powerup) {
        this.powerupGenerator.removeItem(powerup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Score getScore() {
        return this.score;
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
    public Shuttle getShuttle() {
        return this.shuttle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SquareAgent> getSquareAgents() {
        return this.squareGenerator.getItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PowerUpBlock> getPowerUpBlocks() {
        return this.powerupGenerator.getItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemGenerator<Bullet> getBulletGenerator() {
        return this.bulletGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<Bullet> getBulletAgents() {
        return this.bulletGenerator.getItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> getPowerUpBlockTypes() {
        return Collections.unmodifiableList(ModelImpl.POWER_UP_TYPES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminate() {
        this.bulletGenerator.getItems().forEach(b -> b.terminate());
        this.squareGenerator.getItems().forEach(s -> s.terminate());
        this.squareGenerator.terminate();
        this.bulletGenerator.terminate();
        this.powerupGenerator.terminate();
        this.getShuttle().getActivePowerUps().forEach(p -> p.terminate());
        //this.istanceGenerators();
        this.gameStatus = GameStatus.ENDED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        if (gameStatus.equals(GameStatus.RUNNING)) {
            this.bulletGenerator.getItems().forEach(b -> b.pause());
            this.squareGenerator.getItems().forEach(s -> s.pause());
            this.bulletGenerator.pause();
            this.squareGenerator.pause();
            this.powerupGenerator.pause();
            this.getShuttle().getActivePowerUps().forEach(p -> p.pause());
        }
        gameStatus = GameStatus.PAUSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeGame() {
        if (gameStatus.equals(GameStatus.PAUSED)) {
            bulletGenerator.getItems().forEach(b -> b.resumeGame());
            squareGenerator.getItems().forEach(s -> s.resumeGame());
            this.bulletGenerator.resumeGame();
            this.squareGenerator.resumeGame();
            this.powerupGenerator.resumeGame();
            this.getShuttle().getActivePowerUps().forEach(p -> p.resumeGame());
        }
        gameStatus = GameStatus.RUNNING;
    }

    private void generatorInstantiation() {
        this.squareGenerator = new SquareGenerator(this);
        this.bulletGenerator = new BulletGenerator(this);
        this.powerupGenerator = new PowerUpGenerator(this);
    }
}
