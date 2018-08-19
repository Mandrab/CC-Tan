package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.Commands;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSource;

/**
 * An abstract implementation of a generic updater.
 * This class is package protected.
 */
abstract class UpdaterImpl extends Thread implements Updater {

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
    UpdaterImpl(final CommandsObserverSource commandsObserverSource) {
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

    @Override
    /** {@inheritDoc} */
    public synchronized void terminate() {
        commandsObserverSource.removeCommandsObserver(this);
        if (suspended) {
            suspended = false;
            notifyAll();
        }
        terminated = true;
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void setPause(final boolean val) {
        suspended = val;
        if (!suspended) {
            notifyAll();
        }
    }

    @Override
    /** {@inheritDoc} */
    public boolean isRunning() {
        return !suspended;
    }

    @Override
    /** {@inheritDoc} */
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    /** {@inheritDoc} */
    public abstract void newCommand(Commands command);

}
