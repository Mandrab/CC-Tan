package it.unibo.oop.cctan.view;

import java.awt.MouseInfo;
import java.awt.Point;

/**
 * Works on mouse (degree). Package protected.
 */
class MouseEvents {

    private final View view;

    MouseEvents(final View view) {
        this.view = view;
    }

    /**
     * Get the degrees of the mouse relatively at the center of the game window.
     * 
     * @return A double representing the position of the mouse relatively to the
     *         center of the window [center-right = 0, top-center = 90, ...]
     */
    protected double getMouseRelativePosition() {
        return calculateDegrees(MouseInfo.getPointerInfo().getLocation().x - getWindowCenter().x,
                                getWindowCenter().y - MouseInfo.getPointerInfo().getLocation().y);
    }

    /**
     * Get the degrees of the mouse relatively at the center of the game window between a range.
     * Eg: lowerBoud = 0, upperBound = 180, position top-left -> ret 67.5
     * 
     * @param lowerBound
     *          Minimum value
     * @param upperBound
     *          Maximum value
     * @return
     *          The angle adapted at the selected range
     */
    protected double getMouseRelativePositionInRange(final double lowerBound, final double upperBound) {
        return (upperBound - lowerBound) * getMouseRelativePosition() / 360;
    }

    private double calculateDegrees(final int x, final int y) {
        return Math.abs((y < 0 ? 360 : 0) - Math.toDegrees(Math.acos((x * 1 + y * 0) / (Math.hypot(x, y) * Math.hypot(1, 0)))));
    }

    private Point getWindowCenter() {
        return view.getGameWindowDimension().isPresent() && view.getWindowLocation().isPresent()
               ? new Point(view.getWindowLocation().get().x + view.getGameWindowDimension().get().width / 2,
                         view.getWindowLocation().get().y + view.getGameWindowDimension().get().height / 2)
               : new Point(0, 0);
    }

}
