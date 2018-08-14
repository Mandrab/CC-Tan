package it.unibo.oop.cctan.view;

import java.awt.MouseInfo;
import java.awt.Point;

class MouseEvents {

    private View view;

    MouseEvents(final View view) {
        this.view = view;
    }

    double getMouseRelativePosition() {
        return calculateDegrees(MouseInfo.getPointerInfo().getLocation().x - getWindowCenter().x,
                                getWindowCenter().y - MouseInfo.getPointerInfo().getLocation().y);
    }

    double getMouseRelativePositionInRange(final double lowerBound, final double upperBound) {
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
