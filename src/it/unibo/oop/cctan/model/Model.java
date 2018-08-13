package it.unibo.oop.cctan.model;

import java.util.List;

import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.model.generator.ItemGenerator;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;

/**
 * The model of the MVC pattern, with methods to manage squares and balls.
 */
public interface Model extends Commands {

    /**
     * Start the Model, launching the two generators (both balls and squares).
     */
    void launch();

    /**
     * Get the game area boundaries used by the model.
     * 
     * @return the current map's boundaries
     */
    Boundary getBounds();

    /**
     * Get the current Shuttle.
     * 
     * @return the actual Shuttle item
     */
    Shuttle getShuttle();

    /**
     * Removes the ball from the application.
     * 
     * @param bullet
     *            it's the ball that must be removed
     */
    void removeBullet(Bullet bullet);

    void removePowerUp(PowerUpBlock powerup);

    /**
     * Returns the list of the balls that are present in the application.
     * 
     * @return the current list of all the balls that are moving within the
     *         application
     */
    List<Bullet> getBulletAgents();

    /**
     * Removes the square from the application.
     * 
     * @param square
     *            it's the square that must be removed
     */
    void removeSquare(SquareAgent square);

    /**
     * Returns the list of the squares that are present in the application.
     * 
     * @return the current list of all the squares that are moving within the
     *         application
     */
    List<SquareAgent> getSquareAgents();

    List<PowerUpBlock> getPowerUpBlocks();

    ItemGenerator<Bullet> getBulletGenerator();

    void setSpaceshipAngle(double angle);

    Score getScore();

    /**
     * Return the status of the game defined by the GameStatus enum.
     * 
     * @return The status of the game
     */
    GameStatus getGameStatus();

    void setGameStatus(GameStatus status);

    List<PowerUpBlockImpl.PowerUpBlockBuilder<?>> getPowerUpBlockTypes();
    
    void setDisplayRatio(double ratio);
}
