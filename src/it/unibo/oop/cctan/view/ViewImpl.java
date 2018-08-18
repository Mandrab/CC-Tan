package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interpackage_comunication.ModelData;

/**
 * A class that implements View interface.
 */
public class ViewImpl extends SizeAndCommandsLinkImpl implements View {

    private final Controller controller;
    private final Loader loader;
    private final KeyCommandsListener keyCommandsListener;
    private Optional<MouseEvents> mouseEvents = Optional.empty();
    private Optional<GameWindow> gameWindow = Optional.empty();
    private Optional<SettingsWindow> settingsWindow = Optional.empty();

    /**
     * The constructor method that instantiate all the sub-classes of the view.
     * 
     * @param controller
     *            The Controller type class with which will have to interact in the
     *            future
     */
    public ViewImpl(final Controller controller) {
        super();
        this.controller = controller;
        keyCommandsListener = new KeyCommandsListener(this);
        setCommandsObserverSource(keyCommandsListener);
        loader = new Loader(this);
        controller.setView(this);
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void showGameWindow(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (!gameWindow.isPresent()) {
            gameWindow = Optional.of(new GameWindow(this));
        }
        gameWindow.get().addKeyListener(keyCommandsListener.getKeyListener());
        gameWindow.get().update(gameWindowSize, screenRatio);
        gameWindow.get().setVisible(true);
        if (!mouseEvents.isPresent()) {
            mouseEvents = Optional.of(new MouseEvents(this));
        }
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
    public synchronized void showSettingsWindow() {
        if (!settingsWindow.isPresent()) {
            System.out.println("istanzio");
            settingsWindow = Optional.of(new SettingsWindow(this));
        }
        setSizeObserverSource(settingsWindow.get());
        settingsWindow.get().show();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Point> getWindowLocation() {
        return gameWindow.isPresent() 
               ? Optional.ofNullable(gameWindow.get().getLocation()) 
               : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public double getMouseRelativePosition() {
        if (!mouseEvents.isPresent()) {
            mouseEvents = Optional.of(new MouseEvents(this));
        }
        return mouseEvents.get().getMouseRelativePosition();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Dimension> getGameWindowDimension() {
        return gameWindow.isPresent() ? Optional.of(gameWindow.get().getSize()) : Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public KeyCommandsListener getKeyCommandsListener() {
        return keyCommandsListener;
    }

    @Override
    /** {@inheritDoc} */
    public void refreshGui(final Component component) {
        if (component.equals(Component.LOADER)) {
            loader.refresh();
        } else {
            gameWindow.ifPresent(gw -> gw.refresh(controller.getModelData()));
        }
    }

    @Override
    /** {@inheritDoc} */
    public ModelData getModelData() {
        return controller.getModelData();
    }

    /** {@inheritDoc} */
    @Override
    public void hideGameWindow() {
        if (gameWindow.isPresent()) {
            gameWindow.get().setVisible(false);
        }
    }
}
