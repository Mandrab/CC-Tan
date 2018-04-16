package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.tuple.Pair;

class GameWindow extends JFrame implements SizeObserver {

    private static final long serialVersionUID = -4110471158542881589L;
    private Dimension gameWindowSize;
    private Pair<Integer, Integer> screenRatio;
    private View view;
    private GraphicPanel gpanel;

    GameWindow(final View view, final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        setTitle("CC-Tan!");
        this.view = view;
        this.gameWindowSize = gameWindowSize;
        this.screenRatio = screenRatio;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gpanel = new GraphicPanel(this);
        getContentPane().add(gpanel, BorderLayout.CENTER);

        setSize(gameWindowSize);
        setResizable(false);
    }

    Dimension getDimension() {
        return gameWindowSize;
    }

    Pair<Integer, Integer> getScreenRatio() {
        return screenRatio;
    }

    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        this.gameWindowSize = gameWindowSize;
        this.screenRatio = screenRatio;
    }

}
