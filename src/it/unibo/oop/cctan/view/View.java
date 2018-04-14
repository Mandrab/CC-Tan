package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;

import org.apache.commons.lang3.tuple.Pair;

/** 
* State what a View implementation must implements.
*/
public interface View {

    /** 
     * Setup and show the game window.
     * 
     * @param resolution represent the pixel of width and height
     */
    void show(Dimension resolution);

    /** 
     * Get the top-left point of the window.
     * 
     * @return the x,y origin of the window.
     */
    Point getWindowLocation();

    /** 
     * Set the position of the mouse.
     * 
     * @param point representing the position of the mouse relatively at the center of the window (as a cartesian graphic)
     */
    void setMouseRelativePosition(Point point);

    /** 
     * Allow to add a "command" observer.
     * 
     * @param commandsObserver is a class that implements CommandsObserver interface
     */
	void addCommandsObserver(CommandsObserver commandsObserver);
	
	/**
	 * Return the dimension of the game window.
	 * 
	 * @return a dimension that stores width and height
	 */
	Dimension getDimension();

}
