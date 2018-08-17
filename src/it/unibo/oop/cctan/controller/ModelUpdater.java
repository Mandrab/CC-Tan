package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.view.View;

/**
 * A class created to periodically update data to the model.
 * This class is package protected.
 */
class ModelUpdater extends Updater {

    private final View view;
    private final Model model;

    ModelUpdater(final View view, final Model model, final CommandsObserverSource commandsObserverSource) {
        super(commandsObserverSource);
        this.view = view;
        this.model = model;
    }

    @Override
    /**
     * Pass the degrees of the mouse relatively at the center of the game window to
     * the model.
     * 
     * @param angle
     *            A double representing the position of the mouse relatively to the
     *            center of the window [center-right = 0, top-center = 90, ...]
     */
    protected void exec() {
        model.setSpaceshipAngle(view.getMouseRelativePosition());
    }

    @Override
    /** {@inheritDoc} */
    public void newCommand(final Commands command) {
        setPause(command == Commands.PAUSE || command == Commands.END);
    }

}
