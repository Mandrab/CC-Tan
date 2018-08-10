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

}
