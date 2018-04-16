package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

class GraphicPanel extends JPanel {

    private static final long serialVersionUID = 7666161570364892169L;
    private GraphicPanelUpdater updater;
    private Drawer drawer;
    private Dimension dimension;

    GraphicPanel(final GameWindow gw) {
        dimension = gw.getDimension();
        setSize(dimension);

        drawer = new Drawer(dimension, gw.getScreenRatio());

        updater = new GraphicPanelUpdater(this);
        updater.start();
    }

    public void paint(final Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
        drawer.setGraphics(graphics);
    }

    void redraw() {

    }

}
