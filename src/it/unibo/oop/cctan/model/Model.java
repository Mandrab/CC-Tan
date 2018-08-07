package it.unibo.oop.cctan.model;

import java.util.List;

import it.unibo.oop.cctan.geometry.Boundary;

/**
 * The model of the MVC pattern, with methods to manage squares and balls.
 */
public interface Model {

    /**
     * Start the Model, launching the two generators (both balls and squares).
     */
    void launch();

    void terminate();

    /**
     * Get the game area boundaries used by the model.
     * @return
     *          the current map's boundaries
     */
    Boundary getBounds();

    /**
     * Get the current Shuttle.
     * @return
     *          the actual Shuttle item
     */
    Shuttle getShuttle();

    /**
     * Removes the ball from the application.
     * @param ball
     *          it's the ball that must be removed
     */
    void removeBullet(MovableItem ball);

    /**
     * Returns the list of the balls that are present in the application.
     * @return
     *          the current list of all the balls that are moving within the application
     */
    List<MovableItem> getBulletAgents();

    /**
     * Removes the square from the application.
     * @param square
     *          it's the square that must be removed
     */
    void removeSquare(MovableItem square);

    /**
     * Returns the list of the squares that are present in the application.
     * @return
     *          the current list of all the squares that are moving within the application
     */
    List<MovableItem> getSquareAgents();
    
    ItemGenerator getBulletGenerator();

    void setSpaceshipAngle(double angle);

    Score getScore();
}
