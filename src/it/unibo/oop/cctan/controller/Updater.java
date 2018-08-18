package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.Commands;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserver;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSource;

/**
 * An abstract implementation of a generic updater.
 * This class is package protected.
 */
abstract class Updater extends Thread implements CommandsObserver {

    private static final int REFRESH_TIME = 20;
    private final CommandsObserverSource commandsObserverSource;
    private boolean suspended;
    private boolean terminated;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    Updater(final CommandsObserverSource commandsObserverSource) {
        super();
        this.commandsObserverSource = commandsObserverSource;
        commandsObserverSource.addCommandsObserver(this);
    }

    @Override
    /** {@inheritDoc} */
    public void run() {
        while (!terminated) {
            try {
                exec();
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The actions to be executed.
     */
    protected abstract void exec();

    /**
     * Stop the execution of GraphicPanelUpdater (the game window will not be
     * updated again).
     */
    public synchronized void terminate() {
        commandsObserverSource.removeCommandsObserver(this);
        if (suspended) {
            suspended = false;
            notifyAll();
        }
        terminated = true;
    }

    /**
     * Set if the thread has to be paused.
     * 
     * @param val
     *          True if should be paused, False otherwise
     */
    public synchronized void setPause(final boolean val) {
        suspended = val;
        if (!suspended) {
            notifyAll();
        }
    }

    /**
     * Inform if the thread is running.
     * 
     * @return
     * true if the thread is running, false otherwise
     */
    public boolean isRunning() {
        return !suspended;
    }

    /**
     * Inform if the thread is terminated.
     * 
     * @return
     * true if the thread is terminated, false otherwise
     */
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    /** {@inheritDoc} */
    public abstract void newCommand(Commands command);

}
