package it.unibo.oop.cctan.model;

import java.util.List;

/**
 * Represents a generic generator of MovableItem.
 */
public interface ItemGenerator {

    /**
     * This method is used to start the thread that generates MovableItem.
     */
    void launch();

    /**
     * This method is used to add to the list the new MovableItem that has just been created.
     * @param item
     *          It's the MovableItem object that must be add to the application.
     */
    void addItemToList(MovableItem item);

    /**
     * Get a new list containing all MovableItem that have been generated 
     * up to that moment.
     * @return
     *          New list containing all MovableItem.
     */
    List<MovableItem> getItems();

    /**
     * This method is used to remove a MovableItem that don't serve 
     * within the application.
     * @param item
     *          It's the MovableItem object that must be removed from
     *          the application.
     */
    void removeItem(MovableItem item);

    /**
     * This method is used to stop the thread that generates MovableItem.
     */
    void terminate();

    /**
     * Get the model of the MVC application.
     * @return
     *          The model.
     */
    Model getModel();
}
