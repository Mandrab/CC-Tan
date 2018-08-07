package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;

/**
 * A class created to handle the refresh of the game window.
 */
class GraphicPanelUpdater extends Thread implements CommandsObserver {

    private final int refreshTime = 20;
    private boolean stop = false;
    private GraphicPanel gpanel;

    /**
     * The constructor of GraphicPanelUpdater class.
     * 
     * @param gpanel
     *            The graphic panel to update.
     */
    GraphicPanelUpdater(final GraphicPanel gpanel) {
        this.gpanel = gpanel;
    }

    @Override
    public void run() {
        while (!stop) {
            gpanel.redraw(gpanel.getListOfMappableData());
            try {
                Thread.sleep(refreshTime);
            } catch (Exception ex) {
                System.err.println("An error has occurred");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Stop the execution of GraphicPanelUpdater (the game window will not be
     * updated again).
     */
    public void terminate() {
        stop = true;
    }

    @Override
    public void newCommand(Commands command) {
        stop = !(command == Commands.START || command == Commands.RESUME);
        start();
        switch (command) {
        case PAUSE:
            gpanel.redraw(gpanel.getListOfMappableData()
                                .add(new MappableDataImpl("PAUSE", 
                                                          Color.RED,
                                                          new Rectangle2D.Double(-1, 1, 2, 2))));
            
        }
    }

}
