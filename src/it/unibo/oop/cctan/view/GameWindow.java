package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

class GameWindow extends JFrame {

    private static final long serialVersionUID = -4110471158542881589L;
    private Dimension gameWindowSize;
    private View view;
    private GraphicPanel gpanel;

    GameWindow(final View view, final Dimension gameWindowSize) {
        setTitle("CC-Tan!");
        this.view = view;
        this.gameWindowSize = gameWindowSize;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gpanel = new GraphicPanel(this);
        getContentPane().add(gpanel, BorderLayout.CENTER);

        setSize(gameWindowSize);
        setResizable(false);
    }

    Dimension getDimension() {
        return gameWindowSize;
    }
}
