package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.interpackage_comunication.commands_observer.Commands;
import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserverSource;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelDataImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.view.View;

/**
 * A class created to handle the refresh of the game window.
 * This class is package protected.
 */
class ViewUpdaterImpl extends UpdaterImpl implements ViewUpdater {

    private final View view;
    private final Model model;
    private final MappableDataAdapter mappableDataAdapter;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    ViewUpdaterImpl(final View view, final Model model, final CommandsObserverSource commandsObserverSource) {
        super(commandsObserverSource);
        this.view = view;
        this.model = model;
        mappableDataAdapter = new MappableDataAdapter(model);
    }

    @Override
    /**
     * If the game is ended then force an end command to all the observers.
     */
    protected void exec() {
        if (model.getGameStatus() == GameStatus.ENDED) {
            view.getCommandsObserverSource().ifPresent(s -> s.forceCommand(Commands.END));
        }
        view.refreshGui();
    }

    @Override
    /** {@inheritDoc} */
    public ModelData getModelData() {
        return new ModelDataImpl(mappableDataAdapter.getListOfMappableData(),
                                 model.getScore().getPoints(),
                                 model.getGameStatus());
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void newCommand(final Commands command) {
        setPause(command == Commands.PAUSE || command == Commands.END);
    }

}
