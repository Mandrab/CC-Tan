package it.unibo.oop.cctan.model;

/**
 * An item that can be hit by something.
 */
public interface Hittable {

    /**
     * Get the remaining hit points of the square.
     * @return
     *          the remaining hit points of the square
     */
    int getHP();

    /**
     * Specify the current item has been hit and decrease its hit points by damage units.
     * If the new hit points value is equal or less than 0, the item will be destroyed
     * and will be executed other operation according to implementation.
     * @param damage
     *                  damage amount, i.e. the number of points that will be subtracted in the
     *                  hit item
     */
    void hit(int damage);
}
