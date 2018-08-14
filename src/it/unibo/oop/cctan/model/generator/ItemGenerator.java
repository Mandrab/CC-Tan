package it.unibo.oop.cctan.model.generator;

import java.util.List;

import it.unibo.oop.cctan.model.Commands;
import it.unibo.oop.cctan.model.FixedItem;
import it.unibo.oop.cctan.model.Model;

/**
 * Represents a generic generator of objects of type <T>.
 * @param <T>
 *              It's the type of objects that will be created dynamically 
 *              over time from the class that will implement this interface.
 */
public interface ItemGenerator<T extends FixedItem> extends Commands {

    /**
     * This method is used to start the thread that generates objects.
     */
    void launch();

    /**
     * This method is used to add to the list the new objects that has just been created.
     * @param item
     *          It's the object that must be add to the application.
     */
    void addItemToList(T item);

    /**
     * Get a new list containing all objects that have been generated up to that moment.
     * @return
     *          New list containing all objects.
     */
    List<T> getItems();

    /**
     * This method is used to remove a objects that don't serve within the application.
     * @param item
     *          It's the object that must be removed from the application.
     */
    void removeItem(T item);

    /**
     * @return
     *          The model of the application.
     */
    Model getModel();

}
