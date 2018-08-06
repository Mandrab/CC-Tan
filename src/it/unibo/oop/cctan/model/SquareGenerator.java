package it.unibo.oop.cctan.model;

import java.util.List;

/**
 * The task of this object is to generate squares over time. More specifically, after each 
 * "GENERATION_RATIO" a new ball is created within the application. 
 */
public interface SquareGenerator {

    /**
     * This method allows to start the "SquareGeneratorImpl" thread and the "SquareRatio" thread.
     */
    void launch();

    void terminate();
    /**
     * Removes the square from the application.
     * @param square
     *          it's the square that must be removed
     */
    void removeSquare(SquareAgent square);

    /**
     * Returns the list of the squares that are present in the application.
     * @return
     *          the current list of all the squares that are moving within the application
     */
    List<SquareAgent> getSquareAgents();
}
