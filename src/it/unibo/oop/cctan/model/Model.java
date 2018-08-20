package it.unibo.oop.cctan.model;

import java.util.List;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.model.generator.ItemGenerator;
import it.unibo.oop.cctan.model.geometry.Boundary;

/**
 * The model of the MVC pattern, with methods to manage squares and balls.
 */
public interface Model extends Commands {

    /**
     * Start the Model, launching the three generators (bullets, squares and power-ups).
     */
    void launch();

    /**
     * Sets the x/y ratio of the Cartesian graphic.
     * @param ratio
     *          The result of the division of x over y.
     */
    void setDisplayRatio(double ratio);

    /**
     * Set the current angle of the Shuttle.
     * @param angle
     *          New angle of the Shuttle.
     */
    void setSpaceshipAngle(double angle);

    /**
     * Set the current state of the game.
     * @param status
     *          New state of the game.
     */
    void setGameStatus(GameStatus status);

    /**
     * Removes the {@link Bullet Bullet} from the application.
     * @param bullet
     *            It's the {@link Bullet Bullet} that must be removed.
     */
    void removeBullet(Bullet bullet);

    /**
     * Removes the {@link SquareAgent SquareAgent} from the application.
     * @param square
     *            It's the {@link SquareAgent SquareAgent} that must be removed.
     */
    void removeSquare(SquareAgent square);

    /**
     * Removes the {@link PowerUpBlock PowerUpBlock} from the application.
     * @param powerup
     *            It's the {@link PowerUpBlock PowerUpBlock} that must be removed.
     */
    void removePowerUp(PowerUpBlock powerup);

    /**
     * Get the current game score.
     * @return 
     *          The current game score.
     */
    Score getScore();

    /**
     * Get the game area boundaries used by the model.
     * @return 
     *          The current map's boundaries.
     */
    Boundary getBounds();

    /**
     * Get the current {@link Shuttle Shuttle}.
     * @return 
     *          The actual {@link Shuttle Shuttle} item.
     */
    Shuttle getShuttle();

    /**
     * Return the status of the game defined by the {@link GameStatus GameStatus} enumeration.
     * @return 
     *          The status of the game.
     */
    GameStatus getGameStatus();

    /**
     * Returns the list of the {@link Bullet Bullet} that are present within the application.
     * @return 
     *          The current list of all the {@link Bullet Bullet} that are moving within the application.
     */
    List<Bullet> getBulletAgents();

    /**
     * Returns the list of the {@link SquareAgent SquareAgent} that are present within the application.
     * @return 
     *          The current list of all {@link SquareAgent SquareAgent} that are moving within the application.
     */
    List<SquareAgent> getSquareAgents();

    /**
     * Returns the list of the {@link PowerUpBlock PowerUpBlock} that are present within the application.
     * @return 
     *          The current list of all {@link PowerUpBlock PowerUpBlock} that are within the application.
     */
    List<PowerUpBlock> getPowerUpBlocks();

    /**
     * Returns the bullet generator of the application.
     * @see ItemGenerator
     * @see BulletGenerator
     * @return
     *          The BulletGeneratorImpl object of the application.
     */
    ItemGenerator<Bullet> getBulletGenerator();

    /**
     * Return all the builders of all types of {@link PowerUpBlock PowerUpBlock} that can be 
     * generated within the application.
     * @return
     *          All the possible builders of all types of {@link PowerUpBlock PowerUpBlock}.
     */
    List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> getPowerUpBlockTypes();

}
