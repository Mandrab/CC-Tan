package it.unibo.oop.cctan.model;

import java.awt.Color;

public interface Bullet extends MovableItem, Runnable {

    /**
     * Get the color of the bullets to use in game area.
     * @return
     *          the color of the bullets
     */
    Color getColor();
}
