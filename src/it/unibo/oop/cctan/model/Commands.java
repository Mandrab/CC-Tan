package it.unibo.oop.cctan.model;

/**
 * This interface offers the possibility to terminate, 
 * pause or resume the execution of a specific thread.
 */
public interface Commands {

    /**
     * This method allows you to pause the thread.
     */
    void pause();

    /**
     * This method allows you to terminate the thread.
     */
    void terminate();

    /**
     * This method allows you to resume the thread.
     */
    void resumeGame();

}
