package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;

/**
 * A class created to handle the refresh of the game window.
 */
class GraphicPanelUpdater extends Thread implements CommandsObserver {

    private static final int REFRESH_TIME = 20;
    private static final int OPACITY_VALUE = 127; // [0 - 127]; 0 = transparent, 255 = normal
    private boolean suspended = false;
    private boolean terminated = false;
    private GraphicPanel gpanel;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    GraphicPanelUpdater(final GraphicPanel gpanel) {
        this.gpanel = gpanel;
        gpanel.getCommandsObserverSource().ifPresent(s -> s.addCommandsObserver(this));
    }

    @Override
    /** {@inheritDoc} */
    public void run() {
        while (!terminated) {
            try {
                synchronized (this) {
                    if (suspended) {
                        wait();
                    }
                    gpanel.redraw(gpanel.getListOfMappableData());
                }
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop the execution of GraphicPanelUpdater (the game window will not be
     * updated again).
     */
    public synchronized void terminate() {
        gpanel.getCommandsObserverSource().ifPresent(s -> s.removeCommandsObserver(this));
        if (suspended) {
            suspended = false;
            notify();
        }
        terminated = true;
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void newCommand(final Commands command) {
        suspended = command == Commands.PAUSE || command == Commands.END;
        if (suspended) {
            gpanel.redraw(getPrintableText((command == Commands.PAUSE ? "PAUSE!" : "END GAME!") + System.lineSeparator()
                    + "Score: " + gpanel.getScore()));
        } else {
            notify();
        }
    }

    /**
     * Modifies the mappable data list to show a centered message. The other object
     * will become opacities.
     * 
     * @param text
     *            The message to be printed.
     * @return The new list that now include the message
     */
    private List<MappableData> getPrintableText(final String text) {
        List<MappableData> l = gpanel.getListOfMappableData().stream().map(e -> new MappableDataImpl(e.getText(),
                new Color(e.getColor().getRed(), e.getColor().getGreen(), e.getColor().getBlue(), OPACITY_VALUE),
                e.getShape())).collect(Collectors.toList());
        l.add(new MappableDataImpl(text, Color.RED, new Rectangle2D.Double(-1, -1, 2d, 2d)));
        return l;
    }

}
