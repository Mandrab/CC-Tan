package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserver;

/**
 * An interface for class that have to update something.
 */
interface Updater extends CommandsObserver {

    /**
     * Starts the updater.
     */
    void start();

    /**
     * Set if the thread has to be paused.
     * 
     * @param val
     *            True if should be paused, False otherwise
     */
    void setPause(boolean val);

    /**
     * Stop the execution of GraphicPanelUpdater (the game graphics will not be
     * updated again).
     */
    void terminate();

    /**
     * Inform if the thread is running.
     * 
     * @return true if the thread is running, false otherwise
     */
    boolean isRunning();

    /**
     * Inform if the thread is terminated.
     * 
     * @return true if the thread is terminated, false otherwise
     */
    boolean isTerminated();

}
