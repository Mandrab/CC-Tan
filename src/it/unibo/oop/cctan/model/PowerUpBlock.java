package it.unibo.oop.cctan.model;

/**
 * Represent a generic power-up block that spawns in the game area.
 * When it will be destroyed, its power will be activated.
 */
public interface PowerUpBlock extends FixedItem, Hittable {

    /**
     * When power-up block's hit points reach 0, this method will be called to
     * use it immediately.
     */
    void use();

    /**
     * Get the power-up name, that specify what it ability.
     * @return
     *          a short name of the power-up
     */
    String getName();

    /**
     * Get a symbol (an ASCII character) that represent the power-up.
     * @return
     *          the symbol representing the power-up
     */
    String getSymbol();
}
