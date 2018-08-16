package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;

/**
 * An abstract implementation of a generic updater.
 * This class is package protected.
 */
abstract class Updater extends Thread implements CommandsObserver {

    private static final int REFRESH_TIME = 20;
    private CommandsObserverSource commandsObserverSource;
    private boolean suspended = false;
    private boolean terminated = false;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    Updater(final CommandsObserverSource commandsObserverSource) {
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
                    if (suspended) {
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
            notify();
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
            notify();
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
