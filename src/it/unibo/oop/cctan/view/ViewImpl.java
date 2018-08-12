package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.controller.Controller;

/**
 * A class that implements View interface.
 */
public class ViewImpl extends SizeAndControlChainOfResponsibilityImpl implements View {

    private Controller controller;
    private MouseEvents mouseEvents;
    private Loader loader;
    private Optional<GameWindow> gameWindow = Optional.empty();
    private Optional<SettingsWindow> settingsWindow = Optional.empty();
    private CommandsObserversManager commandsObserversManager;
    private KeyCommandsListener keyCommandsListener;

    /**
     * The constructor method that instantiate all the sub-classes of the view.
     * 
     * @param controller
     *            The Controller type class with which will have to interact in the
     *            future
     */
    public ViewImpl(final Controller controller) {
        this.controller = controller;
        loader = new Loader(this);
        controller.setView(this);
        commandsObserversManager = new CommandsObserversManager();
        this.keyCommandsListener = new KeyCommandsListener(this);
    }

    @Override
    /** {@inheritDoc} */
    public void showGameWindow(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (!gameWindow.isPresent()) {
            gameWindow = Optional.of(new GameWindow(this));
        }
        gameWindow.get().update(gameWindowSize, screenRatio);
        gameWindow.get().setVisible(true);
        mouseEvents = new MouseEvents(this);
    }

    @Override
    /** {@inheritDoc} */
    public Optional<String> getPlayerName() {
        if (settingsWindow.isPresent()) {
            return Optional.of(settingsWindow.get().getPlayerName());
        } else {
            return Optional.empty();
        }
    }

    @Override
    /** {@inheritDoc} */
    public void showSettingsWindow() {
        if (!settingsWindow.isPresent()) {
            settingsWindow = Optional.of(new SettingsWindow(this));
        } else {
            settingsWindow.get().show();
        }
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Dimension> getDimension() { //return optional or save value in view
        return gameWindow.isPresent() ? gameWindow.get().getDimension() : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Point> getWindowLocation() { //return optional
        return gameWindow.isPresent() 
               ? Optional.ofNullable(gameWindow.get().getLocation()) 
               : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public double getMouseRelativePosition() {
        return mouseEvents.getMouseRelativePosition();
    }

    @Override
    /** {@inheritDoc} */
    public void setMouseRelativePosition(final double mouseRelativePosition) {
        controller.setMouseRelativePosition(mouseRelativePosition);
    }

    @Override
    public Optional<Dimension> getGameWindowDimension() {
        return gameWindow.isPresent() ? Optional.of(gameWindow.get().getSize()) : Optional.empty();
    }

    @Override
    public LoadedFiles getLoadedFiles() {
        return controller.getLoadedFiles();
    }

    /** {@inheritDoc} */
    @Override
    public KeyCommandsListener getKeyCommandsListener() {
        return keyCommandsListener;
    }

    /** {@inheritDoc} */
    @Override
    public CommandsObserversManager getCommandsObserversManager() {
        return commandsObserversManager;
    }

    @Override
    public void refreshGui() {
        gameWindow.ifPresentOrElse(gw -> gw.refresh(controller.getModelData()), () -> loader.refresh());
    }

    @Override
    public ModelData getModelData() {
        return controller.getModelData();
    }

}
