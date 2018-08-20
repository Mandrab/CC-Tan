package it.unibo.oop.cctan.model;

/**
 * Represent an item that can also change its position during his life.
 */
public interface MovableItem extends FixedItem, Commands {

    /**
     * Get the movement speed of the item express in units per refresh. A unit is
     * a percentage of the game area (in coordinate between -1 and 1 for both directions).
     * For example, 0.1 units means the item will be moved of 1/10 the window dimension every refresh.
     * @return
     *          the current speed of the item
     */
    double getSpeed();
}
