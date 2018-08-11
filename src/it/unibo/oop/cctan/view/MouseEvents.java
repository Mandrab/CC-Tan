package it.unibo.oop.cctan.view;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Optional;
import java.util.function.Consumer;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;

class MouseEvents extends Thread implements CommandsObserver {

    private static final int REFRESH_TIME = 50;
    private View view;
    private boolean suspended;
    private boolean terminated;
    private boolean observing;

    MouseEvents(final View view) {
        this.view = view;
        addCommandsObserver();
        suspended = false;
        terminated = false;
        start();
    }

    @Override
    public void run() {
        while (!terminated) {
            try {
                synchronized (this) {
                    if (suspended) {
                        wait();
                    }
                    if (!observing) {
                        addCommandsObserver();
                    }
                    view.setMouseRelativePosition(getMouseRelativePosition());
                }
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void terminate() {
        if (observing) {
            removeCommandsObserver();
        }
        if (suspended) {
            suspended = false;
            notify();
        }
        terminated = true;
    }

    public synchronized boolean isRunning() {
        return !suspended;
    }

    private void addCommandsObserver() {
        workOnCommandsObserver(s -> {
            s.addCommandsObserver(this); 
            observing = true;
        });
    }

    private void removeCommandsObserver() {
        workOnCommandsObserver(s -> {
            s.removeCommandsObserver(this); 
            observing = false;
        });
    }

    private void workOnCommandsObserver(final Consumer<CommandsObserverSource> c) {
        Optional<CommandsObserverSource> commandsObserverSource = view.getCommandsObserverSource();
        commandsObserverSource.ifPresent(s -> c.accept(s));
    }

    @Override
    public synchronized void newCommand(final Commands command) {
        suspended = command == Commands.PAUSE || command == Commands.END;
        if (!suspended) {
            notify();
        }
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
