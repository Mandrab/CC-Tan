package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.ModelDataImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

/**
 * A class created to handle the refresh of the game window.
 */
class ViewUpdater extends Thread implements CommandsObserver {

    private static final int REFRESH_TIME = 20;
    private View view;
    private Model model;
    private MappableDataAdapter mappableDataAdapter;
    private boolean suspended = false;
    private boolean terminated = false;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    ViewUpdater(final View view, final Model model) {
        this.view = view;
        this.model = model;
        mappableDataAdapter = new MappableDataAdapter(model);
        view.getCommandsObserverSource().ifPresent(s -> s.addCommandsObserver(this));
    }

    @Override
    /** {@inheritDoc} */
    public void run() {
        while (!terminated) {
            try {
                synchronized (this) {
                    view.refreshGui(Component.GAME_WINDOW);
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

    ModelData getModelData() {
        return new ModelDataImpl(mappableDataAdapter.getListOfMappableData(),
                                 model.getScore().getPoints(),
                                 model.getGameStatus());
    }

    /**
     * Stop the execution of GraphicPanelUpdater (the game window will not be
     * updated again).
     */
    public synchronized void terminate() {
        view.getCommandsObserverSource().ifPresent(s -> s.removeCommandsObserver(this));
        if (suspended) {
            suspended = false;
            notify();
        }
        terminated = true;
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void newCommand(final Commands command) {
        suspended = command == Commands.PAUSE || command == Commands.END;
        if (!suspended) {
            notify();
        }
    }

}
