package it.unibo.oop.cctan.view;

/**
 * A class created to handle the refresh of the game window.
 */
class GraphicPanelUpdater extends Thread {

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

}
