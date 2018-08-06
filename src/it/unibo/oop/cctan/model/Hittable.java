package it.unibo.oop.cctan.model;

public interface Hittable {

    /**
     * Get the remaining hit points of the square.
     * @return
     *          the remaining hit points of the square
     */
    int getHP();

    /**
     * Specify the current item has been hit and decrease its hit points by 1.
     * If the new hit points value is 0 the item will be destroyed and will be executed
     * other operation according to implementation.
     */
    void hit();
}
