package it.unibo.oop.cctan.view;

import java.util.LinkedList;

class GraphicPanelUpdater extends Thread {

    private boolean stop = false;
    private GraphicPanel gpanel;

    GraphicPanelUpdater(final GraphicPanel gpanel) {
        this.gpanel = gpanel;
    }

    public void run() {
        while (!stop) {
            gpanel.redraw(gpanel.getListOfMappableData());
            try {
                Thread.sleep(20);   //concurrent modification exception con tempo troppo basso -> aggiusta con synchronized  
            } catch (Exception ex) {
                System.err.println("An error has occurred");
                ex.printStackTrace();
            }
        }
    }

    public void terminate() {
        stop = true;
    }

}
