package it.unibo.oop.cctan.model.geometry;

/**
 * Identify the side of an element in the game area.
 */
public enum Side {
    /**
     * The upper side of the element.
     */
    ABOVE,
    /**
     * The lower side of the element.
     */
    BELOW,
    /**
     * The right side of the element.
     */
    RIGHT,
    /**
     * The left side of the element.
     */
    LEFT,
    /**
     * A generic corner.
     */
    CORNER,
    /**
     * The left upper corner.
     */
    LEFT_TOP_CORNER,
    /**
     * The right upper corner.
     */
    RIGHT_TOP_CORNER,
    /**
     * The left bottom corner.
     */
    LEFT_BOTTOM_CORNER,
    /**
     * The right bottom corner.
     */
    RIGHT_BOTTOM_CORNER;
}
