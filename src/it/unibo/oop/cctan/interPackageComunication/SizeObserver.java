package it.unibo.oop.cctan.interPackageComunication;

import java.awt.Dimension;

import org.apache.commons.lang3.tuple.Pair;

/**
 * An interface that specifies which method must have a class that needs to be
 * informed when size or ratio changes. This is an interface of the "Observer"
 * pattern.
 */
public interface SizeObserver {

    /**
     * A method that as to be implemented by every one who want to be informed of
     * the change of the dimension/ratio of the screen.
     * 
     * @param gameWindowSize
     *            Dimension of the game window (e.g.: 320x240, 640x480,
     *            1024x768,...).
     * @param screenRatio
     *            Ratio of the game window (e.g.: 1:1, 4:3, 16:9,...).
     */
    void update(Dimension gameWindowSize, Pair<Integer, Integer> screenRatio);

}
