package it.unibo.oop.cctan.model;

import java.util.List;

/**
 * The task of this object is to generate balls over time. More specifically, after each 
 * "GENERATION_RATIO" a new ball is created within the application.
 */
public interface BallGenerator {

    /**
     * This method allows to start the "BallGeneratorImpl" thread and the "BallRatio" thread.
     */
    void launch(); 

    /**
     * Removes the ball from the application.
     * @param ball
     *          it's the ball that must be removed
     */
    void removeBall(BallAgent ball);

    /**
     * Returns the list of the balls that are present in the application.
     * @return
     *          the current list of all the balls that are moving within the application
     */
    List<BallAgent> getBallAgents();
}
