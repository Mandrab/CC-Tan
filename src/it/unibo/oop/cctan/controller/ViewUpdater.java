package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.ModelDataImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

/**
 * A class created to handle the refresh of the game window.
 * This class is package protected.
 */
class ViewUpdater extends Updater {

    private View view;
    private Model model;
    private MappableDataAdapter mappableDataAdapter;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    ViewUpdater(final View view, final Model model, final CommandsObserverSource commandsObserverSource) {
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
            System.out.println("ended");
            view.getCommandsObserverSource().ifPresent(s -> s.forceCommand(Commands.END));
        }
        view.refreshGui(Component.GAME_WINDOW);
    }

    /**
     * Return a flat class containing all the useful data to map.
     * This method is package protected.
     * 
     * @return
     *          A flat class which contain all the useful data
     */
    ModelData getModelData() {
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
