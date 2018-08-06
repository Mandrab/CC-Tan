package it.unibo.oop.cctan.view;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

class MouseEvents extends Thread implements CommandsObserver {

    private View view;
    private boolean run;

    MouseEvents(final View view) {
        this.view = view;
        start();
    }
    
    @Override
    public void run() {
        while(run) {
            view.setMouseRelativePosition(getMouseRelativePosition());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.err.println("Error during mouse position detection!");
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void newCommand(Commands command) {
        if (command == Commands.START || command == Commands.RESUME) {
            run = true;
            start();
        } else
            run = false;
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
        return new Point(view.getWindowLocation().x + view.getDimension().width / 2,
                         view.getWindowLocation().x + view.getDimension().height / 2);
    }

}
