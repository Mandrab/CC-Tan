package it.unibo.oop.cctan.view;

import java.awt.Dimension;

import javafx.util.Pair;



interface SizeObserver {

    /**
     * A method that as to be implemented by every one who want to be informed of
     * the change of the dimension/ratio of the screen.
     * 
     * @param gameWindowSize
     *            Dimension of the game window (e.g.: 320x240, 640x480,
     *            1024x768,...).
     * @param rat
     *            Ratio of the game window (e.g.: 1:1, 4:3, 16:9,...).
     */
    void update(Dimension gameWindowSize, Pair<Integer, Integer> rat);

}
