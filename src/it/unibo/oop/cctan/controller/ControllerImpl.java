package it.unibo.oop.cctan.controller;

import java.awt.Dimension;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.ModelImpl;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

/**
 * A class that implements controller interface.
 * This implementation is package protected.
 */
class ControllerImpl implements Controller {

    private Optional<View> view = Optional.empty();
    private Model model;
    private FileLoader fileLoader;
    private ViewUpdater viewUpdater;
    private ModelUpdater modelUpdater;

    ControllerImpl() {
        fileLoader = new FileLoader(this);
        fileLoader.start();
    }

    @Override
    /** {@inheritDoc} */
    public void setView(final View view) {
        view.getCommandsObserverSource().ifPresent(s -> s.addCommandsObserver(this));
        view.getSizeObserverSource().ifPresent(s -> s.addSizeObserver(this));
        model = new ModelImpl();
        this.view = Optional.of(view);
        view.refreshGui(Component.LOADER);
    }

    //@Override
    /** {@inheritDoc} */
    //public LoadedFiles getLoadedFiles() {
      //  return fileLoader.getLoadedFiles();
    //}

    @Override
    /** {@inheritDoc} */
    public ModelData getModelData() {
        return viewUpdater.getModelData();
    }

    @Override
    /** {@inheritDoc} */
    public void refreshGui(final Component component) {
        view.ifPresent(v -> v.refreshGui(component));
    }

    @Override
    /** {@inheritDoc} */
    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        model.setDisplayRatio(screenRatio.getKey().doubleValue() 
                              / screenRatio.getValue().doubleValue());
    }

    @Override
    /** {@inheritDoc} */
    public void newCommand(final Commands command) {
        switch (command) {
            case START:
                model.launch();
                view.get()
                    .getSizeObserverSource()
                    .ifPresent(s -> s.getRatio().ifPresent(r -> 
                        model.setDisplayRatio(r.getKey().doubleValue() 
                                              / r.getValue().doubleValue())));
                view.get().getCommandsObserverSource().ifPresent(cos -> {
                    viewUpdater = new ViewUpdater(view.get(), model, cos);
                    viewUpdater.start();
                    modelUpdater = new ModelUpdater(view.get(), model, cos);
                    modelUpdater.start();
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
                viewUpdater.terminate();
                modelUpdater.terminate();
                break;
            default:
                break;
        }
    }
}
