package it.unibo.oop.cctan.view;

class GraphicPanelUpdater extends Thread {

    private boolean stop = false;
    private GraphicPanel gpanel;

    GraphicPanelUpdater(final GraphicPanel gpanel) {
        this.gpanel = gpanel;
    }

    public void run() {
        while (!stop) {
            gpanel.redraw();
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
