package it.unibo.oop.cctan.controller;

import java.awt.Dimension;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.ModelImpl;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

public class ControllerImpl implements Controller, CommandsObserver {

    private Optional<View> view = Optional.empty();
    private Model model;
    private FileLoader fileLoader;
    private ViewUpdater viewUpdater;

    public ControllerImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setView(View v) {
        v.getCommandsObserverSource().ifPresent(s -> s.addCommandsObserver(this));
        model = new ModelImpl();
        fileLoader = new FileLoader(this);
        this.view = Optional.of(v);
        fileLoader.start();
    }

    @Override
    public void setMouseRelativePosition(double angle) {
        model.setSpaceshipAngle(angle);
    }

    @Override
    public LoadedFiles getLoadedFiles() {
        return fileLoader.getLoadedFiles();
    }

    @Override
    public ModelData getModelData() {
        return viewUpdater.getModelData();
    }

    @Override
    public void newCommand(final Commands command) {
        switch (command) {
            case START:
                model.launch();
                viewUpdater = new ViewUpdater(view.get(), model);
                viewUpdater.start();
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
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshGui(final Component component) {
        view.ifPresent(v -> v.refreshGui(component));
    }

    @Override
    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        model.setDisplayRatio(screenRatio.getKey() / screenRatio.getValue());
    }

}
