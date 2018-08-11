package it.unibo.oop.cctan.view;

import java.awt.MouseInfo;
import java.awt.Point;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

class MouseEvents extends Thread implements CommandsObserver {

    private View view;
    private boolean suspend;
    private boolean terminated;

    MouseEvents(final View view) {
        this.view = view;
        view.addCommandsObserver(this);
        suspend = false;
        terminated = false;
        start();
    }
    
    @Override
    public void run() {
        while(!terminated) {
            while(!suspend) {
                view.setMouseRelativePosition(getMouseRelativePosition());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.err.println("Error during mouse position detection!");
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(terminated ? 0 : 50);
            } catch (InterruptedException e) {
                System.err.println("Error during mouse position detection!");
                e.printStackTrace();
            }
        }
    }
    
    @Override
    synchronized public void newCommand(Commands command) {
        suspend = command == Commands.PAUSE || command == Commands.END;
    }
    
    synchronized public void terminate() {
        suspend = true;
        terminated = true;
    }
    
    public boolean isRunning() {
        return !suspend;
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
