package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.Boundary;

/**
 * The model of the MVC pattern, with methods to manage squares and balls.
 */
public interface Model extends BallGenerator, SquareGenerator {

    /**
     * Start the Model, launching the two generators (both balls and squares).
     */
    void start();

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
}
