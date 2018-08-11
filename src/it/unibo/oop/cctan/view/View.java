package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

/**
 * State what a View implementation must implements.
 */
public interface View extends SizeAndControlChainOfResponsibility {

    /**
     * Setup and show the game window.
     * 
     * @param resolution
     *            represent the pixel of width and height
     * @param screenRatio
     *            represent the ratio between x and y edge (eg:: 16:9, 4:3)
     */
    void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio);

    /**
     * Setup and show the setting window if not present yes, show it if is present.
     */
    void showSettingsWindow();

    /**
     * Get the top-left point of the window.
     * 
     * @return the x,y origin of the window.
     */
    Optional<Point> getWindowLocation();

    /**
     * Get the degrees of the mouse relatively at the center of the game window.
     * 
     * @return A double representing the position of the mouse relatively to the
     *         center of the window [center-right = 0, top-center = 90, ...]
     */
    double getMouseRelativePosition();

    /**
     * Get the copy of list of CommandsObservers.
     * 
     * @return A copy of list of CommandsObservers.
     */
    List<CommandsObserver> getCommandsObserversList();

    /**
     * Get the copy of list of SizeObserver.
     * 
     * @return A copy of list of SizeObserver.
     */
    List<SizeObserver> getSizeObserversList();

    /**
     * Return the dimension of the game window.
     * 
     * @return a dimension that stores width and height
     */
    Optional<Dimension> getDimension();

    /**
     * Return a list of data that as to be mapped.
     * 
     * @return The list of the MappableData
     */
    List<MappableData> getListOfMappableData();

    /**
     * Obtain the actual score in the game.
     * 
     * @return the actual score
     */
    int getScore();

    /**
     * Sets the percentage of advance of a loading. If the number is less than 0 or
     * exceeds 100, the change is ignored.
     * 
     * @param value
     *            the percentage (es. 1 -> 1%, 40 -> 40%)
     */
    void advanceLoading(int value);

    /**
     * Sets the loaded backgroud image of the loader screen.
     * 
     * @param img
     *            is the imageIcon that will be load in the background
     */
    void setLoadImage(ImageIcon img);

    /**
     * Set the degrees of the mouse relatively at the center of the game window to
     * the controller.
     * 
     * @param mouseRelativePosition The degrees to pass
     */
    void setMouseRelativePosition(double mouseRelativePosition);

    Optional<Dimension> getGameWindowDimension();

    File getFont();

    /**
     * Allow to get the actual Player name if present.
     * 
     * @return a optional of string if present, otherwise return an optional empty
     */
    Optional<String> getPlayerName();

    /**
     * allow to get the keyCommandListener.
     * 
     * @return the keyCommandListener
     */
    KeyCommandsListener getKeyCommandsListener();

}
