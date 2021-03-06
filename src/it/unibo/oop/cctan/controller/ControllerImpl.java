package it.unibo.oop.cctan.controller;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.interpackage_comunication.commands_observer.Commands;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelDataImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.ModelImpl;
import it.unibo.oop.cctan.view.View;

/**
 * A class that implements controller interface. This implementation is package
 * protected.
 */
class ControllerImpl implements Controller {

    private Optional<View> view;
    private final Model model;
    private Optional<ViewUpdater> viewUpdater;
    private Optional<Updater> modelUpdater;

    /**
     * Constructor.
     */
    ControllerImpl() {
        final FileLoader fileLoader = new FileLoader();
        view = Optional.empty();
        model = new ModelImpl();
        fileLoader.start();
        viewUpdater = Optional.empty();
        modelUpdater = Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public void setView(final View view) {
        view.getCommandsObserverSource().ifPresent(s -> s.addObserver(this));
        view.getSizeObserverSource().ifPresent(s -> s.addObserver(this));
        this.view = Optional.of(view);
        view.refreshGui();
    }

    @Override
    /** {@inheritDoc} */
    public ModelData getModelData() {
        return viewUpdater.isPresent() 
               ? viewUpdater.get().getModelData()
               : new ModelDataImpl(new LinkedList<>(), 0, GameStatus.ENDED);
    }

    @Override
    /** {@inheritDoc} */
    public void refreshGui() {
        view.ifPresent(v -> v.refreshGui());
    }

    @Override
    /** {@inheritDoc} */
    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        model.setDisplayRatio(screenRatio.getKey().doubleValue() / screenRatio.getValue().doubleValue());
    }

    @Override
    /** {@inheritDoc} */
    public void newCommand(final Commands command) {
        switch (command) {
            case START:
                model.launch();
                view.get()
                    .getSizeObserverSource()
                    .ifPresent(s -> s.getRatio()
                                     .ifPresent(r -> model.setDisplayRatio(r.getKey().doubleValue() 
                                                                           / r.getValue().doubleValue())));
                view.get()
                    .getCommandsObserverSource()
                    .ifPresent(cos -> {
                        viewUpdater = Optional.of(new ViewUpdaterImpl(view.get(), model, cos));
                        viewUpdater.get().start();
                        modelUpdater = Optional.of(new ModelUpdater(view.get(), model, cos));
                        modelUpdater.get().start();
                    });
                break;
            case PAUSE:
                model.pause();
                break;
            case RESUME:
                model.resumeGame();
                break;
            case END:
                model.terminate();
                viewUpdater.ifPresent(vu -> vu.terminate());
                modelUpdater.ifPresent(mu -> mu.terminate());
                break;
            default:
                break;
        }
    }
}
