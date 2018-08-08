package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class GraphicPanel extends MotionPanel {

    private static final long serialVersionUID = 7666161570364892169L;
    private GameWindow gameWindow;
    private GraphicPanelUpdater updater;
    private Drawer drawer;
    private Dimension dimension;
    private List<MappableData> mappableDatas;
    private int score;

    GraphicPanel(final GameWindow gw) {
        super(gw);
        gameWindow = gw;
        dimension = gw.getDimension();
        mappableDatas = new LinkedList<>();
        score = 0;
        setSize(dimension);

        drawer = new Drawer(dimension, gw.getScreenRatio());
        
        setPreferredSize(dimension);
        updater = new GraphicPanelUpdater(this);
        updater.start();
    }

    public void paint(final Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, (int)(dimension.width * 1.1), (int)(dimension.height * 1.1));
        drawer.setGraphics(graphics);
        synchronized (this) {
            mappableDatas.forEach(drawer::draw);
        }
        drawer.drawText(new ImmutablePair<Double, Double>(0.5, 0.9), Color.WHITE, score + "");
    }

    void redraw(final List<MappableData> mappableDatas) {
        synchronized (this) {
            this.mappableDatas = mappableDatas;
        }
        this.score = gameWindow.getScore();
        repaint();
    }

    public List<MappableData> getListOfMappableData() {
        return gameWindow.getListOfMappableData();
    }

    public int getScore() {
        return gameWindow.getScore();
    }

    public void addCommandsObserver(CommandsObserver commandsObserver) {
        gameWindow.addCommandsObserver(commandsObserver);
    }

}
