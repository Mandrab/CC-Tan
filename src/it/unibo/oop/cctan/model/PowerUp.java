package it.unibo.oop.cctan.model;

import java.awt.Color;

public interface PowerUp extends FixedItem, Hittable {

    /**
     * If power-up life is 0, use it.
     */
    void use();

    /**
     * Get the color of the power-up to use in game area.
     * @return
     *          the color of the power-up
     */
    Color getColor();

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
