package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.controller.Controller;

/**
 * A class that implements View interface.
 */
public class ViewImpl implements View {

    private Controller controller;
    private MouseEvents mouseEvents;
    private Loader loader;
    private Optional<GameWindow> gameWindow = Optional.empty();
    private Optional<SettingsWindow> settingsWindow = Optional.empty();
    private List<CommandsObserver> commandsObservers;
    private List<SizeObserver> sizeObervers;

    /**
     * The constructor method that instantiate all the sub-classes of the view.
     * 
     * @param controller
     *            The Controller type class with which will have to interact in the
     *            future
     */
    public ViewImpl(final Controller controller) {
        this.controller = controller;
        loader = new Loader();
        controller.setView(this);
        commandsObservers = new ArrayList<>();
        sizeObervers = new ArrayList<>();
        
        //Impostazioni
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
    public void showSettingsWindow() {
        if (!settingsWindow.isPresent()) {
            settingsWindow = Optional.of(new SettingsWindow(this));
        } else {
            settingsWindow.get().show();
        }
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Dimension> getDimension() {//return optional or save value in view
        return gameWindow.isPresent() ? gameWindow.get().getDimension() : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<Point> getWindowLocation() {//return optional
        return gameWindow.isPresent() ? 
               Optional.ofNullable(gameWindow.get().getLocation()) : 
               Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public void addCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.add(commandsObserver);
    }

    @Override
    /** {@inheritDoc} */
    public void addSizeObserver(final SizeObserver sizeObserver) {
        sizeObervers.add(sizeObserver);
    }

    @Override
    /** {@inheritDoc} */
    public List<MappableData> getListOfMappableData() {
        return controller.getListOfMappableData();
    }

    @Override
    /** {@inheritDoc} */
    public int getScore() {
        return controller.getScore();
    }

    @Override
    /** {@inheritDoc} */
    public void advanceLoading(int value) {
        loader.advanceLoading(value);
    }

    @Override
    /** {@inheritDoc} */
    public void setLoadImage(ImageIcon img) {
        loader.setLoadImage(img);
    }

    @Override
    /** {@inheritDoc} */
    public double getMouseRelativePosition() {
        return mouseEvents.getMouseRelativePosition();
    }

    @Override
    /** {@inheritDoc} */
    public void setMouseRelativePosition(double mouseRelativePosition) {
        controller.setMouseRelativePosition(mouseRelativePosition);        
    }

    @Override
    public Optional<Dimension> getGameWindowDimension() {
        return gameWindow.isPresent() ? Optional.of(gameWindow.get().getSize()) : Optional.empty();
    }

    @Override
    public File getFont() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<CommandsObserver> getCommandsObserversList() {
        List<CommandsObserver> copia = new ArrayList<>();
        copia.addAll(commandsObservers);
        return copia;
    }

    

}
