package it.unibo.oop.cctan.controller;

import java.util.Optional;

import javax.swing.ImageIcon;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.ModelImpl;
import it.unibo.oop.cctan.view.View;

public class ControllerImpl implements Controller {

    private Optional<View> view = Optional.empty();
    private Model model;
    private FileLoader fileLoader;
    private ViewUpdater viewUpdater;

    @Override
    public void setView(View v) {
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
                model = new ModelImpl();
                model.launch();
                viewUpdater = new ViewUpdater(view.get(), model);
                break;
            case PAUSE:
                //model pausa (se non lo fa da solo)
                break;
            case RESUME:
                //model resume (se non lo fa da solo)
                break;
            case END:
                //model end (se non lo fa da solo)
                viewUpdater.terminate();
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshGui() {
        view.ifPresent(v -> v.refreshGui());
    }

}
