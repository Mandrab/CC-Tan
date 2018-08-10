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
    private boolean suspend = false;
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
        gpanel.addCommandsObserver(this);
    }

    @Override
    public void run() {
        while (!terminated) {
            try {
                synchronized (this) {
                    if (suspend) {
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
    public void terminate() {
        terminated = true;
    }

    @Override
    public synchronized void newCommand(Commands command) {
        if (suspend = command == Commands.PAUSE || command == Commands.END) {
            gpanel.redraw(getPrintableText((command == Commands.PAUSE ? "PAUSE!" : "END GAME!") 
                                           + System.lineSeparator()
                                           + "Score: " + gpanel.getScore()));
        } else {
            notify();
        }
    }

    private List<MappableData> getPrintableText(String text) {
        List<MappableData> l = gpanel.getListOfMappableData()
                                     .stream()
                                     .map(e -> new MappableDataImpl(e.getText(),
                                                                    new Color(e.getColor().getRed(), 
                                                                              e.getColor().getGreen(), 
                                                                              e.getColor().getBlue(), 
                                                                              127), 
                                                                    e.getShape()))
                                     .collect(Collectors.toList());
        l.add(new MappableDataImpl(text, 
                                   Color.RED,
                                   new Rectangle2D.Double(-1, -1, 2d, 2d)));
        return l;
    }

}
