package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.MappableData;

/**
 * State what a View implementation must implements.
 */
public interface View {

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
     * Get the top-left point of the window.
     * 
     * @return the x,y origin of the window.
     */
    Point getWindowLocation();

    /**
     * Set the position of the mouse.
     * 
     * @param point
     *            representing the position of the mouse relatively at the center of
     *            the window (as a Cartesian graphic)
     */
    void setMouseRelativePosition(Point point);

    /**
     * Allow to add a "command" observer.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    void addCommandsObserver(CommandsObserver commandsObserver);

    /**
     * Allow to add a "size" observer.
     * 
     * @param sizeObserver
     *            is a class that implements SizeObserver interface
     */
    void addSizeObserver(SizeObserver sizeObserver);

    /**
     * Return the dimension of the game window.
     * 
     * @return a dimension that stores width and height
     */
    Dimension getDimension();

    /**
     * Return a list of data that as to be mapped.
     * 
     * @return The list of the MappableData
     */
    List<MappableData> getListOfMappableData();

}
