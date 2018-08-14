package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.view.View;

public class ModelUpdater extends Updater {

    private View view;
    private Model model;

    ModelUpdater(final View view, final Model model, final CommandsObserverSource commandsObserverSource) {
        super(commandsObserverSource);
        this.view = view;
        this.model = model;
    }

    @Override
    void exec() {
        model.setSpaceshipAngle(view.getMouseRelativePosition());
    }

    @Override
    public void newCommand(Commands command) {
        setPause(command == Commands.PAUSE || command == Commands.END);
    }

}
